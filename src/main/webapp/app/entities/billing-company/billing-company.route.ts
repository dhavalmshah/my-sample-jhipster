import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBillingCompany, BillingCompany } from 'app/shared/model/billing-company.model';
import { BillingCompanyService } from './billing-company.service';
import { BillingCompanyComponent } from './billing-company.component';
import { BillingCompanyDetailComponent } from './billing-company-detail.component';
import { BillingCompanyUpdateComponent } from './billing-company-update.component';

@Injectable({ providedIn: 'root' })
export class BillingCompanyResolve implements Resolve<IBillingCompany> {
  constructor(private service: BillingCompanyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBillingCompany> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((billingCompany: HttpResponse<BillingCompany>) => {
          if (billingCompany.body) {
            return of(billingCompany.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BillingCompany());
  }
}

export const billingCompanyRoute: Routes = [
  {
    path: '',
    component: BillingCompanyComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingCompany.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BillingCompanyDetailComponent,
    resolve: {
      billingCompany: BillingCompanyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingCompany.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BillingCompanyUpdateComponent,
    resolve: {
      billingCompany: BillingCompanyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingCompany.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BillingCompanyUpdateComponent,
    resolve: {
      billingCompany: BillingCompanyResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.billingCompany.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
