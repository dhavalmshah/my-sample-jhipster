import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { GoodsReceivedComponent } from 'app/entities/goods-received/goods-received.component';
import { GoodsReceivedService } from 'app/entities/goods-received/goods-received.service';
import { GoodsReceived } from 'app/shared/model/goods-received.model';

describe('Component Tests', () => {
  describe('GoodsReceived Management Component', () => {
    let comp: GoodsReceivedComponent;
    let fixture: ComponentFixture<GoodsReceivedComponent>;
    let service: GoodsReceivedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [GoodsReceivedComponent],
      })
        .overrideTemplate(GoodsReceivedComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GoodsReceivedComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GoodsReceivedService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GoodsReceived(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.goodsReceiveds && comp.goodsReceiveds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
