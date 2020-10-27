import { IContact } from 'app/shared/model/contact.model';
import { ICounterParty } from 'app/shared/model/counter-party.model';

export interface ILocation {
  id?: number;
  name?: string;
  panNumber?: string;
  gstNumber?: string;
  contact?: IContact;
  counterParty?: ICounterParty;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public name?: string,
    public panNumber?: string,
    public gstNumber?: string,
    public contact?: IContact,
    public counterParty?: ICounterParty
  ) {}
}
