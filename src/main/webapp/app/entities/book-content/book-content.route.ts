import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBookContent, BookContent } from 'app/shared/model/book-content.model';
import { BookContentService } from './book-content.service';
import { BookContentComponent } from './book-content.component';
import { BookContentDetailComponent } from './book-content-detail.component';
import { BookContentUpdateComponent } from './book-content-update.component';

@Injectable({ providedIn: 'root' })
export class BookContentResolve implements Resolve<IBookContent> {
  constructor(private service: BookContentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBookContent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bookContent: HttpResponse<BookContent>) => {
          if (bookContent.body) {
            return of(bookContent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BookContent());
  }
}

export const bookContentRoute: Routes = [
  {
    path: '',
    component: BookContentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BookContentDetailComponent,
    resolve: {
      bookContent: BookContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BookContentUpdateComponent,
    resolve: {
      bookContent: BookContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BookContentUpdateComponent,
    resolve: {
      bookContent: BookContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BookContents'
    },
    canActivate: [UserRouteAccessService]
  }
];
