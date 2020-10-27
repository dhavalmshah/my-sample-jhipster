import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { BillingLocationDeleteDialogComponent } from './billing-location-delete-dialog.component';

@Component({
  selector: 'jhi-billing-location',
  templateUrl: './billing-location.component.html',
})
export class BillingLocationComponent implements OnInit, OnDestroy {
  billingLocations?: IBillingLocation[];
  eventSubscriber?: Subscription;

  constructor(
    protected billingLocationService: BillingLocationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.billingLocationService.query().subscribe((res: HttpResponse<IBillingLocation[]>) => (this.billingLocations = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBillingLocations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBillingLocation): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBillingLocations(): void {
    this.eventSubscriber = this.eventManager.subscribe('billingLocationListModification', () => this.loadAll());
  }

  delete(billingLocation: IBillingLocation): void {
    const modalRef = this.modalService.open(BillingLocationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billingLocation = billingLocation;
  }
}
