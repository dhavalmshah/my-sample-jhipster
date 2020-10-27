import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductAlias, ProductAlias } from 'app/shared/model/product-alias.model';
import { ProductAliasService } from './product-alias.service';
import { ProductAliasComponent } from './product-alias.component';
import { ProductAliasDetailComponent } from './product-alias-detail.component';
import { ProductAliasUpdateComponent } from './product-alias-update.component';

@Injectable({ providedIn: 'root' })
export class ProductAliasResolve implements Resolve<IProductAlias> {
  constructor(private service: ProductAliasService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductAlias> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productAlias: HttpResponse<ProductAlias>) => {
          if (productAlias.body) {
            return of(productAlias.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductAlias());
  }
}

export const productAliasRoute: Routes = [
  {
    path: '',
    component: ProductAliasComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.productAlias.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductAliasDetailComponent,
    resolve: {
      productAlias: ProductAliasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.productAlias.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductAliasUpdateComponent,
    resolve: {
      productAlias: ProductAliasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.productAlias.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductAliasUpdateComponent,
    resolve: {
      productAlias: ProductAliasResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'mySampleApp.productAlias.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
