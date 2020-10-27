import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { ProductionStatus } from 'app/shared/model/enumerations/production-status.model';

export interface IProduction {
  id?: number;
  status?: ProductionStatus;
  transaction?: IStockTransaction;
}

export class Production implements IProduction {
  constructor(public id?: number, public status?: ProductionStatus, public transaction?: IStockTransaction) {}
}
