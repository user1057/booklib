import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { BooklibTestModule } from '../../../test.module';
import { BookContentUpdateComponent } from 'app/entities/book-content/book-content-update.component';
import { BookContentService } from 'app/entities/book-content/book-content.service';
import { BookContent } from 'app/shared/model/book-content.model';

describe('Component Tests', () => {
  describe('BookContent Management Update Component', () => {
    let comp: BookContentUpdateComponent;
    let fixture: ComponentFixture<BookContentUpdateComponent>;
    let service: BookContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BooklibTestModule],
        declarations: [BookContentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(BookContentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BookContentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BookContentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BookContent(123);
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
        const entity = new BookContent();
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
