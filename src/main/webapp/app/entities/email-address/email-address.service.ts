import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmailAddress } from 'app/shared/model/email-address.model';

type EntityResponseType = HttpResponse<IEmailAddress>;
type EntityArrayResponseType = HttpResponse<IEmailAddress[]>;

@Injectable({ providedIn: 'root' })
export class EmailAddressService {
  public resourceUrl = SERVER_API_URL + 'api/email-addresses';

  constructor(protected http: HttpClient) {}

  create(emailAddress: IEmailAddress): Observable<EntityResponseType> {
    return this.http.post<IEmailAddress>(this.resourceUrl, emailAddress, { observe: 'response' });
  }

  update(emailAddress: IEmailAddress): Observable<EntityResponseType> {
    return this.http.put<IEmailAddress>(this.resourceUrl, emailAddress, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmailAddress>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmailAddress[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
