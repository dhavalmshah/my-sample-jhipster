import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';

type EntityResponseType = HttpResponse<IStockTransaction>;
type EntityArrayResponseType = HttpResponse<IStockTransaction[]>;

@Injectable({ providedIn: 'root' })
export class StockTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/stock-transactions';

  constructor(protected http: HttpClient) {}

  create(stockTransaction: IStockTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockTransaction);
    return this.http
      .post<IStockTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(stockTransaction: IStockTransaction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(stockTransaction);
    return this.http
      .put<IStockTransaction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IStockTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IStockTransaction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(stockTransaction: IStockTransaction): IStockTransaction {
    const copy: IStockTransaction = Object.assign({}, stockTransaction, {
      transactionDate:
        stockTransaction.transactionDate && stockTransaction.transactionDate.isValid()
          ? stockTransaction.transactionDate.toJSON()
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transactionDate = res.body.transactionDate ? moment(res.body.transactionDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((stockTransaction: IStockTransaction) => {
        stockTransaction.transactionDate = stockTransaction.transactionDate ? moment(stockTransaction.transactionDate) : undefined;
      });
    }
    return res;
  }
}
