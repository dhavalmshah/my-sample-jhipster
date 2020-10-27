import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { ITransportDetails } from 'app/shared/model/transport-details.model';
import { GoodsDispatchStatus } from 'app/shared/model/enumerations/goods-dispatch-status.model';

export interface IDispatch {
  id?: number;
  status?: GoodsDispatchStatus;
  transaction?: IStockTransaction;
  transporter?: ITransportDetails;
  transporter2?: ITransportDetails;
}

export class Dispatch implements IDispatch {
  constructor(
    public id?: number,
    public status?: GoodsDispatchStatus,
    public transaction?: IStockTransaction,
    public transporter?: ITransportDetails,
    public transporter2?: ITransportDetails
  ) {}
}
