import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDispatch } from 'app/shared/model/dispatch.model';
import { DispatchService } from './dispatch.service';
import { DispatchDeleteDialogComponent } from './dispatch-delete-dialog.component';

@Component({
  selector: 'jhi-dispatch',
  templateUrl: './dispatch.component.html',
})
export class DispatchComponent implements OnInit, OnDestroy {
  dispatches?: IDispatch[];
  eventSubscriber?: Subscription;

  constructor(protected dispatchService: DispatchService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.dispatchService.query().subscribe((res: HttpResponse<IDispatch[]>) => (this.dispatches = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDispatches();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDispatch): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDispatches(): void {
    this.eventSubscriber = this.eventManager.subscribe('dispatchListModification', () => this.loadAll());
  }

  delete(dispatch: IDispatch): void {
    const modalRef = this.modalService.open(DispatchDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dispatch = dispatch;
  }
}
