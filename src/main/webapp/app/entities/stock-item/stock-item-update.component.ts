import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IStockItem, StockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from './stock-item.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { IPacking } from 'app/shared/model/packing.model';
import { PackingService } from 'app/entities/packing/packing.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';
import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from 'app/entities/billing-location/billing-location.service';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';

type SelectableEntity = IUnit | IPacking | IProduct | IBillingLocation | IStockTransaction;

@Component({
  selector: 'jhi-stock-item-update',
  templateUrl: './stock-item-update.component.html',
})
export class StockItemUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  packings: IPacking[] = [];
  products: IProduct[] = [];
  billinglocations: IBillingLocation[] = [];
  stocktransactions: IStockTransaction[] = [];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    quantity: [null, [Validators.required]],
    batchNumber: [null, [Validators.required, Validators.maxLength(50)]],
    unit: [null, Validators.required],
    packing: [null, Validators.required],
    product: [null, Validators.required],
    location: [null, Validators.required],
    creditTrans: [],
    debitTrans: [],
  });

  constructor(
    protected stockItemService: StockItemService,
    protected unitService: UnitService,
    protected packingService: PackingService,
    protected productService: ProductService,
    protected billingLocationService: BillingLocationService,
    protected stockTransactionService: StockTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockItem }) => {
      this.updateForm(stockItem);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.packingService.query().subscribe((res: HttpResponse<IPacking[]>) => (this.packings = res.body || []));

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));

      this.billingLocationService.query().subscribe((res: HttpResponse<IBillingLocation[]>) => (this.billinglocations = res.body || []));

      this.stockTransactionService.query().subscribe((res: HttpResponse<IStockTransaction[]>) => (this.stocktransactions = res.body || []));
    });
  }

  updateForm(stockItem: IStockItem): void {
    this.editForm.patchValue({
      id: stockItem.id,
      status: stockItem.status,
      quantity: stockItem.quantity,
      batchNumber: stockItem.batchNumber,
      unit: stockItem.unit,
      packing: stockItem.packing,
      product: stockItem.product,
      location: stockItem.location,
      creditTrans: stockItem.creditTrans,
      debitTrans: stockItem.debitTrans,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockItem = this.createFromForm();
    if (stockItem.id !== undefined) {
      this.subscribeToSaveResponse(this.stockItemService.update(stockItem));
    } else {
      this.subscribeToSaveResponse(this.stockItemService.create(stockItem));
    }
  }

  private createFromForm(): IStockItem {
    return {
      ...new StockItem(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      batchNumber: this.editForm.get(['batchNumber'])!.value,
      unit: this.editForm.get(['unit'])!.value,
      packing: this.editForm.get(['packing'])!.value,
      product: this.editForm.get(['product'])!.value,
      location: this.editForm.get(['location'])!.value,
      creditTrans: this.editForm.get(['creditTrans'])!.value,
      debitTrans: this.editForm.get(['debitTrans'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockItem>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
