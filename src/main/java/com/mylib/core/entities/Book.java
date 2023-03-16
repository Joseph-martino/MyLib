package com.mylib.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String title;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "author_id")
	private Author author;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "illustrator_id")
	private Illustrator illustrator;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "editor_id")
	private Editor editor;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "collection_id")
	private Collection collection;
	
	
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
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Illustrator getIllustrator() {
		return illustrator;
	}
	public void setIllustrator(Illustrator illustrator) {
		this.illustrator = illustrator;
	}
	public Editor getEditor() {
		return editor;
	}
	public void setEditor(Editor editor) {
		this.editor = editor;
	}
	public Collection getCollection() {
		return collection;
	}
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	
	

}
