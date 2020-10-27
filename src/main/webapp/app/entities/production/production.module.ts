import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { ProductionComponent } from './production.component';
import { ProductionDetailComponent } from './production-detail.component';
import { ProductionUpdateComponent } from './production-update.component';
import { ProductionDeleteDialogComponent } from './production-delete-dialog.component';
import { productionRoute } from './production.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(productionRoute)],
  declarations: [ProductionComponent, ProductionDetailComponent, ProductionUpdateComponent, ProductionDeleteDialogComponent],
  entryComponents: [ProductionDeleteDialogComponent],
})
export class MySampleProductionModule {}
