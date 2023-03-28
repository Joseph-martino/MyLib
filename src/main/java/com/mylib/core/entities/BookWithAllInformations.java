package com.mylib.core.entities;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "BOOKWITHALLINFORMATIONS")
public class BookWithAllInformations {
	
	@Id
	@Column(name="book_id")
	private long bookId;
	@Column(name="title")
	private String title;
	@Column(name="author_id")
	private long authorId;
	@Column(name="illustrator_id")
	private long illustratorId;
	@Column(name="editor_id")
	private long editorId;
	@Column(name="collection_id")
	private long collectionId;
	@Column(name="authorfullname")
	private String authorName;
	@Column(name="illustratorfullname")
	private String illustratorName;
	@Column(name="editorname")
	private String editorName;
	@Column(name="collectionname")
	private String collectionName;
	public long getBookId() {
		return bookId;
	}
	public void setBookId(long bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(long authorId) {
		this.authorId = authorId;
	}
	public long getIllustratorId() {
		return illustratorId;
	}
	public void setIllustratorId(long illustratorId) {
		this.illustratorId = illustratorId;
	}
	public long getEditorId() {
		return editorId;
	}
	public void setEditorId(long editorId) {
		this.editorId = editorId;
	}
	public long getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(long collectionId) {
		this.collectionId = collectionId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getIllustratorName() {
		return illustratorName;
	}
	public void setIllustratorName(String illustratorName) {
		this.illustratorName = illustratorName;
	}
	public String getEditorName() {
		return editorName;
	}
	public void setEditorName(String editorName) {
		this.editorName = editorName;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	
	
	


}
