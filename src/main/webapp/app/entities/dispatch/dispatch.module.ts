import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { DispatchComponent } from './dispatch.component';
import { DispatchDetailComponent } from './dispatch-detail.component';
import { DispatchUpdateComponent } from './dispatch-update.component';
import { DispatchDeleteDialogComponent } from './dispatch-delete-dialog.component';
import { dispatchRoute } from './dispatch.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(dispatchRoute)],
  declarations: [DispatchComponent, DispatchDetailComponent, DispatchUpdateComponent, DispatchDeleteDialogComponent],
  entryComponents: [DispatchDeleteDialogComponent],
})
export class MySampleDispatchModule {}
