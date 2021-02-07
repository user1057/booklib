package com.jhipster.booklib.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.jhipster.booklib.domain.Book} entity. This class is used
 * in {@link com.jhipster.booklib.web.rest.BookResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /books?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter isbn;

    private IntegerFilter pageCount;

    private BooleanFilter processed;

    private LongFilter contentId;

    public BookCriteria() {
    }

    public BookCriteria(BookCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.pageCount = other.pageCount == null ? null : other.pageCount.copy();
        this.processed = other.processed == null ? null : other.processed.copy();
        this.contentId = other.contentId == null ? null : other.contentId.copy();
    }

    @Override
    public BookCriteria copy() {
        return new BookCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIsbn() {
        return isbn;
    }

    public void setIsbn(StringFilter isbn) {
        this.isbn = isbn;
    }

    public IntegerFilter getPageCount() {
        return pageCount;
    }

    public void setPageCount(IntegerFilter pageCount) {
        this.pageCount = pageCount;
    }

    public BooleanFilter getProcessed() {
        return processed;
    }

    public void setProcessed(BooleanFilter processed) {
        this.processed = processed;
    }

    public LongFilter getContentId() {
        return contentId;
    }

    public void setContentId(LongFilter contentId) {
        this.contentId = contentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookCriteria that = (BookCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(pageCount, that.pageCount) &&
            Objects.equals(processed, that.processed) &&
            Objects.equals(contentId, that.contentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isbn,
        pageCount,
        processed,
        contentId
        );
    }

    @Override
    public String toString() {
        return "BookCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (pageCount != null ? "pageCount=" + pageCount + ", " : "") +
                (processed != null ? "processed=" + processed + ", " : "") +
                (contentId != null ? "contentId=" + contentId + ", " : "") +
            "}";
    }

}
