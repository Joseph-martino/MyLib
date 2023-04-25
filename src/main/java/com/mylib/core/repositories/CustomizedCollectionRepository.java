package com.mylib.core.repositories;

import com.mylib.core.entities.Collection;

public interface CustomizedCollectionRepository {
	
	void changeCollectionStatusToOk();
	void changeCollectionWithStatusToDeleteToOk();
	void changeCollectionStatusToToDelete();
	void deleteCollectionWithStatusToDelete();
	void deleteCollectionWithStatusInProgress();
	void deleteCollectionWithStatusOk();
	void createCollection(Collection collection);
}
