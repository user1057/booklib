import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BooklibSharedModule } from 'app/shared/shared.module';
import { BookContentComponent } from './book-content.component';
import { BookContentDetailComponent } from './book-content-detail.component';
import { BookContentUpdateComponent } from './book-content-update.component';
import { BookContentDeleteDialogComponent } from './book-content-delete-dialog.component';
import { bookContentRoute } from './book-content.route';

@NgModule({
  imports: [BooklibSharedModule, RouterModule.forChild(bookContentRoute)],
  declarations: [BookContentComponent, BookContentDetailComponent, BookContentUpdateComponent, BookContentDeleteDialogComponent],
  entryComponents: [BookContentDeleteDialogComponent]
})
export class BooklibBookContentModule {}
