import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { EmailAddressComponent } from './email-address.component';
import { EmailAddressDetailComponent } from './email-address-detail.component';
import { EmailAddressUpdateComponent } from './email-address-update.component';
import { EmailAddressDeleteDialogComponent } from './email-address-delete-dialog.component';
import { emailAddressRoute } from './email-address.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(emailAddressRoute)],
  declarations: [EmailAddressComponent, EmailAddressDetailComponent, EmailAddressUpdateComponent, EmailAddressDeleteDialogComponent],
  entryComponents: [EmailAddressDeleteDialogComponent],
})
export class MySampleEmailAddressModule {}
