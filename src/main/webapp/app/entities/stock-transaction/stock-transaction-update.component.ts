import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IStockTransaction, StockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from './stock-transaction.service';

@Component({
  selector: 'jhi-stock-transaction-update',
  templateUrl: './stock-transaction-update.component.html',
})
export class StockTransactionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required, Validators.maxLength(65335)]],
    transactionDate: [null, [Validators.required]],
    transactionType: [null, [Validators.required]],
  });

  constructor(
    protected stockTransactionService: StockTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockTransaction }) => {
      if (!stockTransaction.id) {
        const today = moment().startOf('day');
        stockTransaction.transactionDate = today;
      }

      this.updateForm(stockTransaction);
    });
  }

  updateForm(stockTransaction: IStockTransaction): void {
    this.editForm.patchValue({
      id: stockTransaction.id,
      description: stockTransaction.description,
      transactionDate: stockTransaction.transactionDate ? stockTransaction.transactionDate.format(DATE_TIME_FORMAT) : null,
      transactionType: stockTransaction.transactionType,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockTransaction = this.createFromForm();
    if (stockTransaction.id !== undefined) {
      this.subscribeToSaveResponse(this.stockTransactionService.update(stockTransaction));
    } else {
      this.subscribeToSaveResponse(this.stockTransactionService.create(stockTransaction));
    }
  }

  private createFromForm(): IStockTransaction {
    return {
      ...new StockTransaction(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      transactionDate: this.editForm.get(['transactionDate'])!.value
        ? moment(this.editForm.get(['transactionDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      transactionType: this.editForm.get(['transactionType'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockTransaction>>): void {
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
}
