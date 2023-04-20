import { Component } from '@angular/core';
import { Book } from '../models/book.model';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, tap, map, lastValueFrom } from 'rxjs';
import { OnInit } from '@angular/core';
import { BookService } from '../services/book.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-single-book',
  templateUrl: './single-book.component.html',
  styleUrls: ['./single-book.component.scss'],
})
export class SingleBookComponent implements OnInit {
  book!: Book;
  snapId!: number;
  updateForm!: FormGroup;
  success!: boolean;
  isModified!: boolean;
  isDeleted!: boolean;

  constructor(
    private bookService: BookService,
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  async ngOnInit(): Promise<void> {
    this.success = true;
    this.isDeleted = false;
    this.isModified = false;
    this.snapId = +this.route.snapshot.params['id'];
    this.book = await lastValueFrom(this.bookService.getBookById(this.snapId));
    this.updateForm = new FormGroup({
      title: new FormControl(this.book.title, [Validators.required]),
      authorName: new FormControl(this.book.author.fullName, [Validators.required]),
      illustratorName: new FormControl(this.book.illustrator.fullName, [Validators.required]),
      editorName: new FormControl(this.book.editor.name, [Validators.required]),
      collectionName: new FormControl(this.book.collection.name, [Validators.required])
    });
  }

  get title() {
    return this.updateForm.get('title');
  }

  get authorName() {
    return this.updateForm.get('authorName');
  }

  get illustratorName() {
    return this.updateForm.get('illustratorName');
  }

  get editorName() {
    return this.updateForm.get('editorName');
  }

  get collectionName() {
    return this.updateForm.get('collectionName');
  }

  // //https://angular.io/guide/form-validation

  onDeleteBook(): void {
    this.isDeleted = true;
  }

  onUpdateBook(): void {
    this.isModified = true;
    //this.success = false;
  }

  onHide() {
    this.isModified = false;
  }

  onSubmitForm(): void {
    this.success = false;
    this.bookService
      .updateBook(this.snapId, this.updateForm.value)
      .pipe(tap(() => this.router.navigateByUrl(`/books/${this.snapId}`)))
      .subscribe();
    //setTimeout(() => (this.success = false), 3000);
  }

  onCloseModal(value: boolean) {
    //this.isDeleted = value;
  }

  onPreviousPage() {
    this.router.navigateByUrl('/books');
  }
}
