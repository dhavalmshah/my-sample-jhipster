import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillingLocation, BillingLocation } from 'app/shared/model/billing-location.model';
import { BillingLocationService } from './billing-location.service';
import { BillingLocationComponent } from './billing-location.component';
import { BillingLocationDetailComponent } from './billing-location-detail.component';
import { BillingLocationUpdateComponent } from './billing-location-update.component';

@Injectable({ providedIn: 'root' })
export class BillingLocationResolve implements Resolve<IBillingLocation> {
  constructor(private service: BillingLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillingLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billingLocation: HttpResponse<BillingLocation>) => {
          if (billingLocation.body) {
            return of(billingLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillingLocation());
  }
}

export const billingLocationRoute: Routes = [
  {
    path: '',
    component: BillingLocationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillingLocationDetailComponent,
    resolve: {
      billingLocation: BillingLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillingLocationUpdateComponent,
    resolve: {
      billingLocation: BillingLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillingLocationUpdateComponent,
    resolve: {
      billingLocation: BillingLocationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingLocation.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
