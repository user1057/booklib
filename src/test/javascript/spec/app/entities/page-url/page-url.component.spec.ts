import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BooklibTestModule } from '../../../test.module';
import { PageUrlComponent } from 'app/entities/page-url/page-url.component';
import { PageUrlService } from 'app/entities/page-url/page-url.service';
import { PageUrl } from 'app/shared/model/page-url.model';

describe('Component Tests', () => {
  describe('PageUrl Management Component', () => {
    let comp: PageUrlComponent;
    let fixture: ComponentFixture<PageUrlComponent>;
    let service: PageUrlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [PageUrlComponent]
      })
        .overrideTemplate(PageUrlComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageUrlComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageUrlService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PageUrl(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pageUrls && comp.pageUrls[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
