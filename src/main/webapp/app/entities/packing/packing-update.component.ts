import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPacking, Packing } from 'app/shared/model/packing.model';
import { PackingService } from './packing.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-packing-update',
  templateUrl: './packing-update.component.html',
})
export class PackingUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required, Validators.maxLength(10)]],
    quantity: [null, [Validators.required, Validators.min(0)]],
    packingType: [null, [Validators.required]],
    netWeight: [null, [Validators.required, Validators.min(0)]],
    grossWeight: [null, [Validators.required, Validators.min(0)]],
    unit: [null, Validators.required],
  });

  constructor(
    protected packingService: PackingService,
    protected unitService: UnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ packing }) => {
      this.updateForm(packing);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
    });
  }

  updateForm(packing: IPacking): void {
    this.editForm.patchValue({
      id: packing.id,
      name: packing.name,
      quantity: packing.quantity,
      packingType: packing.packingType,
      netWeight: packing.netWeight,
      grossWeight: packing.grossWeight,
      unit: packing.unit,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const packing = this.createFromForm();
    if (packing.id !== undefined) {
      this.subscribeToSaveResponse(this.packingService.update(packing));
    } else {
      this.subscribeToSaveResponse(this.packingService.create(packing));
    }
  }

  private createFromForm(): IPacking {
    return {
      ...new Packing(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      packingType: this.editForm.get(['packingType'])!.value,
      netWeight: this.editForm.get(['netWeight'])!.value,
      grossWeight: this.editForm.get(['grossWeight'])!.value,
      unit: this.editForm.get(['unit'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPacking>>): void {
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

  trackById(index: number, item: IUnit): any {
    return item.id;
  }
}
