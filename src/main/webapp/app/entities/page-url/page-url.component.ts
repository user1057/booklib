import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageUrl } from 'app/shared/model/page-url.model';
import { PageUrlService } from './page-url.service';
import { PageUrlDeleteDialogComponent } from './page-url-delete-dialog.component';

@Component({
  selector: 'jhi-page-url',
  templateUrl: './page-url.component.html'
})
export class PageUrlComponent implements OnInit, OnDestroy {
  pageUrls?: IPageUrl[];
  eventSubscriber?: Subscription;

  constructor(protected pageUrlService: PageUrlService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.pageUrlService.query().subscribe((res: HttpResponse<IPageUrl[]>) => (this.pageUrls = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPageUrls();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPageUrl): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPageUrls(): void {
    this.eventSubscriber = this.eventManager.subscribe('pageUrlListModification', () => this.loadAll());
  }

  delete(pageUrl: IPageUrl): void {
    const modalRef = this.modalService.open(PageUrlDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pageUrl = pageUrl;
  }
}
