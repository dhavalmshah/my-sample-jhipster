import { IAddress } from 'app/shared/model/address.model';
import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { IEmailAddress } from 'app/shared/model/email-address.model';

export interface IContact {
  id?: number;
  firstName?: string;
  lastName?: string;
  designation?: string;
  notes?: string;
  address?: IAddress;
  phones?: IPhoneNumber[];
  emails?: IEmailAddress[];
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public designation?: string,
    public notes?: string,
    public address?: IAddress,
    public phones?: IPhoneNumber[],
    public emails?: IEmailAddress[]
  ) {}
}
