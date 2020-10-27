import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBillingCompany } from 'app/shared/model/billing-company.model';

@Component({
  selector: 'jhi-billing-company-detail',
  templateUrl: './billing-company-detail.component.html',
})
export class BillingCompanyDetailComponent implements OnInit {
  billingCompany: IBillingCompany | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ billingCompany }) => (this.billingCompany = billingCompany));
  }

  previousState(): void {
    window.history.back();
  }
}
