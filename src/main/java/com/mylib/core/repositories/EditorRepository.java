package com.mylib.core.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mylib.core.entities.Author;
import com.mylib.core.entities.Book;
import com.mylib.core.entities.Editor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


public interface EditorRepository extends CrudRepository<Editor, Long>{	
	
	Editor getByName(String name);
}
