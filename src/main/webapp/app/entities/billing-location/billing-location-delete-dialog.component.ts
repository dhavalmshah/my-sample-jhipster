import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';

@Component({
  templateUrl: './billing-location-delete-dialog.component.html',
})
export class BillingLocationDeleteDialogComponent {
  billingLocation?: IBillingLocation;

  constructor(
    protected billingLocationService: BillingLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billingLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billingLocationListModification');
      this.activeModal.close();
    });
  }
}
