import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
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

  constructor(private bookService: BookService,
    private formBuilder: FormBuilder){
  }

  ngOnInit(): void {
    this.message = "Le livre a été crée avec succès";
    this.success = false;
    this.bookForm = this.formBuilder.group(
      {
        title: [null, Validators.required],
        authorName: [null, Validators.required],
        illustratorName: [null, Validators.required],
        editorName: [null, Validators.required],
        collectionName: [null, Validators.required],
      }
    );
  }

  onSubmitForm():void{
    this.bookService.addBook(this.bookForm.value).subscribe();
    this.success = true;
    setTimeout(() => this.success = false, 3000);
  }

}
