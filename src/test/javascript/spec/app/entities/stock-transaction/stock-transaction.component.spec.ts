import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MySampleTestModule } from '../../../test.module';
import { StockTransactionComponent } from 'app/entities/stock-transaction/stock-transaction.component';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';
import { StockTransaction } from 'app/shared/model/stock-transaction.model';

describe('Component Tests', () => {
  describe('StockTransaction Management Component', () => {
    let comp: StockTransactionComponent;
    let fixture: ComponentFixture<StockTransactionComponent>;
    let service: StockTransactionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MySampleTestModule],
        declarations: [StockTransactionComponent],
      })
        .overrideTemplate(StockTransactionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StockTransactionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(StockTransactionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new StockTransaction(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.stockTransactions && comp.stockTransactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
