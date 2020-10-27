import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDispatch, Dispatch } from 'app/shared/model/dispatch.model';
import { DispatchService } from './dispatch.service';
import { DispatchComponent } from './dispatch.component';
import { DispatchDetailComponent } from './dispatch-detail.component';
import { DispatchUpdateComponent } from './dispatch-update.component';

@Injectable({ providedIn: 'root' })
export class DispatchResolve implements Resolve<IDispatch> {
  constructor(private service: DispatchService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDispatch> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dispatch: HttpResponse<Dispatch>) => {
          if (dispatch.body) {
            return of(dispatch.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dispatch());
  }
}

export const dispatchRoute: Routes = [
  {
    path: '',
    component: DispatchComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.dispatch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DispatchDetailComponent,
    resolve: {
      dispatch: DispatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.dispatch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DispatchUpdateComponent,
    resolve: {
      dispatch: DispatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.dispatch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DispatchUpdateComponent,
    resolve: {
      dispatch: DispatchResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.dispatch.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
