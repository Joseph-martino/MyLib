package com.mylib.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.repositories.CollectionRepository;

@Service
public class CollectionService {
	@Autowired
	private CollectionRepository collectionRepository;
	
	public void changeCollectionStatutToOk() {
		this.collectionRepository.changeCollectionStatusToOk();	
	}
	
	public void changeCollectionStatusToToDelete() {
		this.collectionRepository.changeCollectionWithStatusToDeleteToOk();	
	}
	
	public void deleteCollectionWithStatusToDelete() {
		this.collectionRepository.deleteCollectionWithStatusToDelete();	
	}
	
	public void deleteCollectionWithStatusInProgress() {
		this.collectionRepository.deleteCollectionWithStatusInProgress();	
	}
	
	public void changeCollectionWithStatusToDeleteToOk() {
		this.collectionRepository.changeCollectionWithStatusToDeleteToOk();
	}
	
	public void deleteCollectionWithStatusOk() {
		this.collectionRepository.deleteCollectionWithStatusOk();
	}

}
