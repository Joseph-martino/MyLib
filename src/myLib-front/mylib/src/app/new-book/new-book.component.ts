import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
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

  constructor(private bookService: BookService,
    private formBuilder: FormBuilder){
  }

  ngOnInit(): void {
    this.bookForm = this.formBuilder.group(
      {
        title: [null],
        authorName: [null],
        illustratorName: [null],
        editorName: [null],
        collectionName: [null],
      }
    );
  }

  onSubmitForm():void{
    console.log(this.bookForm.value);
    this.bookService.addBook(this.bookForm.value).subscribe();
  }

}
