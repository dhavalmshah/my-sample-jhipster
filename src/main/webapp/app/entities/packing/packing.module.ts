import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { PackingComponent } from './packing.component';
import { PackingDetailComponent } from './packing-detail.component';
import { PackingUpdateComponent } from './packing-update.component';
import { PackingDeleteDialogComponent } from './packing-delete-dialog.component';
import { packingRoute } from './packing.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(packingRoute)],
  declarations: [PackingComponent, PackingDetailComponent, PackingUpdateComponent, PackingDeleteDialogComponent],
  entryComponents: [PackingDeleteDialogComponent],
})
export class MySamplePackingModule {}
