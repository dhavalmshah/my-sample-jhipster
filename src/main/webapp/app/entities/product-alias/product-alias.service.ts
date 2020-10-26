import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductAlias } from 'app/shared/model/product-alias.model';

type EntityResponseType = HttpResponse<IProductAlias>;
type EntityArrayResponseType = HttpResponse<IProductAlias[]>;

@Injectable({ providedIn: 'root' })
export class ProductAliasService {
  public resourceUrl = SERVER_API_URL + 'api/product-aliases';

  constructor(protected http: HttpClient) {}

  create(productAlias: IProductAlias): Observable<EntityResponseType> {
    return this.http.post<IProductAlias>(this.resourceUrl, productAlias, { observe: 'response' });
  }

  update(productAlias: IProductAlias): Observable<EntityResponseType> {
    return this.http.put<IProductAlias>(this.resourceUrl, productAlias, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductAlias>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductAlias[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
