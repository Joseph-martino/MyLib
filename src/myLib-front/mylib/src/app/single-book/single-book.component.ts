import { Component } from '@angular/core';
import { Book } from '../models/book.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, tap, map } from 'rxjs';
import { OnInit } from '@angular/core';
import { BookService } from '../services/book.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
  success!: boolean;
  message!: string;
  title$!: Observable<string>;
  authorName$!: Observable<string>;
  illustratorName$!: Observable<string>;
  editorName$!: Observable<string>;
  collectionName$!: Observable<string>;


  constructor(private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder){
  }

  ngOnInit(): void {
    this.isModified = false;
    this.success =false;
    this.message = "Le livre a été modifié avec succès";

    this.snapId = +this.route.snapshot.params['id'];
    this.book$ = this.bookService.getBookById(this.snapId);
    this.title$ = this.book$.pipe(
      map(book => book.title),
    );

    this.authorName$ = this.book$.pipe(
      map(book => book.author.fullName),
    );

    this.illustratorName$ = this.book$.pipe(
      map(book => book.illustrator.fullName),
    );

    this.editorName$ = this.book$.pipe(
      map(book => book.editor.name),
    );

    this.collectionName$ = this.book$.pipe(
      map(book => book.collection.name),
    );
    this.updateForm = this.formBuilder.group({
      title: [null, Validators.required],
      authorName: [null, Validators.required],
      illustratorName: [null, Validators.required],
      editorName: [null, Validators.required],
      collectionName: [null, Validators.required],
    });
  }

  //https://angular.io/guide/form-validation

  onDeleteBook():void{
    this.bookService.deleteBook(this.snapId).pipe(
      tap(()=> this.router.navigateByUrl('/books'))
    ).subscribe();
    this.message = "Le livre a été supprimé";
    this.success = true;
    setTimeout(() => this.success = false, 3000);
  }

  onUpdateBook():void {
    this.isModified = true;
  }

  onHide(){
    this.isModified = false;
  }

  onSubmitForm():void {
    this.success = true;
    setTimeout(() => this.success = false, 2000);
    this.bookService.updateBook(this.snapId, this.updateForm.value).pipe(
      tap(() => this.router.navigateByUrl(`/books/${this.snapId}`))
    ).subscribe();
  }
}
