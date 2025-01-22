package org.example.entity;

import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    //связть с таблицей Users через id (сделано)
    //сделать возможность создания нескольких вишлистов (на др, нг, годовщину). для этого использовать wishListName (сделано)
    private String wishlistName;
    private List<Wish> wishlist;

    public Wishlist(String wishlistName) {
        this.wishlistName = wishlistName;
        this.wishlist = new ArrayList<>();
    }

    //что будет у вишлиста?
    //создать вишлист, посмотреть вишлисты, удалить вишлист (сделано) добавить виш в вишлист, удалить виш из вишлиста
}
