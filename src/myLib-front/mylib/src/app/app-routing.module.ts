import { NgModule } from "@angular/core";
import { RouterModule,Routes } from "@angular/router";
import { BookListComponent } from "./book-list/book-list.component";
import { BookComponent } from "./book/book.component";
import { LandingPageComponent } from "./landing-page/landing-page.component";
import { NewBookComponent } from "./new-book/new-book.component";
import { SingleBookComponent } from "./single-book/single-book.component";

const routes: Routes = [
    {path: 'books/:id', component: SingleBookComponent},
    {path: 'books', component: BookListComponent},
    { path: 'create', component: NewBookComponent},
    {path: '', component: LandingPageComponent}
    
]

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],

    exports: [
        RouterModule
    ]
})
export class AppRoutingModule {

}