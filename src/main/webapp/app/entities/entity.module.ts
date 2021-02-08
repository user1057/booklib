import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'book',
        loadChildren: () => import('./book/book.module').then(m => m.BooklibBookModule)
      },
      {
        path: 'book-content',
        loadChildren: () => import('./book-content/book-content.module').then(m => m.BooklibBookContentModule)
      },
      {
        path: 'page-url',
        loadChildren: () => import('./page-url/page-url.module').then(m => m.BooklibPageUrlModule)
      },
      {
        path: 'page-content',
        loadChildren: () => import('./page-content/page-content.module').then(m => m.BooklibPageContentModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class BooklibEntityModule {}
