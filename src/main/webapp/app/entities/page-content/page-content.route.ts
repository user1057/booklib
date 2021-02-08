import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPageContent, PageContent } from 'app/shared/model/page-content.model';
import { PageContentService } from './page-content.service';
import { PageContentComponent } from './page-content.component';
import { PageContentDetailComponent } from './page-content-detail.component';
import { PageContentUpdateComponent } from './page-content-update.component';

@Injectable({ providedIn: 'root' })
export class PageContentResolve implements Resolve<IPageContent> {
  constructor(private service: PageContentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageContent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pageContent: HttpResponse<PageContent>) => {
          if (pageContent.body) {
            return of(pageContent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PageContent());
  }
}

export const pageContentRoute: Routes = [
  {
    path: '',
    component: PageContentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PageContentDetailComponent,
    resolve: {
      pageContent: PageContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PageContentUpdateComponent,
    resolve: {
      pageContent: PageContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PageContentUpdateComponent,
    resolve: {
      pageContent: PageContentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageContents'
    },
    canActivate: [UserRouteAccessService]
  }
];
