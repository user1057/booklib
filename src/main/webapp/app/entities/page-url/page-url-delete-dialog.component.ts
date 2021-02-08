import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPageUrl } from 'app/shared/model/page-url.model';
import { PageUrlService } from './page-url.service';

@Component({
  templateUrl: './page-url-delete-dialog.component.html'
})
export class PageUrlDeleteDialogComponent {
  pageUrl?: IPageUrl;

  constructor(protected pageUrlService: PageUrlService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.pageUrlService.delete(id).subscribe(() => {
      this.eventManager.broadcast('pageUrlListModification');
      this.activeModal.close();
    });
  }
}
