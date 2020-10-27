import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBillingCompany, BillingCompany } from 'app/shared/model/billing-company.model';
import { BillingCompanyService } from './billing-company.service';

@Component({
  selector: 'jhi-billing-company-update',
  templateUrl: './billing-company-update.component.html',
})
export class BillingCompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    notes: [null, [Validators.maxLength(65535)]],
  });

  constructor(protected billingCompanyService: BillingCompanyService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingCompany }) => {
      this.updateForm(billingCompany);
    });
  }

  updateForm(billingCompany: IBillingCompany): void {
    this.editForm.patchValue({
      id: billingCompany.id,
      name: billingCompany.name,
      notes: billingCompany.notes,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billingCompany = this.createFromForm();
    if (billingCompany.id !== undefined) {
      this.subscribeToSaveResponse(this.billingCompanyService.update(billingCompany));
    } else {
      this.subscribeToSaveResponse(this.billingCompanyService.create(billingCompany));
    }
  }

  private createFromForm(): IBillingCompany {
    return {
      ...new BillingCompany(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      notes: this.editForm.get(['notes'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingCompany>>): void {
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
