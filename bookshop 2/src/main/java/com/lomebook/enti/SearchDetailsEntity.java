package com.lomebook.enti;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "SearchDetails", schema = "dbo", catalog = "book_shop3")
public class SearchDetailsEntity {
    private String id;
    private String keyWords;
    private Timestamp searchDateTime;

    @Id
    @Column(name = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "KeyWords")
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Basic
    @Column(name = "SearchDateTime")
    public Timestamp getSearchDateTime() {
        return searchDateTime;
    }

    public void setSearchDateTime(Timestamp searchDateTime) {
        this.searchDateTime = searchDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchDetailsEntity that = (SearchDetailsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (keyWords != null ? !keyWords.equals(that.keyWords) : that.keyWords != null) return false;
        if (searchDateTime != null ? !searchDateTime.equals(that.searchDateTime) : that.searchDateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (keyWords != null ? keyWords.hashCode() : 0);
        result = 31 * result + (searchDateTime != null ? searchDateTime.hashCode() : 0);
        return result;
    }
}
