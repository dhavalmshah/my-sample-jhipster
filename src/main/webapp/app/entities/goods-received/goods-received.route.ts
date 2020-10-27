import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGoodsReceived, GoodsReceived } from 'app/shared/model/goods-received.model';
import { GoodsReceivedService } from './goods-received.service';
import { GoodsReceivedComponent } from './goods-received.component';
import { GoodsReceivedDetailComponent } from './goods-received-detail.component';
import { GoodsReceivedUpdateComponent } from './goods-received-update.component';

@Injectable({ providedIn: 'root' })
export class GoodsReceivedResolve implements Resolve<IGoodsReceived> {
  constructor(private service: GoodsReceivedService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGoodsReceived> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((goodsReceived: HttpResponse<GoodsReceived>) => {
          if (goodsReceived.body) {
            return of(goodsReceived.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GoodsReceived());
  }
}

export const goodsReceivedRoute: Routes = [
  {
    path: '',
    component: GoodsReceivedComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.goodsReceived.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GoodsReceivedDetailComponent,
    resolve: {
      goodsReceived: GoodsReceivedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.goodsReceived.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GoodsReceivedUpdateComponent,
    resolve: {
      goodsReceived: GoodsReceivedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.goodsReceived.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GoodsReceivedUpdateComponent,
    resolve: {
      goodsReceived: GoodsReceivedResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.goodsReceived.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
