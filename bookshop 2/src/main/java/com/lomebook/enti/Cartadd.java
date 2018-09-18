package com.lomebook.enti;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;

public class Cartadd {
    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }
    @NotEmpty
    private int bookid;

    @NotEmpty
    @NumberFormat
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
