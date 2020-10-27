import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { ProductAliasUpdateComponent } from 'app/entities/product-alias/product-alias-update.component';
import { ProductAliasService } from 'app/entities/product-alias/product-alias.service';
import { ProductAlias } from 'app/shared/model/product-alias.model';

describe('Component Tests', () => {
  describe('ProductAlias Management Update Component', () => {
    let comp: ProductAliasUpdateComponent;
    let fixture: ComponentFixture<ProductAliasUpdateComponent>;
    let service: ProductAliasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [ProductAliasUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ProductAliasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAliasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAliasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductAlias(123);
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
        const entity = new ProductAlias();
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
