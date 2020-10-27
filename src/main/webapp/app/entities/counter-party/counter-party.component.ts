import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from './counter-party.service';
import { CounterPartyDeleteDialogComponent } from './counter-party-delete-dialog.component';

@Component({
  selector: 'jhi-counter-party',
  templateUrl: './counter-party.component.html',
})
export class CounterPartyComponent implements OnInit, OnDestroy {
  counterParties?: ICounterParty[];
  eventSubscriber?: Subscription;

  constructor(
    protected counterPartyService: CounterPartyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.counterPartyService.query().subscribe((res: HttpResponse<ICounterParty[]>) => (this.counterParties = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCounterParties();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICounterParty): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCounterParties(): void {
    this.eventSubscriber = this.eventManager.subscribe('counterPartyListModification', () => this.loadAll());
  }

  delete(counterParty: ICounterParty): void {
    const modalRef = this.modalService.open(CounterPartyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.counterParty = counterParty;
  }
}
