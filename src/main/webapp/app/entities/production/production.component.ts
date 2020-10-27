import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProduction } from 'app/shared/model/production.model';
import { ProductionService } from './production.service';
import { ProductionDeleteDialogComponent } from './production-delete-dialog.component';

@Component({
  selector: 'jhi-production',
  templateUrl: './production.component.html',
})
export class ProductionComponent implements OnInit, OnDestroy {
  productions?: IProduction[];
  eventSubscriber?: Subscription;

  constructor(protected productionService: ProductionService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.productionService.query().subscribe((res: HttpResponse<IProduction[]>) => (this.productions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProduction): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductions(): void {
    this.eventSubscriber = this.eventManager.subscribe('productionListModification', () => this.loadAll());
  }

  delete(production: IProduction): void {
    const modalRef = this.modalService.open(ProductionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.production = production;
  }
}
