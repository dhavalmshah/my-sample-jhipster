import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGoodsReceived } from 'app/shared/model/goods-received.model';
import { GoodsReceivedService } from './goods-received.service';

@Component({
  templateUrl: './goods-received-delete-dialog.component.html',
})
export class GoodsReceivedDeleteDialogComponent {
  goodsReceived?: IGoodsReceived;

  constructor(
    protected goodsReceivedService: GoodsReceivedService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.goodsReceivedService.delete(id).subscribe(() => {
      this.eventManager.broadcast('goodsReceivedListModification');
      this.activeModal.close();
    });
  }
}
