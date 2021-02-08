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
 * Criteria class for the {@link com.jhipster.booklib.domain.PageContent} entity. This class is used
 * in {@link com.jhipster.booklib.web.rest.PageContentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-contents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PageContentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter isbn;

    private IntegerFilter page;

    public PageContentCriteria() {
    }

    public PageContentCriteria(PageContentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.page = other.page == null ? null : other.page.copy();
    }

    @Override
    public PageContentCriteria copy() {
        return new PageContentCriteria(this);
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

    public IntegerFilter getPage() {
        return page;
    }

    public void setPage(IntegerFilter page) {
        this.page = page;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageContentCriteria that = (PageContentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isbn,
        page
        );
    }

    @Override
    public String toString() {
        return "PageContentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (page != null ? "page=" + page + ", " : "") +
            "}";
    }

}
