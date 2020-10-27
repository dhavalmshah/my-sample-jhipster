import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { BillingLocationUpdateComponent } from 'app/entities/billing-location/billing-location-update.component';
import { BillingLocationService } from 'app/entities/billing-location/billing-location.service';
import { BillingLocation } from 'app/shared/model/billing-location.model';

describe('Component Tests', () => {
  describe('BillingLocation Management Update Component', () => {
    let comp: BillingLocationUpdateComponent;
    let fixture: ComponentFixture<BillingLocationUpdateComponent>;
    let service: BillingLocationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [BillingLocationUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillingLocationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingLocationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingLocationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillingLocation(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillingLocation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
