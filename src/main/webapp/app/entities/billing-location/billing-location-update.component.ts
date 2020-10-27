import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBillingLocation, BillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';
import { IBillingCompany } from 'app/shared/model/billing-company.model';
import { BillingCompanyService } from 'app/entities/billing-company/billing-company.service';

type SelectableEntity = IContact | IBillingCompany;

@Component({
  selector: 'jhi-billing-location-update',
  templateUrl: './billing-location-update.component.html',
})
export class BillingLocationUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];
  billingcompanies: IBillingCompany[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255), Validators.pattern('^[A-Z][a-z]+\\d*$')]],
    shortCode: [null, [Validators.required, Validators.maxLength(10), Validators.pattern('^[A-Z][a-z]+\\d*$')]],
    panNumber: [null, [Validators.required, Validators.maxLength(255)]],
    gstNumber: [null, [Validators.required, Validators.maxLength(255)]],
    contact: [null, Validators.required],
    company: [],
  });

  constructor(
    protected billingLocationService: BillingLocationService,
    protected contactService: ContactService,
    protected billingCompanyService: BillingCompanyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingLocation }) => {
      this.updateForm(billingLocation);

      this.contactService
        .query({ filter: 'billinglocation-is-null' })
        .pipe(
          map((res: HttpResponse<IContact[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContact[]) => {
          if (!billingLocation.contact || !billingLocation.contact.id) {
            this.contacts = resBody;
          } else {
            this.contactService
              .find(billingLocation.contact.id)
              .pipe(
                map((subRes: HttpResponse<IContact>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContact[]) => (this.contacts = concatRes));
          }
        });

      this.billingCompanyService.query().subscribe((res: HttpResponse<IBillingCompany[]>) => (this.billingcompanies = res.body || []));
    });
  }

  updateForm(billingLocation: IBillingLocation): void {
    this.editForm.patchValue({
      id: billingLocation.id,
      name: billingLocation.name,
      shortCode: billingLocation.shortCode,
      panNumber: billingLocation.panNumber,
      gstNumber: billingLocation.gstNumber,
      contact: billingLocation.contact,
      company: billingLocation.company,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const billingLocation = this.createFromForm();
    if (billingLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.billingLocationService.update(billingLocation));
    } else {
      this.subscribeToSaveResponse(this.billingLocationService.create(billingLocation));
    }
  }

  private createFromForm(): IBillingLocation {
    return {
      ...new BillingLocation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      shortCode: this.editForm.get(['shortCode'])!.value,
      panNumber: this.editForm.get(['panNumber'])!.value,
      gstNumber: this.editForm.get(['gstNumber'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      company: this.editForm.get(['company'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBillingLocation>>): void {
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
