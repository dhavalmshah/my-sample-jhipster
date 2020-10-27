import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { TransportDetailsService } from 'app/entities/transport-details/transport-details.service';
import { ITransportDetails, TransportDetails } from 'app/shared/model/transport-details.model';

describe('Service Tests', () => {
  describe('TransportDetails Service', () => {
    let injector: TestBed;
    let service: TransportDetailsService;
    let httpMock: HttpTestingController;
    let elemDefault: ITransportDetails;
    let expectedResult: ITransportDetails | ITransportDetails[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(TransportDetailsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new TransportDetails(0, 'AAAAAAA', currentDate, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            transportStartDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedEndDate: currentDate.format(DATE_TIME_FORMAT),
            actualEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TransportDetails', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transportStartDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedEndDate: currentDate.format(DATE_TIME_FORMAT),
            actualEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transportStartDate: currentDate,
            estimatedEndDate: currentDate,
            actualEndDate: currentDate,
          },
          returnedFromService
        );

        service.create(new TransportDetails()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TransportDetails', () => {
        const returnedFromService = Object.assign(
          {
            transportNumber: 'BBBBBB',
            transportStartDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedEndDate: currentDate.format(DATE_TIME_FORMAT),
            actualEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transportStartDate: currentDate,
            estimatedEndDate: currentDate,
            actualEndDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TransportDetails', () => {
        const returnedFromService = Object.assign(
          {
            transportNumber: 'BBBBBB',
            transportStartDate: currentDate.format(DATE_TIME_FORMAT),
            estimatedEndDate: currentDate.format(DATE_TIME_FORMAT),
            actualEndDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transportStartDate: currentDate,
            estimatedEndDate: currentDate,
            actualEndDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TransportDetails', () => {
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
