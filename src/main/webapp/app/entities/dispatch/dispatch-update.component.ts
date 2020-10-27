import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDispatch, Dispatch } from 'app/shared/model/dispatch.model';
import { DispatchService } from './dispatch.service';
import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';
import { ITransportDetails } from 'app/shared/model/transport-details.model';
import { TransportDetailsService } from 'app/entities/transport-details/transport-details.service';

type SelectableEntity = IStockTransaction | ITransportDetails;

@Component({
  selector: 'jhi-dispatch-update',
  templateUrl: './dispatch-update.component.html',
})
export class DispatchUpdateComponent implements OnInit {
  isSaving = false;
  transactions: IStockTransaction[] = [];
  transportdetails: ITransportDetails[] = [];

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required]],
    transaction: [],
    transporter: [],
    transporter2: [],
  });

  constructor(
    protected dispatchService: DispatchService,
    protected stockTransactionService: StockTransactionService,
    protected transportDetailsService: TransportDetailsService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dispatch }) => {
      this.updateForm(dispatch);

      this.stockTransactionService
        .query({ filter: 'dispatch-is-null' })
        .pipe(
          map((res: HttpResponse<IStockTransaction[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IStockTransaction[]) => {
          if (!dispatch.transaction || !dispatch.transaction.id) {
            this.transactions = resBody;
          } else {
            this.stockTransactionService
              .find(dispatch.transaction.id)
              .pipe(
                map((subRes: HttpResponse<IStockTransaction>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStockTransaction[]) => (this.transactions = concatRes));
          }
        });

      this.transportDetailsService.query().subscribe((res: HttpResponse<ITransportDetails[]>) => (this.transportdetails = res.body || []));
    });
  }

  updateForm(dispatch: IDispatch): void {
    this.editForm.patchValue({
      id: dispatch.id,
      status: dispatch.status,
      transaction: dispatch.transaction,
      transporter: dispatch.transporter,
      transporter2: dispatch.transporter2,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dispatch = this.createFromForm();
    if (dispatch.id !== undefined) {
      this.subscribeToSaveResponse(this.dispatchService.update(dispatch));
    } else {
      this.subscribeToSaveResponse(this.dispatchService.create(dispatch));
    }
  }

  private createFromForm(): IDispatch {
    return {
      ...new Dispatch(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      transaction: this.editForm.get(['transaction'])!.value,
      transporter: this.editForm.get(['transporter'])!.value,
      transporter2: this.editForm.get(['transporter2'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDispatch>>): void {
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
