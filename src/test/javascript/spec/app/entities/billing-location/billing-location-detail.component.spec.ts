import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { BillingLocationDetailComponent } from 'app/entities/billing-location/billing-location-detail.component';
import { BillingLocation } from 'app/shared/model/billing-location.model';

describe('Component Tests', () => {
  describe('BillingLocation Management Detail Component', () => {
    let comp: BillingLocationDetailComponent;
    let fixture: ComponentFixture<BillingLocationDetailComponent>;
    const route = ({ data: of({ billingLocation: new BillingLocation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [BillingLocationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillingLocationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingLocationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billingLocation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingLocation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
