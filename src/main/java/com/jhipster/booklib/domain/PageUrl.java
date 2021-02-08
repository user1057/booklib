package com.jhipster.booklib.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A PageUrl.
 */
@Entity
@Table(name = "page_url")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PageUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "page")
    private Integer page;

    @Column(name = "hash")
    private String hash;

    @Column(name = "start_time")
    private Instant startTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public PageUrl isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPage() {
        return page;
    }

    public PageUrl page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getHash() {
        return hash;
    }

    public PageUrl hash(String hash) {
        this.hash = hash;
        return this;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public PageUrl startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageUrl)) {
            return false;
        }
        return id != null && id.equals(((PageUrl) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PageUrl{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", page=" + getPage() +
            ", hash='" + getHash() + "'" +
            ", startTime='" + getStartTime() + "'" +
            "}";
    }
}
