package com.mylib.core.repositories;

import com.mylib.core.entities.Collection;

public interface CustomizedCollectionRepository {
	
	Collection getCollectionByNameAndStatus(String name);
	void changeCollectionStatusToOk();
	void changeCollectionWithStatusToDeleteToOk();
	void changeCollectionStatusToToDelete();
	void deleteCollectionWithStatusToDelete();
	void deleteCollectionWithStatusInProgress();
	void deleteCollectionWithStatusOk();
	void createCollection(Collection collection);
}
