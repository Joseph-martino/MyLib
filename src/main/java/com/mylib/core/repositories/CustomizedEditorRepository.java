package com.mylib.core.repositories;

import com.mylib.core.entities.Editor;

public interface CustomizedEditorRepository {
	
	Editor getEditorByNameAndStatus(String name);
	void changeEditorStatusToOk();
	void changeEditorWithStatusToDeleteToOk();
	void changeEditorStatusToToDelete();
	void deleteEditorWithStatusToDelete();
	void deleteEditorWithStatusInProgress();
	void deleteEditorWithStatusOk();
	void createEditor(Editor editor);
}
