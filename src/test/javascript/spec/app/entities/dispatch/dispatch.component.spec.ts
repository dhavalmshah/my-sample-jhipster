import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { DispatchComponent } from 'app/entities/dispatch/dispatch.component';
import { DispatchService } from 'app/entities/dispatch/dispatch.service';
import { Dispatch } from 'app/shared/model/dispatch.model';

describe('Component Tests', () => {
  describe('Dispatch Management Component', () => {
    let comp: DispatchComponent;
    let fixture: ComponentFixture<DispatchComponent>;
    let service: DispatchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [DispatchComponent],
      })
        .overrideTemplate(DispatchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DispatchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DispatchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Dispatch(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dispatches && comp.dispatches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
