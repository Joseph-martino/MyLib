package com.mylib.core.repositories;

public interface CustomizedAuthorRepository {
	
	void changeAuthorStatusToOk();
	void changeAuthorWithStatusToDeleteToOk();
	void changeAuthorStatusToToDelete();
	void deleteAuthorWithStatusToDelete();
	void deleteAuthorWithStatusInProgress();
	void deleteAuthorWithStatusOk();

}
