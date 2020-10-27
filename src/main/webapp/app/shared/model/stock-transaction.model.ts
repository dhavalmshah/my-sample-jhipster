import { Moment } from 'moment';
import { IStockItem } from 'app/shared/model/stock-item.model';
import { StockTransactionType } from 'app/shared/model/enumerations/stock-transaction-type.model';

export interface IStockTransaction {
  id?: number;
  description?: string;
  transactionDate?: Moment;
  transactionType?: StockTransactionType;
  credits?: IStockItem[];
  debits?: IStockItem[];
}

export class StockTransaction implements IStockTransaction {
  constructor(
    public id?: number,
    public description?: string,
    public transactionDate?: Moment,
    public transactionType?: StockTransactionType,
    public credits?: IStockItem[],
    public debits?: IStockItem[]
  ) {}
}
