import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICounterParty, CounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from './counter-party.service';

@Component({
  selector: 'jhi-counter-party-update',
  templateUrl: './counter-party-update.component.html',
})
export class CounterPartyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    type: [],
    notes: [null, [Validators.maxLength(65535)]],
  });

  constructor(protected counterPartyService: CounterPartyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ counterParty }) => {
      this.updateForm(counterParty);
    });
  }

  updateForm(counterParty: ICounterParty): void {
    this.editForm.patchValue({
      id: counterParty.id,
      name: counterParty.name,
      type: counterParty.type,
      notes: counterParty.notes,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const counterParty = this.createFromForm();
    if (counterParty.id !== undefined) {
      this.subscribeToSaveResponse(this.counterPartyService.update(counterParty));
    } else {
      this.subscribeToSaveResponse(this.counterPartyService.create(counterParty));
    }
  }

  private createFromForm(): ICounterParty {
    return {
      ...new CounterParty(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      type: this.editForm.get(['type'])!.value,
      notes: this.editForm.get(['notes'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICounterParty>>): void {
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
