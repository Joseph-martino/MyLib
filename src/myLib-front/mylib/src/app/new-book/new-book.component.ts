import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { Book } from '../models/book.model';
import { BookService } from '../services/book.service';

@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.scss']
})
export class NewBookComponent implements OnInit{

  bookForm!: FormGroup;
  message!: string;
  success!: boolean;
  book: Book;

  constructor(private bookService: BookService){
      this.book = {
        id: -1,
        title: '',
        author: {
          fullName: ''
        },
        illustrator: {
          fullName:''
        },
        editor: {
          name:''
        },
        collection: {
          name:''
        }
      }
  }

  ngOnInit(): void {
    this.message = "Le livre a été crée avec succès";
    this.success = false;

    this.bookForm = new FormGroup({
      title: new FormControl(this.book.title, [Validators.required]),
      authorName: new FormControl(this.book.author.fullName, [Validators.required]),
      illustratorName: new FormControl(this.book.illustrator.fullName, [Validators.required]),
      editorName: new FormControl(this.book.editor.name, [Validators.required]),
      collectionName: new FormControl(this.book.collection.name, [Validators.required])
    });
  }

  get title() {
    return this.bookForm.get('title');
  }

  get authorName() {
    return this.bookForm.get('authorName');
  }

  get illustratorName() {
    return this.bookForm.get('illustratorName');
  }

  get editorName() {
    return this.bookForm.get('editorName');
  }

  get collectionName() {
    return this.bookForm.get('collectionName');
  }

  onSubmitForm():void{
    this.success = true;
    setTimeout(() => this.success = false, 3000);
    this.book.title =  this.title?.value;
    this.book.author.fullName =  this.authorName?.value;
    this.book.illustrator.fullName =  this.illustratorName?.value;
    this.book.editor.name =  this.editorName?.value;
    this.book.collection.name =  this.collectionName?.value;
    
    console.log(this.bookForm.value);
    this.bookService.addBook(this.bookForm.value).subscribe();
  }

}
