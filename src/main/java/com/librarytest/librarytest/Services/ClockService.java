package com.librarytest.librarytest.Services;

import java.time.LocalDateTime;

public class ClockService {

    public LocalDateTime currentTime(){
        return LocalDateTime.now();
    }
}
