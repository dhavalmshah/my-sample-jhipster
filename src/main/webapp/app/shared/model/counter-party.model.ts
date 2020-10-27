import { ILocation } from 'app/shared/model/location.model';
import { CounterPartyType } from 'app/shared/model/enumerations/counter-party-type.model';

export interface ICounterParty {
  id?: number;
  name?: string;
  type?: CounterPartyType;
  notes?: string;
  locations?: ILocation[];
}

export class CounterParty implements ICounterParty {
  constructor(
    public id?: number,
    public name?: string,
    public type?: CounterPartyType,
    public notes?: string,
    public locations?: ILocation[]
  ) {}
}
