import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { TransportDetailsUpdateComponent } from 'app/entities/transport-details/transport-details-update.component';
import { TransportDetailsService } from 'app/entities/transport-details/transport-details.service';
import { TransportDetails } from 'app/shared/model/transport-details.model';

describe('Component Tests', () => {
  describe('TransportDetails Management Update Component', () => {
    let comp: TransportDetailsUpdateComponent;
    let fixture: ComponentFixture<TransportDetailsUpdateComponent>;
    let service: TransportDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [TransportDetailsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(TransportDetailsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportDetailsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportDetailsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TransportDetails(123);
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
        const entity = new TransportDetails();
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
