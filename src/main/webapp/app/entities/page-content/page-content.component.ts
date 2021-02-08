import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPageContent } from 'app/shared/model/page-content.model';
import { PageContentService } from './page-content.service';
import { PageContentDeleteDialogComponent } from './page-content-delete-dialog.component';

@Component({
  selector: 'jhi-page-content',
  templateUrl: './page-content.component.html'
})
export class PageContentComponent implements OnInit, OnDestroy {
  pageContents?: IPageContent[];
  eventSubscriber?: Subscription;

  constructor(
    protected pageContentService: PageContentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.pageContentService.query().subscribe((res: HttpResponse<IPageContent[]>) => (this.pageContents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPageContents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPageContent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInPageContents(): void {
    this.eventSubscriber = this.eventManager.subscribe('pageContentListModification', () => this.loadAll());
  }

  delete(pageContent: IPageContent): void {
    const modalRef = this.modalService.open(PageContentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.pageContent = pageContent;
  }
}
