import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { StockTransactionService } from 'app/entities/stock-transaction/stock-transaction.service';
import { IStockTransaction, StockTransaction } from 'app/shared/model/stock-transaction.model';
import { StockTransactionType } from 'app/shared/model/enumerations/stock-transaction-type.model';

describe('Service Tests', () => {
  describe('StockTransaction Service', () => {
    let injector: TestBed;
    let service: StockTransactionService;
    let httpMock: HttpTestingController;
    let elemDefault: IStockTransaction;
    let expectedResult: IStockTransaction | IStockTransaction[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(StockTransactionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new StockTransaction(0, 'AAAAAAA', currentDate, StockTransactionType.PURCHASE);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            transactionDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transactionDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate,
          },
          returnedFromService
        );

        service.create(new StockTransaction()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            transactionDate: currentDate.format(DATE_TIME_FORMAT),
            transactionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StockTransaction', () => {
        const returnedFromService = Object.assign(
          {
            description: 'BBBBBB',
            transactionDate: currentDate.format(DATE_TIME_FORMAT),
            transactionType: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transactionDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a StockTransaction', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
