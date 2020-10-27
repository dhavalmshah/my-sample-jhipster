import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { BillingCompanyUpdateComponent } from 'app/entities/billing-company/billing-company-update.component';
import { BillingCompanyService } from 'app/entities/billing-company/billing-company.service';
import { BillingCompany } from 'app/shared/model/billing-company.model';

describe('Component Tests', () => {
  describe('BillingCompany Management Update Component', () => {
    let comp: BillingCompanyUpdateComponent;
    let fixture: ComponentFixture<BillingCompanyUpdateComponent>;
    let service: BillingCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [BillingCompanyUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BillingCompanyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingCompanyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingCompanyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BillingCompany(123);
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
        const entity = new BillingCompany();
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
