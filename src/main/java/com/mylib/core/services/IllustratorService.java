package com.mylib.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mylib.core.repositories.IllustratorRepository;

@Service
public class IllustratorService {
	@Autowired
	private IllustratorRepository illustratorRepository;
	
	public void changeIllustratorStatutToOk() {
		this.illustratorRepository.changeIllustratorStatusToOk();	
	}
	
	public void changeIllustratorStatusToToDelete() {
		this.illustratorRepository.changeIllustratorWithStatusToDeleteToOk();	
	}
	
	public void deleteIllustratorWithStatusToDelete() {
		this.illustratorRepository.deleteIllustratorWithStatusToDelete();	
	}
	
	public void deleteIllustratorWithStatusInProgress() {
		this.illustratorRepository.deleteIllustratorWithStatusInProgress();	
	}
	
	public void changeIllustratorWithStatusToDeleteToOk() {
		this.illustratorRepository.changeIllustratorWithStatusToDeleteToOk();
	}
	
	public void deleteIllustratorWithStatusOk() {
		this.illustratorRepository.deleteIllustratorWithStatusOk();
	}

}
