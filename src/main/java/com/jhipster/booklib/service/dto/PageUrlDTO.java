package com.jhipster.booklib.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.jhipster.booklib.domain.PageUrl} entity.
 */
public class PageUrlDTO implements Serializable {
    
    private Long id;

    private String isbn;

    private Integer page;

    private String hash;

    private Instant startTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
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

        PageUrlDTO pageUrlDTO = (PageUrlDTO) o;
        if (pageUrlDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageUrlDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageUrlDTO{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", page=" + getPage() +
            ", hash='" + getHash() + "'" +
            ", startTime='" + getStartTime() + "'" +
            "}";
    }
}
