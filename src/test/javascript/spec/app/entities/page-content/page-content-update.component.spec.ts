import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BooklibTestModule } from '../../../test.module';
import { PageContentUpdateComponent } from 'app/entities/page-content/page-content-update.component';
import { PageContentService } from 'app/entities/page-content/page-content.service';
import { PageContent } from 'app/shared/model/page-content.model';

describe('Component Tests', () => {
  describe('PageContent Management Update Component', () => {
    let comp: PageContentUpdateComponent;
    let fixture: ComponentFixture<PageContentUpdateComponent>;
    let service: PageContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [PageContentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PageContentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PageContentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PageContentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PageContent(123);
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
        const entity = new PageContent();
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
