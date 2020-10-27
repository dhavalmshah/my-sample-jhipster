import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITransportDetails } from 'app/shared/model/transport-details.model';
import { TransportDetailsService } from './transport-details.service';

@Component({
  templateUrl: './transport-details-delete-dialog.component.html',
})
export class TransportDetailsDeleteDialogComponent {
  transportDetails?: ITransportDetails;

  constructor(
    protected transportDetailsService: TransportDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.transportDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('transportDetailsListModification');
      this.activeModal.close();
    });
  }
}
