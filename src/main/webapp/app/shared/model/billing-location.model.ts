import { IContact } from 'app/shared/model/contact.model';
import { IBillingCompany } from 'app/shared/model/billing-company.model';

export interface IBillingLocation {
  id?: number;
  name?: string;
  shortCode?: string;
  panNumber?: string;
  gstNumber?: string;
  contact?: IContact;
  company?: IBillingCompany;
}

export class BillingLocation implements IBillingLocation {
  constructor(
    public id?: number,
    public name?: string,
    public shortCode?: string,
    public panNumber?: string,
    public gstNumber?: string,
    public contact?: IContact,
    public company?: IBillingCompany
  ) {}
}
