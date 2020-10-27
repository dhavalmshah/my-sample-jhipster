import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { DispatchUpdateComponent } from 'app/entities/dispatch/dispatch-update.component';
import { DispatchService } from 'app/entities/dispatch/dispatch.service';
import { Dispatch } from 'app/shared/model/dispatch.model';

describe('Component Tests', () => {
  describe('Dispatch Management Update Component', () => {
    let comp: DispatchUpdateComponent;
    let fixture: ComponentFixture<DispatchUpdateComponent>;
    let service: DispatchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [DispatchUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DispatchUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DispatchUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DispatchService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dispatch(123);
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
        const entity = new Dispatch();
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
