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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.jhipster.booklib.domain.PageUrl} entity. This class is used
 * in {@link com.jhipster.booklib.web.rest.PageUrlResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /page-urls?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PageUrlCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter isbn;

    private IntegerFilter page;

    private StringFilter hash;

    private InstantFilter startTime;

    public PageUrlCriteria() {
    }

    public PageUrlCriteria(PageUrlCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.isbn = other.isbn == null ? null : other.isbn.copy();
        this.page = other.page == null ? null : other.page.copy();
        this.hash = other.hash == null ? null : other.hash.copy();
        this.startTime = other.startTime == null ? null : other.startTime.copy();
    }

    @Override
    public PageUrlCriteria copy() {
        return new PageUrlCriteria(this);
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

    public StringFilter getHash() {
        return hash;
    }

    public void setHash(StringFilter hash) {
        this.hash = hash;
    }

    public InstantFilter getStartTime() {
        return startTime;
    }

    public void setStartTime(InstantFilter startTime) {
        this.startTime = startTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PageUrlCriteria that = (PageUrlCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(isbn, that.isbn) &&
            Objects.equals(page, that.page) &&
            Objects.equals(hash, that.hash) &&
            Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        isbn,
        page,
        hash,
        startTime
        );
    }

    @Override
    public String toString() {
        return "PageUrlCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (isbn != null ? "isbn=" + isbn + ", " : "") +
                (page != null ? "page=" + page + ", " : "") +
                (hash != null ? "hash=" + hash + ", " : "") +
                (startTime != null ? "startTime=" + startTime + ", " : "") +
            "}";
    }

}
