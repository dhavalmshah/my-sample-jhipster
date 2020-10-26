import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';

@Component({
  templateUrl: './phone-number-delete-dialog.component.html',
})
export class PhoneNumberDeleteDialogComponent {
  phoneNumber?: IPhoneNumber;

  constructor(
    protected phoneNumberService: PhoneNumberService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.phoneNumberService.delete(id).subscribe(() => {
      this.eventManager.broadcast('phoneNumberListModification');
      this.activeModal.close();
    });
  }
}
