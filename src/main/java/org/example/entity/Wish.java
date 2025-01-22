package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wish {
    //связывается с вишлистом по wishlistId
    @JsonProperty("wish_name")
    private String wish_name;
    @JsonProperty("wish_description")
    private String wish_description;
    @JsonProperty("wish_link")
    private String wish_link;
    @JsonProperty("priority")
    private WishPriority priority;

    public Wish(String wishName) {
        this.wish_name = wishName;
    }

    public Wish() {}

    public String getWish_name() {
        return wish_name;
    }

    public String getWish_description() {
        return wish_description;
    }

    public String getWish_link() {
        return wish_link;
    }

    public void setWish_name(String wish_name) {
        this.wish_name = wish_name;
    }

    public void setWish_description(String wish_description) {
        this.wish_description = wish_description;
    }

    public void setWish_link(String wish_link) {
        this.wish_link = wish_link;
    }

    public void setPriority(WishPriority priority) {
        this.priority = priority;
    }

    public WishPriority getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wish wish = (Wish) o;
        return Objects.equals(wish_name, wish.wish_name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wish_name);
    }

    @Override
    public String toString() {
        return "Wish{" +
                "wish_name='" + wish_name + '\'' +
                ", wish_description='" + wish_description + '\'' +
                ", wish_link='" + wish_link + '\'' +
                ", priority=" + priority +
                '}';
    }

    //что будет у виша?
    //добавить описание, добавить ссылку на виш, добавить приоритетность
    //редактировать виш - редактировать имя, описание, ссылку, приоритетность
}
