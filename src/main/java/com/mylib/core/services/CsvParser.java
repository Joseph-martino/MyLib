package com.mylib.core.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CsvParser {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);
		
	@Value("${csv.file.location}")
	private File folder;
	
	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private IllustratorService illustratorService;
	@Autowired
	private EditorService editorService;
	@Autowired
	private CollectionService collectionService;
	
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

        	changeDataStatusToToDelete();
	 
		    try(BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {
		        for(String line; (line = br.readLine()) != null; ) {
		        	this.bookService.lineParser(line);      
		        }

		        changeDataStatusToOk();
		        deleteDataWithStatusToDelete();
		        
		        File processedFolder = new File(folder, "processed");
		        String fileNameWithoutExtension = getFileNameWithoutExtension(sourceFile.getName());
		        File destinationFile = new File(processedFolder.getPath() +"/" + fileNameWithoutExtension+ LocalDate.now() + ".csv");
		        moveFileToFolder(processedFolder, sourceFile, destinationFile);
		    } catch (IOException e) {
		    	LOGGER.error("Echec de la création du livre", e);
		    	
		    	deleteDataWithStatusInProgress();
		    	deleteDataWithStatusOk();
		    	changeDataStatusFromToDeleteToOk();
		    	
		    	File failedFolder = new File(folder, "failed");
		    	String fileNameWithoutExtension = getFileNameWithoutExtension(sourceFile.getName());
		    	File destinationFile = new File(failedFolder.getPath() + "/" + fileNameWithoutExtension + LocalDate.now() + ".csv");
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
	
	public String getFileNameWithoutExtension(String fileName) {
		int position = fileName.lastIndexOf('.');
		String fileNameWithoutExtension = (position == -1) ? fileName : fileName.substring(0, position);
		return fileNameWithoutExtension;
	}
	
	
	public void changeDataStatusToToDelete() {
		this.authorService.changeAuthorStatusToToDelete();
    	this.illustratorService.changeIllustratorStatusToToDelete();
    	this.editorService.changeEditorStatusToToDelete();
    	this.collectionService.changeCollectionStatusToToDelete();
    	this.bookService.changeBookStatusToToDelete();
	}
	
	
	public void changeDataStatusToOk() {
		this.bookService.changeBookStatutToOk();
        this.authorService.changeAuthorStatutToOk();
        this.illustratorService.changeIllustratorStatutToOk();
        this.editorService.changeEditorStatutToOk();
        this.collectionService.changeCollectionStatutToOk();
	}
	
	public void deleteDataWithStatusToDelete() {
		this.bookService.deleteBookWithStatusToDelete();
        this.authorService.deleteAuthorWithStatusToDelete();
        this.illustratorService.deleteIllustratorWithStatusToDelete();
        this.editorService.deleteEditorWithStatusToDelete();
        this.collectionService.deleteCollectionWithStatusToDelete();
	}
	
	@Transactional
	public void deleteDataWithStatusInProgress() {
		this.bookService.deleteBookWithStatusInProgress();
    	this.authorService.deleteAuthorWithStatusInProgress();
    	this.illustratorService.deleteIllustratorWithStatusInProgress();
    	this.editorService.deleteEditorWithStatusInProgress();
    	this.collectionService.deleteCollectionWithStatusInProgress();
	}
	
	@Transactional
	public void deleteDataWithStatusOk() {
		this.bookService.deleteBookWithStatusOk();
    	this.authorService.deleteAuthorWithStatusOk();
    	this.illustratorService.deleteIllustratorWithStatusOk();
    	this.editorService.deleteEditorWithStatusOk();
    	this.collectionService.deleteCollectionWithStatusOk();
	}
	
	@Transactional
	public void changeDataStatusFromToDeleteToOk() {
		this.bookService.changeBookWithStatusToDeleteToOk();
    	this.authorService.changeAuthorWithStatusToDeleteToOk();
    	this.illustratorService.changeIllustratorWithStatusToDeleteToOk();
    	this.editorService.changeEditorWithStatusToDeleteToOk();
    	this.collectionService.changeCollectionWithStatusToDeleteToOk();
	}
}
