import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IPageContent, PageContent } from 'app/shared/model/page-content.model';
import { PageContentService } from './page-content.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-page-content-update',
  templateUrl: './page-content-update.component.html'
})
export class PageContentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    isbn: [],
    page: [],
    data: [],
    dataContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected pageContentService: PageContentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageContent }) => {
      this.updateForm(pageContent);
    });
  }

  updateForm(pageContent: IPageContent): void {
    this.editForm.patchValue({
      id: pageContent.id,
      isbn: pageContent.isbn,
      page: pageContent.page,
      data: pageContent.data,
      dataContentType: pageContent.dataContentType
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('booklibApp.error', { message: err.message })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pageContent = this.createFromForm();
    if (pageContent.id !== undefined) {
      this.subscribeToSaveResponse(this.pageContentService.update(pageContent));
    } else {
      this.subscribeToSaveResponse(this.pageContentService.create(pageContent));
    }
  }

  private createFromForm(): IPageContent {
    return {
      ...new PageContent(),
      id: this.editForm.get(['id'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      page: this.editForm.get(['page'])!.value,
      dataContentType: this.editForm.get(['dataContentType'])!.value,
      data: this.editForm.get(['data'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageContent>>): void {
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
}
