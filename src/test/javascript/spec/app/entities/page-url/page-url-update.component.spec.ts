import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BooklibTestModule } from '../../../test.module';
import { PageUrlUpdateComponent } from 'app/entities/page-url/page-url-update.component';
import { PageUrlService } from 'app/entities/page-url/page-url.service';
import { PageUrl } from 'app/shared/model/page-url.model';

describe('Component Tests', () => {
  describe('PageUrl Management Update Component', () => {
    let comp: PageUrlUpdateComponent;
    let fixture: ComponentFixture<PageUrlUpdateComponent>;
    let service: PageUrlService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [PageUrlUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PageUrlUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageUrlUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageUrlService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PageUrl(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PageUrl();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
