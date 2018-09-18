package com.lomebook.enti;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Cartcount {

    private int id;

    @Min(value = 1)
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
