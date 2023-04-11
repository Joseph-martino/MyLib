import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-single-search-bar',
  templateUrl: './single-search-bar.component.html',
  styleUrls: ['./single-search-bar.component.scss']
})
export class SingleSearchBarComponent{
  @Output() infoSearched = new EventEmitter<string>();

  onEnter(value: string) { 
    this.infoSearched.emit(value);
  }
}
