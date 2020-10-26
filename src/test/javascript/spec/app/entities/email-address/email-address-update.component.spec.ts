import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { EmailAddressUpdateComponent } from 'app/entities/email-address/email-address-update.component';
import { EmailAddressService } from 'app/entities/email-address/email-address.service';
import { EmailAddress } from 'app/shared/model/email-address.model';

describe('Component Tests', () => {
  describe('EmailAddress Management Update Component', () => {
    let comp: EmailAddressUpdateComponent;
    let fixture: ComponentFixture<EmailAddressUpdateComponent>;
    let service: EmailAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [EmailAddressUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(EmailAddressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailAddressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailAddressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EmailAddress(123);
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
        const entity = new EmailAddress();
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
