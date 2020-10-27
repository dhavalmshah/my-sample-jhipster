import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProduction, Production } from 'app/shared/model/production.model';
import { ProductionService } from './production.service';
import { ProductionComponent } from './production.component';
import { ProductionDetailComponent } from './production-detail.component';
import { ProductionUpdateComponent } from './production-update.component';

@Injectable({ providedIn: 'root' })
export class ProductionResolve implements Resolve<IProduction> {
  constructor(private service: ProductionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProduction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((production: HttpResponse<Production>) => {
          if (production.body) {
            return of(production.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Production());
  }
}

export const productionRoute: Routes = [
  {
    path: '',
    component: ProductionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.production.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductionDetailComponent,
    resolve: {
      production: ProductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.production.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductionUpdateComponent,
    resolve: {
      production: ProductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.production.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductionUpdateComponent,
    resolve: {
      production: ProductionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.production.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
