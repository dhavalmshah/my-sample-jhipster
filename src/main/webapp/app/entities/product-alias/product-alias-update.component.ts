import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductAlias, ProductAlias } from 'app/shared/model/product-alias.model';
import { ProductAliasService } from './product-alias.service';
import { IProduct } from 'app/shared/model/product.model';
import { ProductService } from 'app/entities/product/product.service';

@Component({
  selector: 'jhi-product-alias-update',
  templateUrl: './product-alias-update.component.html',
})
export class ProductAliasUpdateComponent implements OnInit {
  isSaving = false;
  products: IProduct[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    product: [],
  });

  constructor(
    protected productAliasService: ProductAliasService,
    protected productService: ProductService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productAlias }) => {
      this.updateForm(productAlias);

      this.productService.query().subscribe((res: HttpResponse<IProduct[]>) => (this.products = res.body || []));
    });
  }

  updateForm(productAlias: IProductAlias): void {
    this.editForm.patchValue({
      id: productAlias.id,
      name: productAlias.name,
      product: productAlias.product,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productAlias = this.createFromForm();
    if (productAlias.id !== undefined) {
      this.subscribeToSaveResponse(this.productAliasService.update(productAlias));
    } else {
      this.subscribeToSaveResponse(this.productAliasService.create(productAlias));
    }
  }

  private createFromForm(): IProductAlias {
    return {
      ...new ProductAlias(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      product: this.editForm.get(['product'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductAlias>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IProduct): any {
    return item.id;
  }
}
