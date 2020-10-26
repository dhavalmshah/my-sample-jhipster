import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEmailAddress } from 'app/shared/model/email-address.model';
import { EmailAddressService } from './email-address.service';

@Component({
  templateUrl: './email-address-delete-dialog.component.html',
})
export class EmailAddressDeleteDialogComponent {
  emailAddress?: IEmailAddress;

  constructor(
    protected emailAddressService: EmailAddressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.emailAddressService.delete(id).subscribe(() => {
      this.eventManager.broadcast('emailAddressListModification');
      this.activeModal.close();
    });
  }
}
