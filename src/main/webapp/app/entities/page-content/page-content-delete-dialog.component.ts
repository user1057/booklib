import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPageContent } from 'app/shared/model/page-content.model';
import { PageContentService } from './page-content.service';

@Component({
  templateUrl: './page-content-delete-dialog.component.html'
})
export class PageContentDeleteDialogComponent {
  pageContent?: IPageContent;

  constructor(
    protected pageContentService: PageContentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageContentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pageContentListModification');
      this.activeModal.close();
    });
  }
}
