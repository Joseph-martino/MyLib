package com.mylib.core.repositories;

import com.mylib.core.entities.Editor;

public interface CustomizedEditorRepository {
	
	void changeEditorStatusToOk();
	void changeEditorWithStatusToDeleteToOk();
	void changeEditorStatusToToDelete();
	void deleteEditorWithStatusToDelete();
	void deleteEditorWithStatusInProgress();
	void deleteEditorWithStatusOk();
	void createEditor(Editor editor);
}
