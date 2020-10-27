import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { ProductAliasComponent } from 'app/entities/product-alias/product-alias.component';
import { ProductAliasService } from 'app/entities/product-alias/product-alias.service';
import { ProductAlias } from 'app/shared/model/product-alias.model';

describe('Component Tests', () => {
  describe('ProductAlias Management Component', () => {
    let comp: ProductAliasComponent;
    let fixture: ComponentFixture<ProductAliasComponent>;
    let service: ProductAliasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [ProductAliasComponent],
      })
        .overrideTemplate(ProductAliasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductAliasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductAliasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductAlias(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productAliases && comp.productAliases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
