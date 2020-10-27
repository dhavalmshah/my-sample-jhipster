import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { TransportDetailsComponent } from 'app/entities/transport-details/transport-details.component';
import { TransportDetailsService } from 'app/entities/transport-details/transport-details.service';
import { TransportDetails } from 'app/shared/model/transport-details.model';

describe('Component Tests', () => {
  describe('TransportDetails Management Component', () => {
    let comp: TransportDetailsComponent;
    let fixture: ComponentFixture<TransportDetailsComponent>;
    let service: TransportDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [TransportDetailsComponent],
      })
        .overrideTemplate(TransportDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TransportDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransportDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TransportDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.transportDetails && comp.transportDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
