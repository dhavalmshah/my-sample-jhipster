import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITransportDetails, TransportDetails } from 'app/shared/model/transport-details.model';
import { TransportDetailsService } from './transport-details.service';
import { ICounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from 'app/entities/counter-party/counter-party.service';

@Component({
  selector: 'jhi-transport-details-update',
  templateUrl: './transport-details-update.component.html',
})
export class TransportDetailsUpdateComponent implements OnInit {
  isSaving = false;
  counterparties: ICounterParty[] = [];

  editForm = this.fb.group({
    id: [],
    transportNumber: [null, [Validators.required, Validators.minLength(2)]],
    transportStartDate: [],
    estimatedEndDate: [],
    actualEndDate: [],
    transporter: [],
  });

  constructor(
    protected transportDetailsService: TransportDetailsService,
    protected counterPartyService: CounterPartyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ transportDetails }) => {
      if (!transportDetails.id) {
        const today = moment().startOf('day');
        transportDetails.transportStartDate = today;
        transportDetails.estimatedEndDate = today;
        transportDetails.actualEndDate = today;
      }

      this.updateForm(transportDetails);

      this.counterPartyService.query().subscribe((res: HttpResponse<ICounterParty[]>) => (this.counterparties = res.body || []));
    });
  }

  updateForm(transportDetails: ITransportDetails): void {
    this.editForm.patchValue({
      id: transportDetails.id,
      transportNumber: transportDetails.transportNumber,
      transportStartDate: transportDetails.transportStartDate ? transportDetails.transportStartDate.format(DATE_TIME_FORMAT) : null,
      estimatedEndDate: transportDetails.estimatedEndDate ? transportDetails.estimatedEndDate.format(DATE_TIME_FORMAT) : null,
      actualEndDate: transportDetails.actualEndDate ? transportDetails.actualEndDate.format(DATE_TIME_FORMAT) : null,
      transporter: transportDetails.transporter,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const transportDetails = this.createFromForm();
    if (transportDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.transportDetailsService.update(transportDetails));
    } else {
      this.subscribeToSaveResponse(this.transportDetailsService.create(transportDetails));
    }
  }

  private createFromForm(): ITransportDetails {
    return {
      ...new TransportDetails(),
      id: this.editForm.get(['id'])!.value,
      transportNumber: this.editForm.get(['transportNumber'])!.value,
      transportStartDate: this.editForm.get(['transportStartDate'])!.value
        ? moment(this.editForm.get(['transportStartDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      estimatedEndDate: this.editForm.get(['estimatedEndDate'])!.value
        ? moment(this.editForm.get(['estimatedEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      actualEndDate: this.editForm.get(['actualEndDate'])!.value
        ? moment(this.editForm.get(['actualEndDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      transporter: this.editForm.get(['transporter'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransportDetails>>): void {
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

  trackById(index: number, item: ICounterParty): any {
    return item.id;
  }
}
