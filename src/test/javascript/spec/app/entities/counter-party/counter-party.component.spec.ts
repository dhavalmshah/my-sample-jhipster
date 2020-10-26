import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { CounterPartyComponent } from 'app/entities/counter-party/counter-party.component';
import { CounterPartyService } from 'app/entities/counter-party/counter-party.service';
import { CounterParty } from 'app/shared/model/counter-party.model';

describe('Component Tests', () => {
  describe('CounterParty Management Component', () => {
    let comp: CounterPartyComponent;
    let fixture: ComponentFixture<CounterPartyComponent>;
    let service: CounterPartyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [CounterPartyComponent],
      })
        .overrideTemplate(CounterPartyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CounterPartyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CounterPartyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CounterParty(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.counterParties && comp.counterParties[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
