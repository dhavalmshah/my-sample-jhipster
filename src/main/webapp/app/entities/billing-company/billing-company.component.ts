import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBillingCompany } from 'app/shared/model/billing-company.model';
import { BillingCompanyService } from './billing-company.service';
import { BillingCompanyDeleteDialogComponent } from './billing-company-delete-dialog.component';

@Component({
  selector: 'jhi-billing-company',
  templateUrl: './billing-company.component.html',
})
export class BillingCompanyComponent implements OnInit, OnDestroy {
  billingCompanies?: IBillingCompany[];
  eventSubscriber?: Subscription;

  constructor(
    protected billingCompanyService: BillingCompanyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.billingCompanyService.query().subscribe((res: HttpResponse<IBillingCompany[]>) => (this.billingCompanies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBillingCompanies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBillingCompany): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBillingCompanies(): void {
    this.eventSubscriber = this.eventManager.subscribe('billingCompanyListModification', () => this.loadAll());
  }

  delete(billingCompany: IBillingCompany): void {
    const modalRef = this.modalService.open(BillingCompanyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.billingCompany = billingCompany;
  }
}
