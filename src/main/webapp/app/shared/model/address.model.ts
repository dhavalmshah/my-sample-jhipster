import { ICity } from 'app/shared/model/city.model';

export interface IAddress {
  id?: number;
  buildingAddress?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: ICity;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public buildingAddress?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: ICity
  ) {}
}
