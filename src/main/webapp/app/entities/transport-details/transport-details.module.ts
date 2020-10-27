import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { TransportDetailsComponent } from './transport-details.component';
import { TransportDetailsDetailComponent } from './transport-details-detail.component';
import { TransportDetailsUpdateComponent } from './transport-details-update.component';
import { TransportDetailsDeleteDialogComponent } from './transport-details-delete-dialog.component';
import { transportDetailsRoute } from './transport-details.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(transportDetailsRoute)],
  declarations: [
    TransportDetailsComponent,
    TransportDetailsDetailComponent,
    TransportDetailsUpdateComponent,
    TransportDetailsDeleteDialogComponent,
  ],
  entryComponents: [TransportDetailsDeleteDialogComponent],
})
export class MySampleTransportDetailsModule {}
