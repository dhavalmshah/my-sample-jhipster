import { IProduct } from 'app/shared/model/product.model';

export interface IProductAlias {
  id?: number;
  name?: string;
  product?: IProduct;
}

export class ProductAlias implements IProductAlias {
  constructor(public id?: number, public name?: string, public product?: IProduct) {}
}
