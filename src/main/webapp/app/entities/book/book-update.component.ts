import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IBook, Book } from 'app/shared/model/book.model';
import { BookService } from './book.service';
import { IBookContent } from 'app/shared/model/book-content.model';
import { BookContentService } from 'app/entities/book-content/book-content.service';

@Component({
  selector: 'jhi-book-update',
  templateUrl: './book-update.component.html'
})
export class BookUpdateComponent implements OnInit {
  isSaving = false;
  contents: IBookContent[] = [];

  editForm = this.fb.group({
    id: [],
    isbn: [],
    pageCount: [],
    processed: [],
    contentId: []
  });

  constructor(
    protected bookService: BookService,
    protected bookContentService: BookContentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ book }) => {
      this.updateForm(book);

      this.bookContentService
        .query({ filter: 'bookcontent-is-null' })
        .pipe(
          map((res: HttpResponse<IBookContent[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IBookContent[]) => {
          if (!book.contentId) {
            this.contents = resBody;
          } else {
            this.bookContentService
              .find(book.contentId)
              .pipe(
                map((subRes: HttpResponse<IBookContent>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IBookContent[]) => (this.contents = concatRes));
          }
        });
    });
  }

  updateForm(book: IBook): void {
    this.editForm.patchValue({
      id: book.id,
      isbn: book.isbn,
      pageCount: book.pageCount,
      processed: book.processed,
      contentId: book.contentId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const book = this.createFromForm();
    if (book.id !== undefined) {
      this.subscribeToSaveResponse(this.bookService.update(book));
    } else {
      this.subscribeToSaveResponse(this.bookService.create(book));
    }
  }

  private createFromForm(): IBook {
    return {
      ...new Book(),
      id: this.editForm.get(['id'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      pageCount: this.editForm.get(['pageCount'])!.value,
      processed: this.editForm.get(['processed'])!.value,
      contentId: this.editForm.get(['contentId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBook>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IBookContent): any {
    return item.id;
  }
}
