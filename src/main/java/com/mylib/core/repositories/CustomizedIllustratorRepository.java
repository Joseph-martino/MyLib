package com.mylib.core.repositories;

import com.mylib.core.entities.Illustrator;

public interface CustomizedIllustratorRepository {
	Illustrator getIllustratorByNameAndStatus(String name);
	void changeIllustratorStatusToOk();
	void changeIllustratorWithStatusToDeleteToOk();
	void changeIllustratorStatusToToDelete();
	void deleteIllustratorWithStatusToDelete();
	void deleteIllustratorWithStatusInProgress();
	void deleteIllustratorWithStatusOk();
	void createIllustrator(Illustrator illustrator);
}
