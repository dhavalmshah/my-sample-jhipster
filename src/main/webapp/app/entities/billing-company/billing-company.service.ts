import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBillingCompany } from 'app/shared/model/billing-company.model';

type EntityResponseType = HttpResponse<IBillingCompany>;
type EntityArrayResponseType = HttpResponse<IBillingCompany[]>;

@Injectable({ providedIn: 'root' })
export class BillingCompanyService {
  public resourceUrl = SERVER_API_URL + 'api/billing-companies';

  constructor(protected http: HttpClient) {}

  create(billingCompany: IBillingCompany): Observable<EntityResponseType> {
    return this.http.post<IBillingCompany>(this.resourceUrl, billingCompany, { observe: 'response' });
  }

  update(billingCompany: IBillingCompany): Observable<EntityResponseType> {
    return this.http.put<IBillingCompany>(this.resourceUrl, billingCompany, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBillingCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBillingCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
