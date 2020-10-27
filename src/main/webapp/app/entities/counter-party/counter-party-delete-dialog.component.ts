import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from './counter-party.service';

@Component({
  templateUrl: './counter-party-delete-dialog.component.html',
})
export class CounterPartyDeleteDialogComponent {
  counterParty?: ICounterParty;

  constructor(
    protected counterPartyService: CounterPartyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.counterPartyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('counterPartyListModification');
      this.activeModal.close();
    });
  }
}
