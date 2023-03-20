package com.mylib.core.repositories;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mylib.core.entities.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class AuthorRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	public void createAuthor(Author author) {
		this.entityManager.persist(author);
		
		System.out.println("L'auteur a été enregistré");
		
		
		
	}

}
