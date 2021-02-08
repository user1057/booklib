import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { PageUrlService } from 'app/entities/page-url/page-url.service';
import { IPageUrl, PageUrl } from 'app/shared/model/page-url.model';

describe('Service Tests', () => {
  describe('PageUrl Service', () => {
    let injector: TestBed;
    let service: PageUrlService;
    let httpMock: HttpTestingController;
    let elemDefault: IPageUrl;
    let expectedResult: IPageUrl | IPageUrl[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(PageUrlService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new PageUrl(0, 'AAAAAAA', 0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a PageUrl', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate
          },
          returnedFromService
        );

        service.create(new PageUrl()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a PageUrl', () => {
        const returnedFromService = Object.assign(
          {
            isbn: 'BBBBBB',
            page: 1,
            hash: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of PageUrl', () => {
        const returnedFromService = Object.assign(
          {
            isbn: 'BBBBBB',
            page: 1,
            hash: 'BBBBBB',
            startTime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a PageUrl', () => {
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
