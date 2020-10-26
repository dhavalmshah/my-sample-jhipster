import { ICity } from 'app/shared/model/city.model';
import { IRegion } from 'app/shared/model/region.model';

export interface ICountry {
  id?: number;
  countryName?: string;
  cities?: ICity[];
  region?: IRegion;
}

export class Country implements ICountry {
  constructor(public id?: number, public countryName?: string, public cities?: ICity[], public region?: IRegion) {}
}
