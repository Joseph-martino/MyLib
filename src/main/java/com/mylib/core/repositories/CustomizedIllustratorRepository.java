package com.mylib.core.repositories;

public interface CustomizedIllustratorRepository {
	void changeIllustratorStatusToOk();
	void changeIllustratorWithStatusToDeleteToOk();
	void changeIllustratorStatusToToDelete();
	void deleteIllustratorWithStatusToDelete();
	void deleteIllustratorWithStatusInProgress();
	void deleteIllustratorWithStatusOk();

}
