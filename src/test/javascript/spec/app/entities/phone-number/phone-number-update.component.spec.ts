import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { PhoneNumberUpdateComponent } from 'app/entities/phone-number/phone-number-update.component';
import { PhoneNumberService } from 'app/entities/phone-number/phone-number.service';
import { PhoneNumber } from 'app/shared/model/phone-number.model';

describe('Component Tests', () => {
  describe('PhoneNumber Management Update Component', () => {
    let comp: PhoneNumberUpdateComponent;
    let fixture: ComponentFixture<PhoneNumberUpdateComponent>;
    let service: PhoneNumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [PhoneNumberUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PhoneNumberUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhoneNumberUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhoneNumberService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PhoneNumber(123);
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
        const entity = new PhoneNumber();
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
