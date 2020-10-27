import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPacking } from 'app/shared/model/packing.model';
import { PackingService } from './packing.service';

@Component({
  templateUrl: './packing-delete-dialog.component.html',
})
export class PackingDeleteDialogComponent {
  packing?: IPacking;

  constructor(protected packingService: PackingService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.packingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('packingListModification');
      this.activeModal.close();
    });
  }
}
