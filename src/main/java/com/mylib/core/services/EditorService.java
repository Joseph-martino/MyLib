package com.mylib.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.repositories.EditorRepository;

@Service
public class EditorService {
	@Autowired
	private EditorRepository editorRepository;
	
	public void changeEditorStatutToOk() {
		this.editorRepository.changeEditorStatusToOk();	
	}
	
	public void changeEditorStatusToToDelete() {
		this.editorRepository.changeEditorStatusToToDelete();	
	}
	
	public void deleteEditorWithStatusToDelete() {
		this.editorRepository.deleteEditorWithStatusToDelete();	
	}
	
	public void deleteEditorWithStatusInProgress() {
		this.editorRepository.deleteEditorWithStatusInProgress();	
	}
	
	public void changeEditorWithStatusToDeleteToOk() {
		this.editorRepository.changeEditorWithStatusToDeleteToOk();
	}
	
	public void deleteEditorWithStatusOk() {
		this.editorRepository.deleteEditorWithStatusOk();
	}

}
