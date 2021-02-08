import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPageUrl } from 'app/shared/model/page-url.model';

@Component({
  selector: 'jhi-page-url-detail',
  templateUrl: './page-url-detail.component.html'
})
export class PageUrlDetailComponent implements OnInit {
  pageUrl: IPageUrl | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageUrl }) => (this.pageUrl = pageUrl));
  }

  previousState(): void {
    window.history.back();
  }
}
