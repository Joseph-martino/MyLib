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
	
	@Transactional
	public Author getAuthorByName(String name) {
		Author author = this.entityManager.find(Author.class, name);
		return author;
	}
	
	@Transactional
	public boolean checkIfAuthorNameExist(String name) {
		Author author = getAuthorByName(name);
		if(name.equals(author.getFullName())) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
