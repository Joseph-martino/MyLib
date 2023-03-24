package com.mylib.core.repositories;

import org.springframework.data.repository.CrudRepository;
import com.mylib.core.entities.Author;


public interface AuthorRepository extends CrudRepository<Author, Long>{
	
	Author getByFullName(String fullName);
}
