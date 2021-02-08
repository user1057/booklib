import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BooklibSharedModule } from 'app/shared/shared.module';
import { PageContentComponent } from './page-content.component';
import { PageContentDetailComponent } from './page-content-detail.component';
import { PageContentUpdateComponent } from './page-content-update.component';
import { PageContentDeleteDialogComponent } from './page-content-delete-dialog.component';
import { pageContentRoute } from './page-content.route';

@NgModule({
  imports: [BooklibSharedModule, RouterModule.forChild(pageContentRoute)],
  declarations: [PageContentComponent, PageContentDetailComponent, PageContentUpdateComponent, PageContentDeleteDialogComponent],
  entryComponents: [PageContentDeleteDialogComponent]
})
export class BooklibPageContentModule {}
