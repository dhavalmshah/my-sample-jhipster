import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDispatch } from 'app/shared/model/dispatch.model';
import { DispatchService } from './dispatch.service';

@Component({
  templateUrl: './dispatch-delete-dialog.component.html',
})
export class DispatchDeleteDialogComponent {
  dispatch?: IDispatch;

  constructor(protected dispatchService: DispatchService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dispatchService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dispatchListModification');
      this.activeModal.close();
    });
  }
}
