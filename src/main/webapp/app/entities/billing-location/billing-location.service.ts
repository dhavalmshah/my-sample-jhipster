import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBillingLocation } from 'app/shared/model/billing-location.model';

type EntityResponseType = HttpResponse<IBillingLocation>;
type EntityArrayResponseType = HttpResponse<IBillingLocation[]>;

@Injectable({ providedIn: 'root' })
export class BillingLocationService {
  public resourceUrl = SERVER_API_URL + 'api/billing-locations';

  constructor(protected http: HttpClient) {}

  create(billingLocation: IBillingLocation): Observable<EntityResponseType> {
    return this.http.post<IBillingLocation>(this.resourceUrl, billingLocation, { observe: 'response' });
  }

  update(billingLocation: IBillingLocation): Observable<EntityResponseType> {
    return this.http.put<IBillingLocation>(this.resourceUrl, billingLocation, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillingLocation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillingLocation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
