import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { ProductAliasComponent } from './product-alias.component';
import { ProductAliasDetailComponent } from './product-alias-detail.component';
import { ProductAliasUpdateComponent } from './product-alias-update.component';
import { ProductAliasDeleteDialogComponent } from './product-alias-delete-dialog.component';
import { productAliasRoute } from './product-alias.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(productAliasRoute)],
  declarations: [ProductAliasComponent, ProductAliasDetailComponent, ProductAliasUpdateComponent, ProductAliasDeleteDialogComponent],
  entryComponents: [ProductAliasDeleteDialogComponent],
})
export class MySampleProductAliasModule {}
