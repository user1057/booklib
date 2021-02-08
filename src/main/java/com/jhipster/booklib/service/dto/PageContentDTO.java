package com.jhipster.booklib.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.jhipster.booklib.domain.PageContent} entity.
 */
public class PageContentDTO implements Serializable {
    
    private Long id;

    private String isbn;

    private Integer page;

    @Lob
    private byte[] data;

    private String dataContentType;
    
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getDataContentType() {
        return dataContentType;
    }

    public void setDataContentType(String dataContentType) {
        this.dataContentType = dataContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PageContentDTO pageContentDTO = (PageContentDTO) o;
        if (pageContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pageContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PageContentDTO{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", page=" + getPage() +
            ", data='" + getData() + "'" +
            "}";
    }
}
