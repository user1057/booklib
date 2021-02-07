import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { BooklibTestModule } from '../../../test.module';
import { BookContentDetailComponent } from 'app/entities/book-content/book-content-detail.component';
import { BookContent } from 'app/shared/model/book-content.model';

describe('Component Tests', () => {
  describe('BookContent Management Detail Component', () => {
    let comp: BookContentDetailComponent;
    let fixture: ComponentFixture<BookContentDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ bookContent: new BookContent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [BookContentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BookContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BookContentDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load bookContent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bookContent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
