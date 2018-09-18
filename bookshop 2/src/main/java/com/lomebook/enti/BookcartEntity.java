package com.lomebook.enti;

public class BookcartEntity {
    private BooksEntity booksEntity;
    private CartEntity cartEntity;

    public BooksEntity getBooksEntity() {
        return booksEntity;
    }

    public void setBooksEntity(BooksEntity booksEntity) {
        this.booksEntity = booksEntity;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
}
