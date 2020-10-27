import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { GoodsReceivedUpdateComponent } from 'app/entities/goods-received/goods-received-update.component';
import { GoodsReceivedService } from 'app/entities/goods-received/goods-received.service';
import { GoodsReceived } from 'app/shared/model/goods-received.model';

describe('Component Tests', () => {
  describe('GoodsReceived Management Update Component', () => {
    let comp: GoodsReceivedUpdateComponent;
    let fixture: ComponentFixture<GoodsReceivedUpdateComponent>;
    let service: GoodsReceivedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [GoodsReceivedUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GoodsReceivedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodsReceivedUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodsReceivedService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GoodsReceived(123);
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
        const entity = new GoodsReceived();
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
