package com.librarytest.librarytest.Models;

import java.security.PublicKey;

public class User {

    private Long id_user;
    private String email;
    private String name;

    public User(Long id_user,String name,String email){
        this.id_user=id_user;
        this.email=email;
        this.name=name;
    }
    public User(String email){
        this.email=email;
    }
    public User(Long id_user,String email){
        this.email=email;
        this.id_user=id_user;
    }


    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
