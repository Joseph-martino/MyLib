package com.mylib.core.repositories;

import com.mylib.core.entities.Author;

public interface CustomizedAuthorRepository {
	
	Author getAuthorByNameAndStatus(String name);
	void changeAuthorStatusToOk();
	void changeAuthorWithStatusToDeleteToOk();
	void changeAuthorStatusToToDelete();
	void deleteAuthorWithStatusToDelete();
	void deleteAuthorWithStatusInProgress();
	void deleteAuthorWithStatusOk();
	void createAuthor(Author author);

}
