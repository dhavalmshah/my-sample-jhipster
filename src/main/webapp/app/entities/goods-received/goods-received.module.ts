import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { GoodsReceivedComponent } from './goods-received.component';
import { GoodsReceivedDetailComponent } from './goods-received-detail.component';
import { GoodsReceivedUpdateComponent } from './goods-received-update.component';
import { GoodsReceivedDeleteDialogComponent } from './goods-received-delete-dialog.component';
import { goodsReceivedRoute } from './goods-received.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(goodsReceivedRoute)],
  declarations: [GoodsReceivedComponent, GoodsReceivedDetailComponent, GoodsReceivedUpdateComponent, GoodsReceivedDeleteDialogComponent],
  entryComponents: [GoodsReceivedDeleteDialogComponent],
})
export class MySampleGoodsReceivedModule {}
