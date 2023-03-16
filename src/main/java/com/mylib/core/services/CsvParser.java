package com.mylib.core.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mylib.core.entities.Book;
import com.mylib.core.entities.Author;
import com.mylib.core.entities.Illustrator;
import com.mylib.core.entities.Editor;
import com.mylib.core.entities.Collection;

@Component
public class CsvParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);
	
	@Value("${csv.file.location}")
	private File file;
	
	@Scheduled(cron = "0 * * * * *")
	public List<Book> getBooksList() {
		 
	    List<Book> books=new ArrayList<>();
        String title;
        
        Map<String, Author> authors = new HashMap<>();
        Map<String, Illustrator> illustrators = new HashMap<>();
        Map<String, Editor> editors = new HashMap<>();
        Map<String, Collection> collections = new HashMap<>();
	 
	    try(BufferedReader br = new BufferedReader(new FileReader(file))) {
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
            			
            			if(authors.containsKey(authorFullName)) {
            				//on appelera la base de donnée plutot que le containKey
            				Author author = authors.get(authorFullName);
            				book.setAuthor(author);
            			} else {
            				Author author = new Author();
            				author.setFullName(authorFullName);
            				authors.put(authorFullName, author);
            				book.setAuthor(author);
            				//persist author
            			}
            	} else {
            		Author author = new Author();
            		author.setFullName("Nom inconnu");
            		book.setAuthor(author);
            	}
            		
            	if(!bookInformationsRow[2].isEmpty() || !"".equals(bookInformationsRow[2])) {
            		String illustratorName = bookInformationsRow[2];
            		if(illustrators.containsKey(illustratorName)) {
            			Illustrator illustrator = illustrators.get(illustratorName);
            			book.setIllustrator(illustrator);
            		} else {
            			Illustrator illustrator = new Illustrator();
            			illustrator.setFullName(illustratorName);
            			illustrators.put(illustratorName, illustrator);
            			book.setIllustrator(illustrator);
            		}
            	} else {
            		Illustrator illustrator = new Illustrator();
            		illustrator.setFullName("Nom inconnu");
            		book.setIllustrator(illustrator);
            	}
            		
        		if(!bookInformationsRow[3].isEmpty() || !"".equals(bookInformationsRow[3])) {
        			String editorName = bookInformationsRow[3];
        			if(editors.containsKey(editorName)) {
        				Editor editor = editors.get(editorName);
        				book.setEditor(editor);
        			} else {
        				Editor editor = new Editor();
        				editor.setName(editorName);
        				editors.put(editorName, editor);
        				book.setEditor(editor);
        			}
        		} else {
        			Editor editor = new Editor();
        			editor.setName("Nom inconnu");
        			book.setEditor(editor);;
        		}
        		
        		if(!bookInformationsRow[4].isEmpty() || !"".equals(bookInformationsRow[4])) {
        			String collectionName = bookInformationsRow[4];
        			if(collections.containsKey(collectionName)) {
        				Collection collection = collections.get(collectionName);
        				book.setCollection(collection);
        			} else {
        				Collection collection = new Collection();
        				collection.setName(collectionName);
        				collections.put(collectionName, collection);
        				book.setCollection(collection);
        			}
        		} else {
        			Collection collection = new Collection();
        			collection.setName("Nom inconnu");
        			book.setCollection(collection);
        		}	
        		
        		book.setTitle(title);
        		//book.persist
        		
	            books.add(book);
	          
	            LOGGER.info("Livre crée avec succès. Titre: {} auteur + : {}", book.getTitle(), book.getAuthor().getFullName());
	            LOGGER.info("Illustrator: {} , Editeur: {}", book.getIllustrator().getFullName(), book.getEditor().getName());
	            LOGGER.info("Collection: {}", book.getCollection().getName());
	        }
	    } catch (IOException e) {
	    	LOGGER.error("Echec de la création du livre", e);
	    }
	    return books;
	}

}
