import { QuantityType } from 'app/shared/model/enumerations/quantity-type.model';

export interface IUnit {
  id?: number;
  name?: string;
  quantityType?: QuantityType;
  fullName?: string;
  baseUnitMultiplier?: number;
  isSmallerThanBase?: boolean;
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public name?: string,
    public quantityType?: QuantityType,
    public fullName?: string,
    public baseUnitMultiplier?: number,
    public isSmallerThanBase?: boolean
  ) {
    this.isSmallerThanBase = this.isSmallerThanBase || false;
  }
}
