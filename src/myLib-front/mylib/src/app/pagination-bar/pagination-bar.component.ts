import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-pagination-bar',
  templateUrl: './pagination-bar.component.html',
  styleUrls: ['./pagination-bar.component.scss']
})
export class PaginationBarComponent {

  @Input() currentPage = 1;
  @Input() totalPages = 1;
  @Input() size = 9;
  @Input() windowSize = 2;
  @Input() showFirstLastButton = true;
  @Input() routerLinkBase: string[] = [];

  getNavigablePages(): number[] {
    const pages = [];
    const left = Math.max(1, this.currentPage - this.windowSize);
    const right = Math.min(this.totalPages, this.currentPage + this.windowSize);
    for (let i = left; i <= right; i++) {
        pages.push(i);
    }
    return pages;
  }
}
