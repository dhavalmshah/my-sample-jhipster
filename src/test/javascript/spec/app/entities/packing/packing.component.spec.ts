import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { PackingComponent } from 'app/entities/packing/packing.component';
import { PackingService } from 'app/entities/packing/packing.service';
import { Packing } from 'app/shared/model/packing.model';

describe('Component Tests', () => {
  describe('Packing Management Component', () => {
    let comp: PackingComponent;
    let fixture: ComponentFixture<PackingComponent>;
    let service: PackingService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [PackingComponent],
      })
        .overrideTemplate(PackingComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PackingComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PackingService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Packing(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.packings && comp.packings[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
