import { ICountry } from 'app/shared/model/country.model';

export interface ICity {
  id?: number;
  cityName?: string;
  stateName?: string;
  country?: ICountry;
}

export class City implements ICity {
  constructor(public id?: number, public cityName?: string, public stateName?: string, public country?: ICountry) {}
}
