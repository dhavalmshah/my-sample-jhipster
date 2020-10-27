import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBillingCompany } from 'app/shared/model/billing-company.model';
import { BillingCompanyService } from './billing-company.service';

@Component({
  templateUrl: './billing-company-delete-dialog.component.html',
})
export class BillingCompanyDeleteDialogComponent {
  billingCompany?: IBillingCompany;

  constructor(
    protected billingCompanyService: BillingCompanyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.billingCompanyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('billingCompanyListModification');
      this.activeModal.close();
    });
  }
}
