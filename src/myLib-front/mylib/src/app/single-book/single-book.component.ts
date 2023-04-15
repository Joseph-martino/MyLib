import { Component } from '@angular/core';
import { Book } from '../models/book.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { OnInit } from '@angular/core';
import { BookService } from '../services/book.service';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-single-book',
  templateUrl: './single-book.component.html',
  styleUrls: ['./single-book.component.scss']
})
export class SingleBookComponent implements OnInit{

  book$!: Observable<Book>;
  snapId!: number;
  isModified!: boolean;
  updateForm!: FormGroup;
  bookPreview$!: Observable<Book>;


  constructor(private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder){
  }

  ngOnInit(): void {
    this.isModified = false;
    this.snapId = +this.route.snapshot.params['id'];
    this.book$ = this.bookService.getBookById(this.snapId);
    this.updateForm = this.formBuilder.group({
      title: [null],
      authorName: [null],
      illustratorName: [null],
      editorName: [null],
      collectionName: [null],
    });
  }

  onDeleteBook():void{
    this.bookService.deleteBook(this.snapId).pipe(
      tap(()=> this.router.navigateByUrl('/books'))
    ).subscribe();
  }

  onUpdateBook():void {
    this.isModified = true;
  }

  onHide(){
    this.isModified = false;
  }

  onSubmitForm():void {
    console.log(this.updateForm.value);
    this.bookService.updateBook(this.snapId, this.updateForm.value).pipe(
      tap(()=> this.router.navigateByUrl(`/books/${this.snapId}`))
    ).subscribe();
    
  }
}
