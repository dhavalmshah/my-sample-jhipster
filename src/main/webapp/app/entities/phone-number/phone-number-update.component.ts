import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPhoneNumber, PhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';

@Component({
  selector: 'jhi-phone-number-update',
  templateUrl: './phone-number-update.component.html',
})
export class PhoneNumberUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    countryCode: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(255), Validators.pattern('^[-\\s\\./0-9]*$')]],
    contact: [],
  });

  constructor(
    protected phoneNumberService: PhoneNumberService,
    protected contactService: ContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phoneNumber }) => {
      this.updateForm(phoneNumber);

      this.contactService.query().subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body || []));
    });
  }

  updateForm(phoneNumber: IPhoneNumber): void {
    this.editForm.patchValue({
      id: phoneNumber.id,
      type: phoneNumber.type,
      countryCode: phoneNumber.countryCode,
      phoneNumber: phoneNumber.phoneNumber,
      contact: phoneNumber.contact,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phoneNumber = this.createFromForm();
    if (phoneNumber.id !== undefined) {
      this.subscribeToSaveResponse(this.phoneNumberService.update(phoneNumber));
    } else {
      this.subscribeToSaveResponse(this.phoneNumberService.create(phoneNumber));
    }
  }

  private createFromForm(): IPhoneNumber {
    return {
      ...new PhoneNumber(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      countryCode: this.editForm.get(['countryCode'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      contact: this.editForm.get(['contact'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhoneNumber>>): void {
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

  trackById(index: number, item: IContact): any {
    return item.id;
  }
}
