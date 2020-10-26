import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductType, ProductType } from 'app/shared/model/product-type.model';
import { ProductTypeService } from './product-type.service';

@Component({
  selector: 'jhi-product-type-update',
  templateUrl: './product-type-update.component.html',
})
export class ProductTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(255)]],
    category: [],
    quantityType: [],
    hsnNumber: [null, [Validators.required]],
    description: [null, [Validators.required, Validators.maxLength(65535)]],
  });

  constructor(protected productTypeService: ProductTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productType }) => {
      this.updateForm(productType);
    });
  }

  updateForm(productType: IProductType): void {
    this.editForm.patchValue({
      id: productType.id,
      name: productType.name,
      category: productType.category,
      quantityType: productType.quantityType,
      hsnNumber: productType.hsnNumber,
      description: productType.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productType = this.createFromForm();
    if (productType.id !== undefined) {
      this.subscribeToSaveResponse(this.productTypeService.update(productType));
    } else {
      this.subscribeToSaveResponse(this.productTypeService.create(productType));
    }
  }

  private createFromForm(): IProductType {
    return {
      ...new ProductType(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value,
      quantityType: this.editForm.get(['quantityType'])!.value,
      hsnNumber: this.editForm.get(['hsnNumber'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductType>>): void {
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
}
