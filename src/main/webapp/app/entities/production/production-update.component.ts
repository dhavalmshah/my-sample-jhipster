import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProduction, Production } from 'app/shared/model/production.model';
import { ProductionService } from './production.service';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';

@Component({
  selector: 'jhi-production-update',
  templateUrl: './production-update.component.html',
})
export class ProductionUpdateComponent implements OnInit {
  isSaving = false;
  transactions: IStockTransaction[] = [];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    transaction: [],
  });

  constructor(
    protected productionService: ProductionService,
    protected stockTransactionService: StockTransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ production }) => {
      this.updateForm(production);

      this.stockTransactionService
        .query({ filter: 'production-is-null' })
        .pipe(
          map((res: HttpResponse<IStockTransaction[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStockTransaction[]) => {
          if (!production.transaction || !production.transaction.id) {
            this.transactions = resBody;
          } else {
            this.stockTransactionService
              .find(production.transaction.id)
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

  updateForm(production: IProduction): void {
    this.editForm.patchValue({
      id: production.id,
      status: production.status,
      transaction: production.transaction,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const production = this.createFromForm();
    if (production.id !== undefined) {
      this.subscribeToSaveResponse(this.productionService.update(production));
    } else {
      this.subscribeToSaveResponse(this.productionService.create(production));
    }
  }

  private createFromForm(): IProduction {
    return {
      ...new Production(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      transaction: this.editForm.get(['transaction'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProduction>>): void {
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
