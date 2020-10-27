import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { PackingUpdateComponent } from 'app/entities/packing/packing-update.component';
import { PackingService } from 'app/entities/packing/packing.service';
import { Packing } from 'app/shared/model/packing.model';

describe('Component Tests', () => {
  describe('Packing Management Update Component', () => {
    let comp: PackingUpdateComponent;
    let fixture: ComponentFixture<PackingUpdateComponent>;
    let service: PackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [PackingUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(PackingUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PackingUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PackingService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Packing(123);
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
        const entity = new Packing();
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
