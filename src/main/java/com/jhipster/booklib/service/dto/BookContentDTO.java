package com.jhipster.booklib.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.jhipster.booklib.domain.BookContent} entity.
 */
public class BookContentDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private byte[] data;

    private String dataContentType;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

        BookContentDTO bookContentDTO = (BookContentDTO) o;
        if (bookContentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bookContentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BookContentDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            "}";
    }
}
