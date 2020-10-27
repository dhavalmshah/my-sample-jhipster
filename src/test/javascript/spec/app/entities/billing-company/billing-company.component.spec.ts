import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { BillingCompanyComponent } from 'app/entities/billing-company/billing-company.component';
import { BillingCompanyService } from 'app/entities/billing-company/billing-company.service';
import { BillingCompany } from 'app/shared/model/billing-company.model';

describe('Component Tests', () => {
  describe('BillingCompany Management Component', () => {
    let comp: BillingCompanyComponent;
    let fixture: ComponentFixture<BillingCompanyComponent>;
    let service: BillingCompanyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [BillingCompanyComponent],
      })
        .overrideTemplate(BillingCompanyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BillingCompanyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BillingCompanyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BillingCompany(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.billingCompanies && comp.billingCompanies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
