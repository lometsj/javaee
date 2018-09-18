package com.lomebook.enti;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Books", schema = "dbo", catalog = "book_shop3")
public class BooksEntity {
    private int id;
    private String title;
    private String author;
    private int publisherId;
    private Timestamp publishDate;
    private String isbn;
    private int wordsCount;
    private int unitPrice;
    private String contentDescription;
    private String aurhorDescription;
    private String editorComment;
    private String toc;
    private int categoryId;
    private int clicks;

    @Id
    @Column(name = "Id", nullable = false)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Title", nullable = false, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "Author", nullable = false, length = 200)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Basic
    @Column(name = "PublisherId", nullable = false)
    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    @Basic
    @Column(name = "PublishDate", nullable = false)
    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    @Basic
    @Column(name = "ISBN", nullable = false, length = 50)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Basic
    @Column(name = "WordsCount", nullable = false)
    public int getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(int wordsCount) {
        this.wordsCount = wordsCount;
    }

    @Basic
    @Column(name = "UnitPrice", nullable = false)
    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "ContentDescription", nullable = true, length = 2147483647)
    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    @Basic
    @Column(name = "AurhorDescription", nullable = true, length = 2147483647)
    public String getAurhorDescription() {
        return aurhorDescription;
    }

    public void setAurhorDescription(String aurhorDescription) {
        this.aurhorDescription = aurhorDescription;
    }

    @Basic
    @Column(name = "EditorComment", nullable = true, length = 2147483647)
    public String getEditorComment() {
        return editorComment;
    }

    public void setEditorComment(String editorComment) {
        this.editorComment = editorComment;
    }

    @Basic
    @Column(name = "TOC", nullable = true, length = 2147483647)
    public String getToc() {
        return toc;
    }

    public void setToc(String toc) {
        this.toc = toc;
    }

    @Basic
    @Column(name = "CategoryId", nullable = false)
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "Clicks", nullable = false)
    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BooksEntity that = (BooksEntity) o;

        if (id != that.id) return false;
        if (publisherId != that.publisherId) return false;
        if (wordsCount != that.wordsCount) return false;
        if (unitPrice != that.unitPrice) return false;
        if (categoryId != that.categoryId) return false;
        if (clicks != that.clicks) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (publishDate != null ? !publishDate.equals(that.publishDate) : that.publishDate != null) return false;
        if (isbn != null ? !isbn.equals(that.isbn) : that.isbn != null) return false;
        if (contentDescription != null ? !contentDescription.equals(that.contentDescription) : that.contentDescription != null)
            return false;
        if (aurhorDescription != null ? !aurhorDescription.equals(that.aurhorDescription) : that.aurhorDescription != null)
            return false;
        if (editorComment != null ? !editorComment.equals(that.editorComment) : that.editorComment != null)
            return false;
        if (toc != null ? !toc.equals(that.toc) : that.toc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + publisherId;
        result = 31 * result + (publishDate != null ? publishDate.hashCode() : 0);
        result = 31 * result + (isbn != null ? isbn.hashCode() : 0);
        result = 31 * result + wordsCount;
        result = 31 * result + unitPrice;
        result = 31 * result + (contentDescription != null ? contentDescription.hashCode() : 0);
        result = 31 * result + (aurhorDescription != null ? aurhorDescription.hashCode() : 0);
        result = 31 * result + (editorComment != null ? editorComment.hashCode() : 0);
        result = 31 * result + (toc != null ? toc.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + clicks;
        return result;
    }
}
