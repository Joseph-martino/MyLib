package com.mylib.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.entities.Author;
import com.mylib.core.repositories.AuthorRepository;

@Service
public class AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;
	
	public void changeAuthorStatutToOk() {
		this.authorRepository.changeAuthorStatusToOk();	
	}
	
	public void changeAuthorStatusToToDelete() {
		this.authorRepository.changeAuthorStatusToToDelete();	
	}
	
	public void deleteAuthorWithStatusToDelete() {
		this.authorRepository.deleteAuthorWithStatusToDelete();	
	}
	
	public void deleteAuthorWithStatusInProgress() {
		this.authorRepository.deleteAuthorWithStatusInProgress();	
	}
	
	public void changeAuthorWithStatusToDeleteToOk() {
		this.authorRepository.changeAuthorWithStatusToDeleteToOk();
	}
	
	public void deleteAuthorWithStatusOk() {
		this.authorRepository.deleteAuthorWithStatusOk();
	}
	
	public Author getAuthorByName(String name) {
		return this.authorRepository.getByFullName(name);
		
	}
}
