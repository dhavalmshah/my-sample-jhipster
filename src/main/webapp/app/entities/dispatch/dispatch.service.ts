import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDispatch } from 'app/shared/model/dispatch.model';

type EntityResponseType = HttpResponse<IDispatch>;
type EntityArrayResponseType = HttpResponse<IDispatch[]>;

@Injectable({ providedIn: 'root' })
export class DispatchService {
  public resourceUrl = SERVER_API_URL + 'api/dispatches';

  constructor(protected http: HttpClient) {}

  create(dispatch: IDispatch): Observable<EntityResponseType> {
    return this.http.post<IDispatch>(this.resourceUrl, dispatch, { observe: 'response' });
  }

  update(dispatch: IDispatch): Observable<EntityResponseType> {
    return this.http.put<IDispatch>(this.resourceUrl, dispatch, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDispatch>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDispatch[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
