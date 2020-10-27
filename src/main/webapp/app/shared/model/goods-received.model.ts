import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { GoodsReceivedStatus } from 'app/shared/model/enumerations/goods-received-status.model';

export interface IGoodsReceived {
  id?: number;
  status?: GoodsReceivedStatus;
  transaction?: IStockTransaction;
}

export class GoodsReceived implements IGoodsReceived {
  constructor(public id?: number, public status?: GoodsReceivedStatus, public transaction?: IStockTransaction) {}
}
