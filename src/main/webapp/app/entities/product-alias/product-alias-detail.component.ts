import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductAlias } from 'app/shared/model/product-alias.model';

@Component({
  selector: 'jhi-product-alias-detail',
  templateUrl: './product-alias-detail.component.html',
})
export class ProductAliasDetailComponent implements OnInit {
  productAlias: IProductAlias | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAlias }) => (this.productAlias = productAlias));
  }

  previousState(): void {
    window.history.back();
  }
}
