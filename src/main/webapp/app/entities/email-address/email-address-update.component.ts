import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEmailAddress, EmailAddress } from 'app/shared/model/email-address.model';
import { EmailAddressService } from './email-address.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact/contact.service';

@Component({
  selector: 'jhi-email-address-update',
  templateUrl: './email-address-update.component.html',
})
export class EmailAddressUpdateComponent implements OnInit {
  isSaving = false;
  contacts: IContact[] = [];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    emailAddress: [
      null,
      [
        Validators.required,
        Validators.maxLength(255),
        Validators.pattern(
          '^[a-zA-Z0-9\\.\\!\\#\\$\\%\\&amp;\\*\\+\\/\\=\\?\\^\\_\\`\\{\\|\\}\\~\\-]+@[a-zA-Z0-9-]+\\.(?:[a-zA-Z0-9-]+)*$'
        ),
      ],
    ],
    contact: [],
  });

  constructor(
    protected emailAddressService: EmailAddressService,
    protected contactService: ContactService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailAddress }) => {
      this.updateForm(emailAddress);

      this.contactService.query().subscribe((res: HttpResponse<IContact[]>) => (this.contacts = res.body || []));
    });
  }

  updateForm(emailAddress: IEmailAddress): void {
    this.editForm.patchValue({
      id: emailAddress.id,
      type: emailAddress.type,
      emailAddress: emailAddress.emailAddress,
      contact: emailAddress.contact,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailAddress = this.createFromForm();
    if (emailAddress.id !== undefined) {
      this.subscribeToSaveResponse(this.emailAddressService.update(emailAddress));
    } else {
      this.subscribeToSaveResponse(this.emailAddressService.create(emailAddress));
    }
  }

  private createFromForm(): IEmailAddress {
    return {
      ...new EmailAddress(),
      id: this.editForm.get(['id'])!.value,
      type: this.editForm.get(['type'])!.value,
      emailAddress: this.editForm.get(['emailAddress'])!.value,
      contact: this.editForm.get(['contact'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailAddress>>): void {
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
