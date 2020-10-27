import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITransportDetails } from 'app/shared/model/transport-details.model';
import { TransportDetailsService } from './transport-details.service';
import { TransportDetailsDeleteDialogComponent } from './transport-details-delete-dialog.component';

@Component({
  selector: 'jhi-transport-details',
  templateUrl: './transport-details.component.html',
})
export class TransportDetailsComponent implements OnInit, OnDestroy {
  transportDetails?: ITransportDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected transportDetailsService: TransportDetailsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.transportDetailsService.query().subscribe((res: HttpResponse<ITransportDetails[]>) => (this.transportDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTransportDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITransportDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTransportDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('transportDetailsListModification', () => this.loadAll());
  }

  delete(transportDetails: ITransportDetails): void {
    const modalRef = this.modalService.open(TransportDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.transportDetails = transportDetails;
  }
}
