import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPageUrl } from 'app/shared/model/page-url.model';

type EntityResponseType = HttpResponse<IPageUrl>;
type EntityArrayResponseType = HttpResponse<IPageUrl[]>;

@Injectable({ providedIn: 'root' })
export class PageUrlService {
  public resourceUrl = SERVER_API_URL + 'api/page-urls';

  constructor(protected http: HttpClient) {}

  create(pageUrl: IPageUrl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pageUrl);
    return this.http
      .post<IPageUrl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pageUrl: IPageUrl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pageUrl);
    return this.http
      .put<IPageUrl>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPageUrl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPageUrl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pageUrl: IPageUrl): IPageUrl {
    const copy: IPageUrl = Object.assign({}, pageUrl, {
      startTime: pageUrl.startTime && pageUrl.startTime.isValid() ? pageUrl.startTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startTime = res.body.startTime ? moment(res.body.startTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pageUrl: IPageUrl) => {
        pageUrl.startTime = pageUrl.startTime ? moment(pageUrl.startTime) : undefined;
      });
    }
    return res;
  }
}
