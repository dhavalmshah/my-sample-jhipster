import { IContact } from 'app/shared/model/contact.model';
import { ContactType } from 'app/shared/model/enumerations/contact-type.model';

export interface IPhoneNumber {
  id?: number;
  type?: ContactType;
  countryCode?: string;
  phoneNumber?: string;
  contact?: IContact;
}

export class PhoneNumber implements IPhoneNumber {
  constructor(
    public id?: number,
    public type?: ContactType,
    public countryCode?: string,
    public phoneNumber?: string,
    public contact?: IContact
  ) {}
}
