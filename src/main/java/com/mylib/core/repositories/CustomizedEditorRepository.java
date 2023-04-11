package com.mylib.core.repositories;

public interface CustomizedEditorRepository {
	
	void changeEditorStatusToOk();
	void changeEditorWithStatusToDeleteToOk();
	void changeEditorStatusToToDelete();
	void deleteEditorWithStatusToDelete();
	void deleteEditorWithStatusInProgress();
	void deleteEditorWithStatusOk();

}
