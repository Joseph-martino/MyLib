package com.mylib.core.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mylib.core.entities.Book;
import com.mylib.core.entities.Author;
import com.mylib.core.entities.Illustrator;
import com.mylib.core.repositories.AuthorRepository;
import com.mylib.core.repositories.BookRepository;
import com.mylib.core.repositories.EditorRepository;
import com.mylib.core.repositories.IBookRepository;
import com.mylib.core.repositories.IllustratorRepository;
import com.mylib.core.repositories.CollectionRepository;
import com.mylib.core.entities.Editor;
import com.mylib.core.entities.Collection;

@Component
public class CsvParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);
	
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private IllustratorRepository illustratorRepository;
	@Autowired
	private EditorRepository editorRepository;
	@Autowired
	private CollectionRepository collectionRepository;
	@Autowired
	private IBookRepository bookRepository;
	
	
	@Value("${csv.file.location}")
	private File folder;

	
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
	    List<Book> books=new ArrayList<>();
        String title;
        
        if(sourceFile == null ) {
        	LOGGER.info("Echec de la lecture du fichier");
        } else {
	 
		    try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
		        for(String line; (line = br.readLine()) != null; ) {
		            
		        	Book book = new Book();
		            final String[] bookInformationsRow = line.split(";", -1);
		            
		            if(!"".equals(bookInformationsRow[0])) {
	            		book.setTitle(bookInformationsRow[0]);
	            	} else {
	            		book.setTitle("Nom inconnu");
	            	}
	            		
	            	
	            	String authorFullName = !"".equals(bookInformationsRow[1]) ? bookInformationsRow[1] : "Nom inconnu";
	        
        			Author author = this.authorRepository.getByFullName(authorFullName);
        			 if(author == null) {
        				author = new Author();
        				author.setFullName(authorFullName);
        				this.authorRepository.save(author);
        			}
        			book.setAuthor(author);	 
	            		
	            
            		String illustratorName = !"".equals(bookInformationsRow[2]) ? bookInformationsRow[2] : "Nom inconnu";
            		Illustrator illustrator = this.illustratorRepository.getByFullName(illustratorName);
            		
            		if(illustrator == null) {
            			illustrator = new Illustrator();
            			illustrator.setFullName(illustratorName);
            			this.illustratorRepository.save(illustrator);
            		} 
            		book.setIllustrator(illustrator);	
            		
        			String editorName = !"".equals(bookInformationsRow[3]) ? bookInformationsRow[3] : "Nom inconnu";
        			Editor editor = this.editorRepository.getByName(editorName);
        			if(editor == null) {
        				editor = new Editor();
        				editor.setName(editorName);
        				this.editorRepository.save(editor);
        			} 
        			book.setEditor(editor);
        		
        			String collectionName = !"".equals(bookInformationsRow[4]) ? bookInformationsRow[4] : "Nom inconnu";
        			Collection collection = this.collectionRepository.getByName(collectionName);
        			if(collection == null) {
        				collection = new Collection();
        				collection.setName(collectionName);
        				this.collectionRepository.save(collection);
        			} 
        			book.setCollection(collection);	
	        		
	        		this.bookRepository.createBook(book);
	        		
		            books.add(book);
		          
		            LOGGER.info("Livre crée avec succès. Titre: {} auteur: {}", book.getTitle(), book.getAuthor().getFullName());
		            LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
		            LOGGER.info("Collection: {}", book.getCollection().getName());
		        }
		        File processedFolder = new File(folder, "processed");
		        File destinationFile = new File(processedFolder.getPath() +"/" + sourceFile.getName());
		        moveFileToFolder(processedFolder, sourceFile, destinationFile);
		    } catch (IOException e) {
		    	LOGGER.error("Echec de la création du livre", e);
		    	File failedFolder = new File(folder, "failed");
		    	File destinationFile = new File(failedFolder.getPath() + "/" + sourceFile.getName());
		    	
		    	moveFileToFolder(failedFolder, sourceFile, destinationFile);
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
}
