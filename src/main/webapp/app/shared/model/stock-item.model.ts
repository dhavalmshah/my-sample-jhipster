import { IUnit } from 'app/shared/model/unit.model';
import { IPacking } from 'app/shared/model/packing.model';
import { IProduct } from 'app/shared/model/product.model';
import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockStatus } from 'app/shared/model/enumerations/stock-status.model';

export interface IStockItem {
  id?: number;
  status?: StockStatus;
  quantity?: number;
  batchNumber?: string;
  unit?: IUnit;
  packing?: IPacking;
  product?: IProduct;
  location?: IBillingLocation;
  trans?: IStockTransaction;
  trans?: IStockTransaction;
}

export class StockItem implements IStockItem {
  constructor(
    public id?: number,
    public status?: StockStatus,
    public quantity?: number,
    public batchNumber?: string,
    public unit?: IUnit,
    public packing?: IPacking,
    public product?: IProduct,
    public location?: IBillingLocation,
    public trans?: IStockTransaction,
    public trans?: IStockTransaction
  ) {}
}
