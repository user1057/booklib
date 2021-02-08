import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPageUrl, PageUrl } from 'app/shared/model/page-url.model';
import { PageUrlService } from './page-url.service';
import { PageUrlComponent } from './page-url.component';
import { PageUrlDetailComponent } from './page-url-detail.component';
import { PageUrlUpdateComponent } from './page-url-update.component';

@Injectable({ providedIn: 'root' })
export class PageUrlResolve implements Resolve<IPageUrl> {
  constructor(private service: PageUrlService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPageUrl> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((pageUrl: HttpResponse<PageUrl>) => {
          if (pageUrl.body) {
            return of(pageUrl.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PageUrl());
  }
}

export const pageUrlRoute: Routes = [
  {
    path: '',
    component: PageUrlComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageUrls'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PageUrlDetailComponent,
    resolve: {
      pageUrl: PageUrlResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageUrls'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PageUrlUpdateComponent,
    resolve: {
      pageUrl: PageUrlResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageUrls'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PageUrlUpdateComponent,
    resolve: {
      pageUrl: PageUrlResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'PageUrls'
    },
    canActivate: [UserRouteAccessService]
  }
];
