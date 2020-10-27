import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionService } from './stock-transaction.service';
import { StockTransactionDeleteDialogComponent } from './stock-transaction-delete-dialog.component';

@Component({
  selector: 'jhi-stock-transaction',
  templateUrl: './stock-transaction.component.html',
})
export class StockTransactionComponent implements OnInit, OnDestroy {
  stockTransactions?: IStockTransaction[];
  eventSubscriber?: Subscription;

  constructor(
    protected stockTransactionService: StockTransactionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.stockTransactionService.query().subscribe((res: HttpResponse<IStockTransaction[]>) => (this.stockTransactions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStockTransactions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStockTransaction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStockTransactions(): void {
    this.eventSubscriber = this.eventManager.subscribe('stockTransactionListModification', () => this.loadAll());
  }

  delete(stockTransaction: IStockTransaction): void {
    const modalRef = this.modalService.open(StockTransactionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.stockTransaction = stockTransaction;
  }
}
