import { ICountry } from 'app/shared/model/country.model';

export interface IRegion {
  id?: number;
  regionName?: string;
  countries?: ICountry[];
}

export class Region implements IRegion {
  constructor(public id?: number, public regionName?: string, public countries?: ICountry[]) {}
}
