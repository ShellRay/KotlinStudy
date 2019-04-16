package com.kotlin.study.greendaogenlib;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "ACCOUNT".
 */
@Entity
public class Account {

    @Id
    private String id;

    @NotNull
    private String name;
    private String avatarlink;

    @Generated
    public Account() {
    }

    public Account(String id) {
        this.id = id;
    }

    @Generated
    public Account(String id, String name, String avatarlink) {
        this.id = id;
        this.name = name;
        this.avatarlink = avatarlink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    public String getName() {
        return name;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getAvatarlink() {
        return avatarlink;
    }

    public void setAvatarlink(String avatarlink) {
        this.avatarlink = avatarlink;
    }

}