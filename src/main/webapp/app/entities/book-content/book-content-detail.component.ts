import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IBookContent } from 'app/shared/model/book-content.model';

@Component({
  selector: 'jhi-book-content-detail',
  templateUrl: './book-content-detail.component.html'
})
export class BookContentDetailComponent implements OnInit {
  bookContent: IBookContent | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bookContent }) => (this.bookContent = bookContent));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
