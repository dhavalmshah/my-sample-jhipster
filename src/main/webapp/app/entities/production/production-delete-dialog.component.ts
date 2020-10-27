import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProduction } from 'app/shared/model/production.model';
import { ProductionService } from './production.service';

@Component({
  templateUrl: './production-delete-dialog.component.html',
})
export class ProductionDeleteDialogComponent {
  production?: IProduction;

  constructor(
    protected productionService: ProductionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productionListModification');
      this.activeModal.close();
    });
  }
}
