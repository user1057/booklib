import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPageContent } from 'app/shared/model/page-content.model';

type EntityResponseType = HttpResponse<IPageContent>;
type EntityArrayResponseType = HttpResponse<IPageContent[]>;

@Injectable({ providedIn: 'root' })
export class PageContentService {
  public resourceUrl = SERVER_API_URL + 'api/page-contents';

  constructor(protected http: HttpClient) {}

  create(pageContent: IPageContent): Observable<EntityResponseType> {
    return this.http.post<IPageContent>(this.resourceUrl, pageContent, { observe: 'response' });
  }

  update(pageContent: IPageContent): Observable<EntityResponseType> {
    return this.http.put<IPageContent>(this.resourceUrl, pageContent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
