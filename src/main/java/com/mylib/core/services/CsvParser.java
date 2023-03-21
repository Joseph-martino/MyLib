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
import com.mylib.core.repositories.IllustratorRepository;
import com.mylib.core.repositories.CollectionRepository;
import com.mylib.core.entities.Editor;
import com.mylib.core.entities.Collection;

@Component
public class CsvParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);
	
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	IllustratorRepository illustratorRepository;
	@Autowired
	EditorRepository editorRepository;
	@Autowired
	CollectionRepository collectionRepository;
	@Autowired
	BookRepository bookRepository;
	
	
	@Value("${csv.file.location}")
	private File folder;
	
	public File getFileFromFolder(File folder) {
		
		File file = null;
		File[] listOfFiles = folder.listFiles();
		for(File fileInFolder: listOfFiles) {
			if(!fileInFolder.exists() && !"bibliotheque.csv".equals(fileInFolder.getName())) {
				LOGGER.info("Le fichier n'existe pas ou n'a pas le nom approprié");
			} else {
				file = fileInFolder;
			}
		}
		return file;
	}
	
	@Scheduled(cron = "0 * * * * *")
	public List<Book> getBooksList() {
		
		
		File sourceFile = getFileFromFolder(folder);
		File destinationFile = new File("C://Users/Joseph/Documents/tutos/processed/" + sourceFile.getName());
		 
	    List<Book> books=new ArrayList<>();
        String title;
	 
	    try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
	        for(String line; (line = br.readLine()) != null; ) {
	            
	        	Book book = new Book();
	            final String[] bookInformationsRow = line.split(";", -1);
	            
	            if(!bookInformationsRow[0].isEmpty() || !"".equals(bookInformationsRow[0])) {
            		title = bookInformationsRow[0];
            	} else {
            		title = "titre inconnu";
            	}
            		
            	if(!bookInformationsRow[1].isEmpty() || !"".equals(bookInformationsRow[1])) {
            			String authorFullName = bookInformationsRow[1];
        
            			Author author = this.authorRepository.getByFullName(authorFullName);
            			if(author != null) {
            				book.setAuthor(author);
            				
            			} else {
            				author = new Author();
            				author.setFullName(authorFullName);
            				authorRepository.save(author);
            				book.setAuthor(author);
            			}
            			
            	} else {
            		Author author = this.authorRepository.getByFullName("Nom inconnu");
            		if(author != null) {
            			author = new Author();
            			author.setFullName(author.getFullName());
            			authorRepository.save(author);
            			book.setAuthor(author);
            		} else {
            			author = new Author();
            			author.setFullName("Nom inconnu");
            			authorRepository.save(author);
            			book.setAuthor(author);
            		}
            	}
            		
            	if(!bookInformationsRow[2].isEmpty() || !"".equals(bookInformationsRow[2])) {
            		String illustratorName = bookInformationsRow[2];
            		Illustrator illustrator = this.illustratorRepository.getByFullName(illustratorName);
            		if(illustrator != null) {
            			book.setIllustrator(illustrator);
            		} else {
            			illustrator = new Illustrator();
            			illustrator.setFullName(illustratorName);
            			illustratorRepository.save(illustrator);
            			book.setIllustrator(illustrator);
            		}
            	} else {
            		Illustrator illustrator = this.illustratorRepository.getByFullName("Nom inconnu");
            		if(illustrator != null) {
            			illustrator = new Illustrator();
            			illustrator.setFullName(illustrator.getFullName());
            			illustratorRepository.save(illustrator);
            			book.setIllustrator(illustrator);
            		} else {
            			illustrator = new Illustrator();
            			illustrator.setFullName("Nom inconnu");
            			illustratorRepository.save(illustrator);
            			book.setIllustrator(illustrator);
            		}
            	}
            		
        		if(!bookInformationsRow[3].isEmpty() || !"".equals(bookInformationsRow[3])) {
        			String editorName = bookInformationsRow[3];
        			Editor editor = this.editorRepository.getByName(editorName);
        			if(editor != null) {
        				book.setEditor(editor);
        			} else {
        				editor = new Editor();
        				editor.setName(editorName);
        				editorRepository.save(editor);
        				book.setEditor(editor);
        			}
        		} else {
        			Editor editor = this.editorRepository.getByName("Nom inconnu");
            		if(editor != null) {
            			editor = new Editor();
            			editor.setName(editor.getName());
            			editorRepository.save(editor);
            			book.setEditor(editor);
            		} else {
            			editor = new Editor();
            			editor.setName("Nom inconnu");
            			editorRepository.save(editor);
            			book.setEditor(editor);
            		}
        		}
        		
        		if(!bookInformationsRow[4].isEmpty() || !"".equals(bookInformationsRow[4])) {
        			String collectionName = bookInformationsRow[4];
        			Collection collection = this.collectionRepository.getByName(collectionName);
        			if(collection != null) {
        				book.setCollection(collection);
        			} else {
        				collection = new Collection();
        				collection.setName(collectionName);
        				collectionRepository.save(collection);
        				book.setCollection(collection);
        			}
        		} else {
        			Collection collection = this.collectionRepository.getByName("Nom inconnu");
            		if(collection != null) {
            			collection = new Collection();
            			collection.setName(collection.getName());
            			collectionRepository.save(collection);
            			book.setCollection(collection);
            		} else {
            			collection = new Collection();
            			collection.setName("Nom inconnu");
            			collectionRepository.save(collection);
            			book.setCollection(collection);
            		}
        		}	
        		
        		book.setTitle(title);
        		bookRepository.createBook(book);
        		
        		File processedFolder = new File("C://Users/Joseph/Documents/tutos/processed/");
        		
        		// methode qui deplace le fichier vers le dossier processed
        		if(!processedFolder.exists()) {
        			processedFolder.mkdir();
        			try {
        				Files.copy(sourceFile.toPath(), destinationFile.toPath(),StandardCopyOption.REPLACE_EXISTING);
        			} catch(IOException e) {
        				e.printStackTrace();
        			}
        			sourceFile.delete();
        		} else {
        			try {
        				Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        			} catch(IOException e) {
        				e.printStackTrace();
        			}
        			sourceFile.delete();
        		}
	            books.add(book);
	          
	            LOGGER.info("Livre crée avec succès. Titre: {} auteur + : {}", book.getTitle(), book.getAuthor().getFullName());
	            LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
	            LOGGER.info("Collection: {}", book.getCollection().getName());
	        }
	    } catch (IOException e) {
	    	// si ca marche pas fichier fails et envoyer fichier dedans
	    	LOGGER.error("Echec de la création du livre", e);
	    	File failedFolder = new File("C://Users/Joseph/Documents/tutos/failed/");
	    	
    		if(!failedFolder.exists()) {
    			failedFolder.mkdir();
    			try {
    				Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    			} catch(IOException ex) {
    				ex.printStackTrace();
    			}
    			sourceFile.delete();
    		} else {
    			try {
    				Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    			} catch(IOException ex) {
    				ex.printStackTrace();
    			}
    			sourceFile.delete();
    		}
	    }
	    return books;
	}

}
