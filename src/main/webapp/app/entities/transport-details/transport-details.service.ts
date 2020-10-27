import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITransportDetails } from 'app/shared/model/transport-details.model';

type EntityResponseType = HttpResponse<ITransportDetails>;
type EntityArrayResponseType = HttpResponse<ITransportDetails[]>;

@Injectable({ providedIn: 'root' })
export class TransportDetailsService {
  public resourceUrl = SERVER_API_URL + 'api/transport-details';

  constructor(protected http: HttpClient) {}

  create(transportDetails: ITransportDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportDetails);
    return this.http
      .post<ITransportDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(transportDetails: ITransportDetails): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(transportDetails);
    return this.http
      .put<ITransportDetails>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITransportDetails>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITransportDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(transportDetails: ITransportDetails): ITransportDetails {
    const copy: ITransportDetails = Object.assign({}, transportDetails, {
      transportStartDate:
        transportDetails.transportStartDate && transportDetails.transportStartDate.isValid()
          ? transportDetails.transportStartDate.toJSON()
          : undefined,
      estimatedEndDate:
        transportDetails.estimatedEndDate && transportDetails.estimatedEndDate.isValid()
          ? transportDetails.estimatedEndDate.toJSON()
          : undefined,
      actualEndDate:
        transportDetails.actualEndDate && transportDetails.actualEndDate.isValid() ? transportDetails.actualEndDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.transportStartDate = res.body.transportStartDate ? moment(res.body.transportStartDate) : undefined;
      res.body.estimatedEndDate = res.body.estimatedEndDate ? moment(res.body.estimatedEndDate) : undefined;
      res.body.actualEndDate = res.body.actualEndDate ? moment(res.body.actualEndDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((transportDetails: ITransportDetails) => {
        transportDetails.transportStartDate = transportDetails.transportStartDate ? moment(transportDetails.transportStartDate) : undefined;
        transportDetails.estimatedEndDate = transportDetails.estimatedEndDate ? moment(transportDetails.estimatedEndDate) : undefined;
        transportDetails.actualEndDate = transportDetails.actualEndDate ? moment(transportDetails.actualEndDate) : undefined;
      });
    }
    return res;
  }
}
