package com.mylib.core.dto;

public class BookDto {
	
	private long id;
	private String title;
	private AuthorDto author;
	private IllustratorDto illustrator;
	private EditorDto editor;
	private CollectionDto collection;
	
	
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public AuthorDto getAuthor() {
		return author;
	}
	public void setAuthor(AuthorDto author) {
		this.author = author;
	}
	public IllustratorDto getIllustrator() {
		return illustrator;
	}
	public void setIllustrator(IllustratorDto illustrator) {
		this.illustrator = illustrator;
	}
	public EditorDto getEditor() {
		return editor;
	}
	public void setEditor(EditorDto editor) {
		this.editor = editor;
	}
	public CollectionDto getCollection() {
		return collection;
	}
	public void setCollection(CollectionDto collection) {
		this.collection = collection;
	}
	
	
	

}
