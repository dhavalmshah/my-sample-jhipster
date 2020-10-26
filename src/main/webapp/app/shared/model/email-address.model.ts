import { IContact } from 'app/shared/model/contact.model';
import { ContactType } from 'app/shared/model/enumerations/contact-type.model';

export interface IEmailAddress {
  id?: number;
  type?: ContactType;
  emailAddress?: string;
  contact?: IContact;
}

export class EmailAddress implements IEmailAddress {
  constructor(public id?: number, public type?: ContactType, public emailAddress?: string, public contact?: IContact) {}
}
