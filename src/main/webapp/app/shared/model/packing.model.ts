import { IUnit } from 'app/shared/model/unit.model';
import { PackingType } from 'app/shared/model/enumerations/packing-type.model';

export interface IPacking {
  id?: number;
  name?: string;
  quantity?: number;
  packingType?: PackingType;
  netWeight?: number;
  grossWeight?: number;
  unit?: IUnit;
}

export class Packing implements IPacking {
  constructor(
    public id?: number,
    public name?: string,
    public quantity?: number,
    public packingType?: PackingType,
    public netWeight?: number,
    public grossWeight?: number,
    public unit?: IUnit
  ) {}
}
