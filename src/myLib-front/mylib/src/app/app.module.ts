import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { BookListComponent } from './book-list/book-list.component';
import { HttpClientModule } from '@angular/common/http';
import { BookComponent } from './book/book.component';
import { SingleBookComponent } from './single-book/single-book.component';
import { HeaderComponent } from './header/header.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { NewBookComponent } from './new-book/new-book.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { SingleSearchBarComponent } from './single-search-bar/single-search-bar.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { PaginationBarComponent } from './pagination-bar/pagination-bar.component';
import { LoaderComponent } from './loader/loader.component';
import { ToastComponent } from './toast/toast.component';
import { ModalComponent } from './modal/modal.component';

@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookComponent,
    SingleBookComponent,
    HeaderComponent,
    LandingPageComponent,
    NewBookComponent,
    SingleSearchBarComponent,
    PaginationBarComponent,
    LoaderComponent,
    ToastComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatProgressSpinnerModule,
    NgxPaginationModule
  ],
  exports: [
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
