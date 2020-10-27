import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PackingService } from 'app/entities/packing/packing.service';
import { IPacking, Packing } from 'app/shared/model/packing.model';
import { PackingType } from 'app/shared/model/enumerations/packing-type.model';

describe('Service Tests', () => {
  describe('Packing Service', () => {
    let injector: TestBed;
    let service: PackingService;
    let httpMock: HttpTestingController;
    let elemDefault: IPacking;
    let expectedResult: IPacking | IPacking[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PackingService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Packing(0, 'AAAAAAA', 0, PackingType.Drum, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Packing', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Packing()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Packing', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            quantity: 1,
            packingType: 'BBBBBB',
            netWeight: 1,
            grossWeight: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Packing', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            quantity: 1,
            packingType: 'BBBBBB',
            netWeight: 1,
            grossWeight: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Packing', () => {
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
