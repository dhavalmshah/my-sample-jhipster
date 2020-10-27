import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { PhoneNumberComponent } from './phone-number.component';
import { PhoneNumberDetailComponent } from './phone-number-detail.component';
import { PhoneNumberUpdateComponent } from './phone-number-update.component';
import { PhoneNumberDeleteDialogComponent } from './phone-number-delete-dialog.component';
import { phoneNumberRoute } from './phone-number.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(phoneNumberRoute)],
  declarations: [PhoneNumberComponent, PhoneNumberDetailComponent, PhoneNumberUpdateComponent, PhoneNumberDeleteDialogComponent],
  entryComponents: [PhoneNumberDeleteDialogComponent],
})
export class MySamplePhoneNumberModule {}
