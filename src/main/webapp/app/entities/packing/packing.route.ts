import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPacking, Packing } from 'app/shared/model/packing.model';
import { PackingService } from './packing.service';
import { PackingComponent } from './packing.component';
import { PackingDetailComponent } from './packing-detail.component';
import { PackingUpdateComponent } from './packing-update.component';

@Injectable({ providedIn: 'root' })
export class PackingResolve implements Resolve<IPacking> {
  constructor(private service: PackingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPacking> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((packing: HttpResponse<Packing>) => {
          if (packing.body) {
            return of(packing.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Packing());
  }
}

export const packingRoute: Routes = [
  {
    path: '',
    component: PackingComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.packing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PackingDetailComponent,
    resolve: {
      packing: PackingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.packing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PackingUpdateComponent,
    resolve: {
      packing: PackingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.packing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PackingUpdateComponent,
    resolve: {
      packing: PackingResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.packing.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
