import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockItem } from 'app/shared/model/stock-item.model';
import { StockItemService } from './stock-item.service';
import { StockItemDeleteDialogComponent } from './stock-item-delete-dialog.component';

@Component({
  selector: 'jhi-stock-item',
  templateUrl: './stock-item.component.html',
})
export class StockItemComponent implements OnInit, OnDestroy {
  stockItems?: IStockItem[];
  eventSubscriber?: Subscription;

  constructor(protected stockItemService: StockItemService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.stockItemService.query().subscribe((res: HttpResponse<IStockItem[]>) => (this.stockItems = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStockItems();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStockItem): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStockItems(): void {
    this.eventSubscriber = this.eventManager.subscribe('stockItemListModification', () => this.loadAll());
  }

  delete(stockItem: IStockItem): void {
    const modalRef = this.modalService.open(StockItemDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stockItem = stockItem;
  }
}
