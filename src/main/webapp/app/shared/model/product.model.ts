import { IProductAlias } from 'app/shared/model/product-alias.model';
import { IProductType } from 'app/shared/model/product-type.model';

export interface IProduct {
  id?: number;
  name?: string;
  productCode?: string;
  aliases?: IProductAlias[];
  productType?: IProductType;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public productCode?: string,
    public aliases?: IProductAlias[],
    public productType?: IProductType
  ) {}
}
