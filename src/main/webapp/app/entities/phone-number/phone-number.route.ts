import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPhoneNumber, PhoneNumber } from 'app/shared/model/phone-number.model';
import { PhoneNumberService } from './phone-number.service';
import { PhoneNumberComponent } from './phone-number.component';
import { PhoneNumberDetailComponent } from './phone-number-detail.component';
import { PhoneNumberUpdateComponent } from './phone-number-update.component';

@Injectable({ providedIn: 'root' })
export class PhoneNumberResolve implements Resolve<IPhoneNumber> {
  constructor(private service: PhoneNumberService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhoneNumber> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((phoneNumber: HttpResponse<PhoneNumber>) => {
          if (phoneNumber.body) {
            return of(phoneNumber.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PhoneNumber());
  }
}

export const phoneNumberRoute: Routes = [
  {
    path: '',
    component: PhoneNumberComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.phoneNumber.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PhoneNumberDetailComponent,
    resolve: {
      phoneNumber: PhoneNumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.phoneNumber.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PhoneNumberUpdateComponent,
    resolve: {
      phoneNumber: PhoneNumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.phoneNumber.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PhoneNumberUpdateComponent,
    resolve: {
      phoneNumber: PhoneNumberResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.phoneNumber.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
