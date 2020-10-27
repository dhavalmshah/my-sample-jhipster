import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { PhoneNumberComponent } from 'app/entities/phone-number/phone-number.component';
import { PhoneNumberService } from 'app/entities/phone-number/phone-number.service';
import { PhoneNumber } from 'app/shared/model/phone-number.model';

describe('Component Tests', () => {
  describe('PhoneNumber Management Component', () => {
    let comp: PhoneNumberComponent;
    let fixture: ComponentFixture<PhoneNumberComponent>;
    let service: PhoneNumberService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [PhoneNumberComponent],
      })
        .overrideTemplate(PhoneNumberComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhoneNumberComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PhoneNumberService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PhoneNumber(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.phoneNumbers && comp.phoneNumbers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
