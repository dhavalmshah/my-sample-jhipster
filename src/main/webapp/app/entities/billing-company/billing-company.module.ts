import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { BillingCompanyComponent } from './billing-company.component';
import { BillingCompanyDetailComponent } from './billing-company-detail.component';
import { BillingCompanyUpdateComponent } from './billing-company-update.component';
import { BillingCompanyDeleteDialogComponent } from './billing-company-delete-dialog.component';
import { billingCompanyRoute } from './billing-company.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(billingCompanyRoute)],
  declarations: [
    BillingCompanyComponent,
    BillingCompanyDetailComponent,
    BillingCompanyUpdateComponent,
    BillingCompanyDeleteDialogComponent,
  ],
  entryComponents: [BillingCompanyDeleteDialogComponent],
})
export class MySampleBillingCompanyModule {}
