package com.librarytest.librarytest.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.librarytest.librarytest.Models.User;

public class InMemoryUserRepository {
    private final Map<Long,User> users = new HashMap<>();

    public User findById(Long id){
        return Optional.ofNullable(users.get(id)).get();
    }

    public InMemoryUserRepository(){
        users.put(1L,new User(1L,"user1","user1@gmail"));
        users.put(2L,new User(2L,"user2","user2@gmail"));
        users.put(3L,new User(3L,"user3","user3@gmail"));
        users.put(4L,new User(4L,"user4","user4@gmail"));
        users.put(5L,new User(5L,"user5","user5@gmail"));
        users.put(6L,new User(6L,"user6","user6@gmail"));
        users.put(7L,new User(7L,"user7","user7@gmail"));
        users.put(8L,new User(8L,"user8","user8@gmail"));
        users.put(9L,new User(9L,"user9","user9@gmail"));
        users.put(10L,new User(10L,"user10","user10@gmail"));
    }
}
