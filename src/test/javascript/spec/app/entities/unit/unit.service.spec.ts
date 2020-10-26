import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UnitService } from 'app/entities/unit/unit.service';
import { IUnit, Unit } from 'app/shared/model/unit.model';
import { QuantityType } from 'app/shared/model/enumerations/quantity-type.model';

describe('Service Tests', () => {
  describe('Unit Service', () => {
    let injector: TestBed;
    let service: UnitService;
    let httpMock: HttpTestingController;
    let elemDefault: IUnit;
    let expectedResult: IUnit | IUnit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UnitService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Unit(0, 'AAAAAAA', QuantityType.VOLUME, 'AAAAAAA', 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Unit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Unit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Unit', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            quantityType: 'BBBBBB',
            fullName: 'BBBBBB',
            baseUnitMultiplier: 1,
            isSmallerThanBase: true,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Unit', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            quantityType: 'BBBBBB',
            fullName: 'BBBBBB',
            baseUnitMultiplier: 1,
            isSmallerThanBase: true,
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

      it('should delete a Unit', () => {
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