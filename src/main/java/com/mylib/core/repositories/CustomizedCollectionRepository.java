package com.mylib.core.repositories;

public interface CustomizedCollectionRepository {
	
	void changeCollectionStatusToOk();
	void changeCollectionWithStatusToDeleteToOk();
	void changeCollectionStatusToToDelete();
	void deleteCollectionWithStatusToDelete();
	void deleteCollectionWithStatusInProgress();
	void deleteCollectionWithStatusOk();

}
