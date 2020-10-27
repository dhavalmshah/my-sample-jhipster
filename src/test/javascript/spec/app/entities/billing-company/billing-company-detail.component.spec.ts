import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { BillingCompanyDetailComponent } from 'app/entities/billing-company/billing-company-detail.component';
import { BillingCompany } from 'app/shared/model/billing-company.model';

describe('Component Tests', () => {
  describe('BillingCompany Management Detail Component', () => {
    let comp: BillingCompanyDetailComponent;
    let fixture: ComponentFixture<BillingCompanyDetailComponent>;
    const route = ({ data: of({ billingCompany: new BillingCompany(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [BillingCompanyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BillingCompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BillingCompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load billingCompany on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.billingCompany).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
