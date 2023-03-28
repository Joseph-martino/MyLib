package com.mylib.core.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Book;
import com.mylib.core.entities.Author;
import com.mylib.core.entities.Illustrator;
import com.mylib.core.repositories.AuthorRepository;
import com.mylib.core.repositories.EditorRepository;
import com.mylib.core.repositories.IBookRepository;
import com.mylib.core.repositories.IllustratorRepository;
import com.mylib.core.repositories.CollectionRepository;
import com.mylib.core.entities.Editor;
import com.mylib.core.entities.Collection;

@Component
public class CsvParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);
		
	@Value("${csv.file.location}")
	private File folder;
	
	@Autowired
	BookService bookService;

	
	public File getFileFromFolder(File folder) {
		File file = null;
		if(folder.exists()) {
			File[] listOfFiles = folder.listFiles();
			for(File fileInFolder: listOfFiles) {
				if("bibliotheque.csv".equals(fileInFolder.getName())) {
					file = fileInFolder;
					break;
				} else {
					LOGGER.info("Le fichier bibliotheque.csv n'existe pas ou n'a pas le nom approprié");
				}
			}
			return file;	
		} 
		LOGGER.info("Le fichier n'existe pas");	
		return null;
	}
	
	@Scheduled(cron = "0 * * * * *")
	public void getBooksList() {
		
		File sourceFile = getFileFromFolder(folder);
        
        if(sourceFile == null ) {
        	LOGGER.info("Echec de la lecture du fichier");
        } else {
        	//récupérer tout ce qu'il y a dans la base de données
        	List<Book> books = this.bookService.getAll();
        	
        	// supprimer tout ce qu'il y a dans la base de données
        	this.bookService.deleteAllFromDatabase();
	 
		    try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
		        for(String line; (line = br.readLine()) != null; ) {
		        	this.bookService.lineParser(line);      
		        }
		        
		        File processedFolder = new File(folder, "processed");
		        String fileNameWithoutExtension = getFileNameWithoutExtension(sourceFile.getName());
		        File destinationFile = new File(processedFolder.getPath() +"/" + fileNameWithoutExtension+ LocalDate.now() + ".csv");
		        moveFileToFolder(processedFolder, sourceFile, destinationFile);
		    } catch (IOException e) {
		    	LOGGER.error("Echec de la création du livre", e);
		    	File failedFolder = new File(folder, "failed");
		    	String fileNameWithoutExtension = getFileNameWithoutExtension(sourceFile.getName());
		    	File destinationFile = new File(failedFolder.getPath() + "/" + fileNameWithoutExtension + LocalDate.now() + ".csv");
		    	moveFileToFolder(failedFolder, sourceFile, destinationFile);
	        	// supprimer tout ce qu'il y a dans la base de données
	        	this.bookService.deleteAllFromDatabase();
        		//ajouter anciennes données de la liste books
        		for(Book book : books) {
        			this.bookService.createBook(book);
        		}	
		    }
        }
	}
	
	public void moveFileToFolder(File folder, File sourceFile, File destinationFile) {
		
		if(!folder.exists()) {
			folder.mkdir();
		} 
		copyFile(sourceFile, destinationFile);
		sourceFile.delete();
		
	}
	
	public void copyFile(File sourceFile, File destinationFile) {
		try {
			Files.copy(sourceFile.toPath(), destinationFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException e) {
			e.printStackTrace();
			LOGGER.info("Fichier " + sourceFile.getName() + "déplacé dans le dossier " + destinationFile.getName());
		}
	}
	
	public String getFileNameWithoutExtension(String fileName) {
		int position = fileName.lastIndexOf('.');
		String fileNameWithoutExtension = (position == -1) ? fileName : fileName.substring(0, position);
		return fileNameWithoutExtension;
	}
}
