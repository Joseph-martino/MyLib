package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Illustrator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class IllustratorRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createIllustrator(Illustrator illustrator) {
		this.entityManager.persist(illustrator);
		
		System.out.println("L'illustrateur a été enregistré");	
	}

}
