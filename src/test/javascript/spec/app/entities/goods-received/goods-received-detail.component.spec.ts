import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MySampleTestModule } from '../../../test.module';
import { GoodsReceivedDetailComponent } from 'app/entities/goods-received/goods-received-detail.component';
import { GoodsReceived } from 'app/shared/model/goods-received.model';

describe('Component Tests', () => {
  describe('GoodsReceived Management Detail Component', () => {
    let comp: GoodsReceivedDetailComponent;
    let fixture: ComponentFixture<GoodsReceivedDetailComponent>;
    const route = ({ data: of({ goodsReceived: new GoodsReceived(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [GoodsReceivedDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GoodsReceivedDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GoodsReceivedDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load goodsReceived on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.goodsReceived).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
