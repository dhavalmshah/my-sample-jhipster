import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { BillingLocationComponent } from './billing-location.component';
import { BillingLocationDetailComponent } from './billing-location-detail.component';
import { BillingLocationUpdateComponent } from './billing-location-update.component';
import { BillingLocationDeleteDialogComponent } from './billing-location-delete-dialog.component';
import { billingLocationRoute } from './billing-location.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(billingLocationRoute)],
  declarations: [
    BillingLocationComponent,
    BillingLocationDetailComponent,
    BillingLocationUpdateComponent,
    BillingLocationDeleteDialogComponent,
  ],
  entryComponents: [BillingLocationDeleteDialogComponent],
})
export class MySampleBillingLocationModule {}
