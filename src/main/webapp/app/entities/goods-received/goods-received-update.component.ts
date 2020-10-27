import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IGoodsReceived, GoodsReceived } from 'app/shared/model/goods-received.model';
import { GoodsReceivedService } from './goods-received.service';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';

@Component({
  selector: 'jhi-goods-received-update',
  templateUrl: './goods-received-update.component.html',
})
export class GoodsReceivedUpdateComponent implements OnInit {
  isSaving = false;
  transactions: IStockTransaction[] = [];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    transaction: [],
  });

  constructor(
    protected goodsReceivedService: GoodsReceivedService,
    protected stockTransactionService: StockTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ goodsReceived }) => {
      this.updateForm(goodsReceived);

      this.stockTransactionService
        .query({ filter: 'goodsreceived-is-null' })
        .pipe(
          map((res: HttpResponse<IStockTransaction[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStockTransaction[]) => {
          if (!goodsReceived.transaction || !goodsReceived.transaction.id) {
            this.transactions = resBody;
          } else {
            this.stockTransactionService
              .find(goodsReceived.transaction.id)
              .pipe(
                map((subRes: HttpResponse<IStockTransaction>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStockTransaction[]) => (this.transactions = concatRes));
          }
        });
    });
  }

  updateForm(goodsReceived: IGoodsReceived): void {
    this.editForm.patchValue({
      id: goodsReceived.id,
      status: goodsReceived.status,
      transaction: goodsReceived.transaction,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const goodsReceived = this.createFromForm();
    if (goodsReceived.id !== undefined) {
      this.subscribeToSaveResponse(this.goodsReceivedService.update(goodsReceived));
    } else {
      this.subscribeToSaveResponse(this.goodsReceivedService.create(goodsReceived));
    }
  }

  private createFromForm(): IGoodsReceived {
    return {
      ...new GoodsReceived(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      transaction: this.editForm.get(['transaction'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGoodsReceived>>): void {
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

  trackById(index: number, item: IStockTransaction): any {
    return item.id;
  }
}
