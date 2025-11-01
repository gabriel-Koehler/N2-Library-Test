package com.librarytest.librarytest.Services;

import java.time.LocalDateTime;

public class ClockService {

    public LocalDateTime currentTime(){
        return LocalDateTime.of(1999,1,1,0,0);
    }
}
