import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmailAddress } from 'app/shared/model/email-address.model';

@Component({
  selector: 'jhi-email-address-detail',
  templateUrl: './email-address-detail.component.html',
})
export class EmailAddressDetailComponent implements OnInit {
  emailAddress: IEmailAddress | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailAddress }) => (this.emailAddress = emailAddress));
  }

  previousState(): void {
    window.history.back();
  }
}
