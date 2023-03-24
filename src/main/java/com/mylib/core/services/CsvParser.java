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
					LOGGER.info("Le fichier n'existe pas ou n'a pas le nom approprié");
				}
			}
			return file;	
		} 
		LOGGER.info("Le fichier n'existe pas");	
		return null;
	}
	
	@Scheduled(cron = "0 * * * * *")
	public List<Book> getBooksList() {
		
		File sourceFile = getFileFromFolder(folder);
	    List<Book> books=new ArrayList<>();
        String title;
        
        //gérer le fait que sourceFile puisse être null
        if(sourceFile == null ) {
        	LOGGER.info("Echec de la lecture du fichier");
        	return null;
        } else {
	 
		    try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
		        for(String line; (line = br.readLine()) != null; ) {
		            
		        	Book book = new Book();
		            final String[] bookInformationsRow = line.split(";", -1);
		            
		            if(!"".equals(bookInformationsRow[0])) {
	            		title = bookInformationsRow[0];
	            	} else {
	            		title = "titre inconnu";
	            	}
	            		
	            	if(!"".equals(bookInformationsRow[1])) {
	            			String authorFullName = bookInformationsRow[1];
	        
	            			Author author = this.authorRepository.getByFullName(authorFullName);
	            			 if(author == null) {
	            				author = new Author();
	            				author.setFullName(authorFullName);
	            				this.authorRepository.save(author);
	            			}
	            			book.setAuthor(author);
	            			
	            	} else {
	            		Author author = this.authorRepository.getByFullName("Nom inconnu");
	            		if(author == null) {
	            			author = new Author();
	            			author.setFullName("Nom inconnu");
	            			authorRepository.save(author);
	            		} 
	            		book.setAuthor(author);
	            	}
	            		
	            	if(!"".equals(bookInformationsRow[2])) {
	            		String illustratorName = bookInformationsRow[2];
	            		Illustrator illustrator = this.illustratorRepository.getByFullName(illustratorName);
	            		
	            		if(illustrator == null) {
	            			illustrator = new Illustrator();
	            			illustrator.setFullName(illustratorName);
	            			this.illustratorRepository.save(illustrator);
	            		} 
	            		book.setIllustrator(illustrator);	
	            	} else {
	            		Illustrator illustrator = this.illustratorRepository.getByFullName("Nom inconnu");
	            		if(illustrator == null) {
	            			illustrator = new Illustrator();
	            			illustrator.setFullName("Nom inconnu");
	            			this.illustratorRepository.save(illustrator);
	            		} 
	            		book.setIllustrator(illustrator);
	            	}
	            		
	        		if(!"".equals(bookInformationsRow[3])) {
	        			String editorName = bookInformationsRow[3];
	        			Editor editor = this.editorRepository.getByName(editorName);
	        			if(editor == null) {
	        				editor = new Editor();
	        				editor.setName(editorName);
	        				this.editorRepository.save(editor);
	        			} 
	        			book.setEditor(editor);
	        		} else {
	        			Editor editor = this.editorRepository.getByName("Nom inconnu");
	            		if(editor == null) {
	            			editor = new Editor();
	            			editor.setName("Nom inconnu");
	            			this.editorRepository.save(editor);
	            		} 
	            		book.setEditor(editor);
	        		}
	        		
	        		if(!"".equals(bookInformationsRow[4])) {
	        			String collectionName = bookInformationsRow[4];
	        			Collection collection = this.collectionRepository.getByName(collectionName);
	        			if(collection == null) {
	        				collection = new Collection();
	        				collection.setName(collectionName);
	        				this.collectionRepository.save(collection);
	        			} 
	        			book.setCollection(collection);
	        			
	        		} else {
	        			Collection collection = this.collectionRepository.getByName("Nom inconnu");
	            		if(collection == null) {
	            			collection = new Collection();
	            			collection.setName("Nom inconnu");
	            			this.collectionRepository.save(collection);
	            		} 
	            		book.setCollection(collection);
	        		}	
	        		
	        		book.setTitle(title);
	        		this.bookRepository.createBook(book);
	        		
	        		File processedFolder = new File(folder, "processed");
	        		File destinationFile = new File(processedFolder.getPath() +"//" + sourceFile.getName());
	        		
	        		moveFileToFolder(processedFolder, sourceFile, destinationFile);
		            books.add(book);
		          
		            LOGGER.info("Livre crée avec succès. Titre: {} auteur + : {}", book.getTitle(), book.getAuthor().getFullName());
		            LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
		            LOGGER.info("Collection: {}", book.getCollection().getName());
		        }
		    } catch (IOException e) {
		    	LOGGER.error("Echec de la création du livre", e);
		    	File failedFolder = new File(folder, "failed");
		    	File destinationFile = new File(failedFolder.getPath() + "//" + sourceFile.getName());
		    	
		    	moveFileToFolder(failedFolder, sourceFile, destinationFile);
		    }
        }
        return books;
	}
	
	
	
	public void moveFileToFolder(File folder, File sourceFile, File destinationFile) {
		
		if(!folder.exists()) {
			folder.mkdir();
			copyFile(sourceFile, destinationFile);
			sourceFile.delete();
		} else {
			copyFile(sourceFile, destinationFile);
			sourceFile.delete();
		}
	}
	
	public void copyFile(File sourceFile, File destinationFile) {
		try {
			Files.copy(sourceFile.toPath(), destinationFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
