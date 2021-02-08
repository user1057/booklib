import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BooklibTestModule } from '../../../test.module';
import { PageContentComponent } from 'app/entities/page-content/page-content.component';
import { PageContentService } from 'app/entities/page-content/page-content.service';
import { PageContent } from 'app/shared/model/page-content.model';

describe('Component Tests', () => {
  describe('PageContent Management Component', () => {
    let comp: PageContentComponent;
    let fixture: ComponentFixture<PageContentComponent>;
    let service: PageContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [PageContentComponent]
      })
        .overrideTemplate(PageContentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageContentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageContentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PageContent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pageContents && comp.pageContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
