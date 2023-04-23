package com.mylib.core.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;
import com.mylib.core.entities.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public interface CollectionRepository extends CrudRepository<Collection, Long>, CustomizedCollectionRepository{
	
	Collection getByName(String name);
		
	
	

}
