import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { PhoneNumberDeleteDialogComponent } from './phone-number-delete-dialog.component';

@Component({
  selector: 'jhi-phone-number',
  templateUrl: './phone-number.component.html',
})
export class PhoneNumberComponent implements OnInit, OnDestroy {
  phoneNumbers?: IPhoneNumber[];
  eventSubscriber?: Subscription;

  constructor(
    protected phoneNumberService: PhoneNumberService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.phoneNumberService.query().subscribe((res: HttpResponse<IPhoneNumber[]>) => (this.phoneNumbers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPhoneNumbers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPhoneNumber): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPhoneNumbers(): void {
    this.eventSubscriber = this.eventManager.subscribe('phoneNumberListModification', () => this.loadAll());
  }

  delete(phoneNumber: IPhoneNumber): void {
    const modalRef = this.modalService.open(PhoneNumberDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.phoneNumber = phoneNumber;
  }
}
