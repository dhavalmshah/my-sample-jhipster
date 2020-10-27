import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { ProductionDetailComponent } from 'app/entities/production/production-detail.component';
import { Production } from 'app/shared/model/production.model';

describe('Component Tests', () => {
  describe('Production Management Detail Component', () => {
    let comp: ProductionDetailComponent;
    let fixture: ComponentFixture<ProductionDetailComponent>;
    const route = ({ data: of({ production: new Production(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [ProductionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ProductionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load production on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.production).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
