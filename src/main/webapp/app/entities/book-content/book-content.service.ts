import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBookContent } from 'app/shared/model/book-content.model';

type EntityResponseType = HttpResponse<IBookContent>;
type EntityArrayResponseType = HttpResponse<IBookContent[]>;

@Injectable({ providedIn: 'root' })
export class BookContentService {
  public resourceUrl = SERVER_API_URL + 'api/book-contents';

  constructor(protected http: HttpClient) {}

  create(bookContent: IBookContent): Observable<EntityResponseType> {
    return this.http.post<IBookContent>(this.resourceUrl, bookContent, { observe: 'response' });
  }

  update(bookContent: IBookContent): Observable<EntityResponseType> {
    return this.http.put<IBookContent>(this.resourceUrl, bookContent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBookContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBookContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
