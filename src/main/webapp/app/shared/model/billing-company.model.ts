import { IBillingLocation } from 'app/shared/model/billing-location.model';

export interface IBillingCompany {
  id?: number;
  name?: string;
  notes?: string;
  locations?: IBillingLocation[];
}

export class BillingCompany implements IBillingCompany {
  constructor(public id?: number, public name?: string, public notes?: string, public locations?: IBillingLocation[]) {}
}
