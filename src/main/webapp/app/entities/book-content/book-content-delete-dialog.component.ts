import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBookContent } from 'app/shared/model/book-content.model';
import { BookContentService } from './book-content.service';

@Component({
  templateUrl: './book-content-delete-dialog.component.html'
})
export class BookContentDeleteDialogComponent {
  bookContent?: IBookContent;

  constructor(
    protected bookContentService: BookContentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bookContentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bookContentListModification');
      this.activeModal.close();
    });
  }
}
