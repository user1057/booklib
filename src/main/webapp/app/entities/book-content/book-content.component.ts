import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBookContent } from 'app/shared/model/book-content.model';
import { BookContentService } from './book-content.service';
import { BookContentDeleteDialogComponent } from './book-content-delete-dialog.component';

@Component({
  selector: 'jhi-book-content',
  templateUrl: './book-content.component.html'
})
export class BookContentComponent implements OnInit, OnDestroy {
  bookContents?: IBookContent[];
  eventSubscriber?: Subscription;

  constructor(
    protected bookContentService: BookContentService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.bookContentService.query().subscribe((res: HttpResponse<IBookContent[]>) => (this.bookContents = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBookContents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBookContent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInBookContents(): void {
    this.eventSubscriber = this.eventManager.subscribe('bookContentListModification', () => this.loadAll());
  }

  delete(bookContent: IBookContent): void {
    const modalRef = this.modalService.open(BookContentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.bookContent = bookContent;
  }
}
