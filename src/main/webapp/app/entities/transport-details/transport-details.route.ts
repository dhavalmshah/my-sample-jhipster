import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITransportDetails, TransportDetails } from 'app/shared/model/transport-details.model';
import { TransportDetailsService } from './transport-details.service';
import { TransportDetailsComponent } from './transport-details.component';
import { TransportDetailsDetailComponent } from './transport-details-detail.component';
import { TransportDetailsUpdateComponent } from './transport-details-update.component';

@Injectable({ providedIn: 'root' })
export class TransportDetailsResolve implements Resolve<ITransportDetails> {
  constructor(private service: TransportDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITransportDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((transportDetails: HttpResponse<TransportDetails>) => {
          if (transportDetails.body) {
            return of(transportDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TransportDetails());
  }
}

export const transportDetailsRoute: Routes = [
  {
    path: '',
    component: TransportDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.transportDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TransportDetailsDetailComponent,
    resolve: {
      transportDetails: TransportDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.transportDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TransportDetailsUpdateComponent,
    resolve: {
      transportDetails: TransportDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.transportDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TransportDetailsUpdateComponent,
    resolve: {
      transportDetails: TransportDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.transportDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
