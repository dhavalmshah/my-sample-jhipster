import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmailAddress } from 'app/shared/model/email-address.model';
import { EmailAddressService } from './email-address.service';
import { EmailAddressDeleteDialogComponent } from './email-address-delete-dialog.component';

@Component({
  selector: 'jhi-email-address',
  templateUrl: './email-address.component.html',
})
export class EmailAddressComponent implements OnInit, OnDestroy {
  emailAddresses?: IEmailAddress[];
  eventSubscriber?: Subscription;

  constructor(
    protected emailAddressService: EmailAddressService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.emailAddressService.query().subscribe((res: HttpResponse<IEmailAddress[]>) => (this.emailAddresses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEmailAddresses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEmailAddress): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEmailAddresses(): void {
    this.eventSubscriber = this.eventManager.subscribe('emailAddressListModification', () => this.loadAll());
  }

  delete(emailAddress: IEmailAddress): void {
    const modalRef = this.modalService.open(EmailAddressDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.emailAddress = emailAddress;
  }
}
