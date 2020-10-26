import { IProduct } from 'app/shared/model/product.model';
import { ProductCategory } from 'app/shared/model/enumerations/product-category.model';
import { QuantityType } from 'app/shared/model/enumerations/quantity-type.model';

export interface IProductType {
  id?: number;
  name?: string;
  category?: ProductCategory;
  quantityType?: QuantityType;
  hsnNumber?: string;
  description?: string;
  products?: IProduct[];
}

export class ProductType implements IProductType {
  constructor(
    public id?: number,
    public name?: string,
    public category?: ProductCategory,
    public quantityType?: QuantityType,
    public hsnNumber?: string,
    public description?: string,
    public products?: IProduct[]
  ) {}
}
