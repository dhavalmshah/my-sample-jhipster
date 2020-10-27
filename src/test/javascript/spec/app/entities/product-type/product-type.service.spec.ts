import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProductTypeService } from 'app/entities/product-type/product-type.service';
import { IProductType, ProductType } from 'app/shared/model/product-type.model';
import { ProductCategory } from 'app/shared/model/enumerations/product-category.model';
import { QuantityType } from 'app/shared/model/enumerations/quantity-type.model';

describe('Service Tests', () => {
  describe('ProductType Service', () => {
    let injector: TestBed;
    let service: ProductTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IProductType;
    let expectedResult: IProductType | IProductType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProductTypeService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProductType(0, 'AAAAAAA', ProductCategory.RAWMATERIAL, QuantityType.VOLUME, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProductType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ProductType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProductType', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            category: 'BBBBBB',
            quantityType: 'BBBBBB',
            hsnNumber: 'BBBBBB',
            description: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProductType', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            category: 'BBBBBB',
            quantityType: 'BBBBBB',
            hsnNumber: 'BBBBBB',
            description: 'BBBBBB',
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

      it('should delete a ProductType', () => {
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
