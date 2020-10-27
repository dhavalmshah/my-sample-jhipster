import { Moment } from 'moment';
import { ICounterParty } from 'app/shared/model/counter-party.model';

export interface ITransportDetails {
  id?: number;
  transportNumber?: string;
  transportStartDate?: Moment;
  estimatedEndDate?: Moment;
  actualEndDate?: Moment;
  transporter?: ICounterParty;
}

export class TransportDetails implements ITransportDetails {
  constructor(
    public id?: number,
    public transportNumber?: string,
    public transportStartDate?: Moment,
    public estimatedEndDate?: Moment,
    public actualEndDate?: Moment,
    public transporter?: ICounterParty
  ) {}
}
