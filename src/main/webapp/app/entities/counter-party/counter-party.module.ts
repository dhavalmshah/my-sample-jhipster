import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MySampleSharedModule } from 'app/shared/shared.module';
import { CounterPartyComponent } from './counter-party.component';
import { CounterPartyDetailComponent } from './counter-party-detail.component';
import { CounterPartyUpdateComponent } from './counter-party-update.component';
import { CounterPartyDeleteDialogComponent } from './counter-party-delete-dialog.component';
import { counterPartyRoute } from './counter-party.route';

@NgModule({
  imports: [MySampleSharedModule, RouterModule.forChild(counterPartyRoute)],
  declarations: [CounterPartyComponent, CounterPartyDetailComponent, CounterPartyUpdateComponent, CounterPartyDeleteDialogComponent],
  entryComponents: [CounterPartyDeleteDialogComponent],
})
export class MySampleCounterPartyModule {}
