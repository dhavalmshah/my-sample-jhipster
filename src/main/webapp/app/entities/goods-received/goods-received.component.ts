import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGoodsReceived } from 'app/shared/model/goods-received.model';
import { GoodsReceivedService } from './goods-received.service';
import { GoodsReceivedDeleteDialogComponent } from './goods-received-delete-dialog.component';

@Component({
  selector: 'jhi-goods-received',
  templateUrl: './goods-received.component.html',
})
export class GoodsReceivedComponent implements OnInit, OnDestroy {
  goodsReceiveds?: IGoodsReceived[];
  eventSubscriber?: Subscription;

  constructor(
    protected goodsReceivedService: GoodsReceivedService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.goodsReceivedService.query().subscribe((res: HttpResponse<IGoodsReceived[]>) => (this.goodsReceiveds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInGoodsReceiveds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IGoodsReceived): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInGoodsReceiveds(): void {
    this.eventSubscriber = this.eventManager.subscribe('goodsReceivedListModification', () => this.loadAll());
  }

  delete(goodsReceived: IGoodsReceived): void {
    const modalRef = this.modalService.open(GoodsReceivedDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.goodsReceived = goodsReceived;
  }
}
