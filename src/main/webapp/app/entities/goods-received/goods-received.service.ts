import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGoodsReceived } from 'app/shared/model/goods-received.model';

type EntityResponseType = HttpResponse<IGoodsReceived>;
type EntityArrayResponseType = HttpResponse<IGoodsReceived[]>;

@Injectable({ providedIn: 'root' })
export class GoodsReceivedService {
  public resourceUrl = SERVER_API_URL + 'api/goods-receiveds';

  constructor(protected http: HttpClient) {}

  create(goodsReceived: IGoodsReceived): Observable<EntityResponseType> {
    return this.http.post<IGoodsReceived>(this.resourceUrl, goodsReceived, { observe: 'response' });
  }

  update(goodsReceived: IGoodsReceived): Observable<EntityResponseType> {
    return this.http.put<IGoodsReceived>(this.resourceUrl, goodsReceived, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGoodsReceived>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGoodsReceived[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
