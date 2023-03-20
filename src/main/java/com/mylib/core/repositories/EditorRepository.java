package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;
import com.mylib.core.entities.Editor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EditorRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createEditor(Editor editor) {
		this.entityManager.persist(editor);
		
		System.out.println("L'éditeur a été enregistré");	
	}
	
	@Transactional
	public Editor getEditorByName(String name) {
		Editor editor = this.entityManager.find(Editor.class, name);
		return editor;
	}
	
	@Transactional
	public boolean checkIfEditorNameExist(String name) {
		Editor editor = getEditorByName(name);
		if(editor.getName().equals(name)) {
			return true;
		} else {
			return false;
		}
	}

}
