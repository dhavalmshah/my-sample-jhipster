import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICounterParty, CounterParty } from 'app/shared/model/counter-party.model';
import { CounterPartyService } from './counter-party.service';
import { CounterPartyComponent } from './counter-party.component';
import { CounterPartyDetailComponent } from './counter-party-detail.component';
import { CounterPartyUpdateComponent } from './counter-party-update.component';

@Injectable({ providedIn: 'root' })
export class CounterPartyResolve implements Resolve<ICounterParty> {
  constructor(private service: CounterPartyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICounterParty> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((counterParty: HttpResponse<CounterParty>) => {
          if (counterParty.body) {
            return of(counterParty.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CounterParty());
  }
}

export const counterPartyRoute: Routes = [
  {
    path: '',
    component: CounterPartyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.counterParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CounterPartyDetailComponent,
    resolve: {
      counterParty: CounterPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.counterParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CounterPartyUpdateComponent,
    resolve: {
      counterParty: CounterPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.counterParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CounterPartyUpdateComponent,
    resolve: {
      counterParty: CounterPartyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.counterParty.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
