import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BooklibSharedModule } from 'app/shared/shared.module';
import { PageUrlComponent } from './page-url.component';
import { PageUrlDetailComponent } from './page-url-detail.component';
import { PageUrlUpdateComponent } from './page-url-update.component';
import { PageUrlDeleteDialogComponent } from './page-url-delete-dialog.component';
import { pageUrlRoute } from './page-url.route';

@NgModule({
  imports: [BooklibSharedModule, RouterModule.forChild(pageUrlRoute)],
  declarations: [PageUrlComponent, PageUrlDetailComponent, PageUrlUpdateComponent, PageUrlDeleteDialogComponent],
  entryComponents: [PageUrlDeleteDialogComponent]
})
export class BooklibPageUrlModule {}
