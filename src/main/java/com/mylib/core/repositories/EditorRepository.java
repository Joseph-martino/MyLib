package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

}
