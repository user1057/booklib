import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IPageUrl, PageUrl } from 'app/shared/model/page-url.model';
import { PageUrlService } from './page-url.service';

@Component({
  selector: 'jhi-page-url-update',
  templateUrl: './page-url-update.component.html'
})
export class PageUrlUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    isbn: [],
    page: [],
    hash: [],
    startTime: []
  });

  constructor(protected pageUrlService: PageUrlService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pageUrl }) => {
      if (!pageUrl.id) {
        const today = moment().startOf('day');
        pageUrl.startTime = today;
      }

      this.updateForm(pageUrl);
    });
  }

  updateForm(pageUrl: IPageUrl): void {
    this.editForm.patchValue({
      id: pageUrl.id,
      isbn: pageUrl.isbn,
      page: pageUrl.page,
      hash: pageUrl.hash,
      startTime: pageUrl.startTime ? pageUrl.startTime.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pageUrl = this.createFromForm();
    if (pageUrl.id !== undefined) {
      this.subscribeToSaveResponse(this.pageUrlService.update(pageUrl));
    } else {
      this.subscribeToSaveResponse(this.pageUrlService.create(pageUrl));
    }
  }

  private createFromForm(): IPageUrl {
    return {
      ...new PageUrl(),
      id: this.editForm.get(['id'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      page: this.editForm.get(['page'])!.value,
      hash: this.editForm.get(['hash'])!.value,
      startTime: this.editForm.get(['startTime'])!.value ? moment(this.editForm.get(['startTime'])!.value, DATE_TIME_FORMAT) : undefined
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPageUrl>>): void {
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
