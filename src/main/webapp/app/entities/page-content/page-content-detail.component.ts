import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPageContent } from 'app/shared/model/page-content.model';

@Component({
  selector: 'jhi-page-content-detail',
  templateUrl: './page-content-detail.component.html'
})
export class PageContentDetailComponent implements OnInit {
  pageContent: IPageContent | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageContent }) => (this.pageContent = pageContent));
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
