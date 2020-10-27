import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICounterParty } from 'app/shared/model/counter-party.model';

type EntityResponseType = HttpResponse<ICounterParty>;
type EntityArrayResponseType = HttpResponse<ICounterParty[]>;

@Injectable({ providedIn: 'root' })
export class CounterPartyService {
  public resourceUrl = SERVER_API_URL + 'api/counter-parties';

  constructor(protected http: HttpClient) {}

  create(counterParty: ICounterParty): Observable<EntityResponseType> {
    return this.http.post<ICounterParty>(this.resourceUrl, counterParty, { observe: 'response' });
  }

  update(counterParty: ICounterParty): Observable<EntityResponseType> {
    return this.http.put<ICounterParty>(this.resourceUrl, counterParty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICounterParty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICounterParty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
