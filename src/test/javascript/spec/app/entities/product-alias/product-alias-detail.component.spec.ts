import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { ProductAliasDetailComponent } from 'app/entities/product-alias/product-alias-detail.component';
import { ProductAlias } from 'app/shared/model/product-alias.model';

describe('Component Tests', () => {
  describe('ProductAlias Management Detail Component', () => {
    let comp: ProductAliasDetailComponent;
    let fixture: ComponentFixture<ProductAliasDetailComponent>;
    const route = ({ data: of({ productAlias: new ProductAlias(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [ProductAliasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductAliasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductAliasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productAlias on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productAlias).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
