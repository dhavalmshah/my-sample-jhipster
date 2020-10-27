import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPacking } from 'app/shared/model/packing.model';
import { PackingService } from './packing.service';
import { PackingDeleteDialogComponent } from './packing-delete-dialog.component';

@Component({
  selector: 'jhi-packing',
  templateUrl: './packing.component.html',
})
export class PackingComponent implements OnInit, OnDestroy {
  packings?: IPacking[];
  eventSubscriber?: Subscription;

  constructor(protected packingService: PackingService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.packingService.query().subscribe((res: HttpResponse<IPacking[]>) => (this.packings = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPackings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPacking): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPackings(): void {
    this.eventSubscriber = this.eventManager.subscribe('packingListModification', () => this.loadAll());
  }

  delete(packing: IPacking): void {
    const modalRef = this.modalService.open(PackingDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.packing = packing;
  }
}
