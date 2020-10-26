import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { EmailAddressComponent } from 'app/entities/email-address/email-address.component';
import { EmailAddressService } from 'app/entities/email-address/email-address.service';
import { EmailAddress } from 'app/shared/model/email-address.model';

describe('Component Tests', () => {
  describe('EmailAddress Management Component', () => {
    let comp: EmailAddressComponent;
    let fixture: ComponentFixture<EmailAddressComponent>;
    let service: EmailAddressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [EmailAddressComponent],
      })
        .overrideTemplate(EmailAddressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmailAddressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmailAddressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EmailAddress(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.emailAddresses && comp.emailAddresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
