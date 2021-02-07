import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BooklibTestModule } from '../../../test.module';
import { BookContentComponent } from 'app/entities/book-content/book-content.component';
import { BookContentService } from 'app/entities/book-content/book-content.service';
import { BookContent } from 'app/shared/model/book-content.model';

describe('Component Tests', () => {
  describe('BookContent Management Component', () => {
    let comp: BookContentComponent;
    let fixture: ComponentFixture<BookContentComponent>;
    let service: BookContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [BookContentComponent]
      })
        .overrideTemplate(BookContentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookContentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookContentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BookContent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bookContents && comp.bookContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
