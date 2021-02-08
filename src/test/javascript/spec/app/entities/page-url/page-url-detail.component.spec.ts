import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BooklibTestModule } from '../../../test.module';
import { PageUrlDetailComponent } from 'app/entities/page-url/page-url-detail.component';
import { PageUrl } from 'app/shared/model/page-url.model';

describe('Component Tests', () => {
  describe('PageUrl Management Detail Component', () => {
    let comp: PageUrlDetailComponent;
    let fixture: ComponentFixture<PageUrlDetailComponent>;
    const route = ({ data: of({ pageUrl: new PageUrl(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [PageUrlDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PageUrlDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PageUrlDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load pageUrl on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.pageUrl).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
