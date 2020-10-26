import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEmailAddress, EmailAddress } from 'app/shared/model/email-address.model';
import { EmailAddressService } from './email-address.service';
import { EmailAddressComponent } from './email-address.component';
import { EmailAddressDetailComponent } from './email-address-detail.component';
import { EmailAddressUpdateComponent } from './email-address-update.component';

@Injectable({ providedIn: 'root' })
export class EmailAddressResolve implements Resolve<IEmailAddress> {
  constructor(private service: EmailAddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmailAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((emailAddress: HttpResponse<EmailAddress>) => {
          if (emailAddress.body) {
            return of(emailAddress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EmailAddress());
  }
}

export const emailAddressRoute: Routes = [
  {
    path: '',
    component: EmailAddressComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.emailAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmailAddressDetailComponent,
    resolve: {
      emailAddress: EmailAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.emailAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmailAddressUpdateComponent,
    resolve: {
      emailAddress: EmailAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.emailAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmailAddressUpdateComponent,
    resolve: {
      emailAddress: EmailAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.emailAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
