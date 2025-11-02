package com.librarytest.librarytest;

import com.librarytest.librarytest.ConfigTests.ResultTestWatchers;
import com.librarytest.librarytest.Services.ClockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ResultTestWatchers.class)
public class ClockServiceTest {
    private ClockService clockServiceStub;
    @BeforeEach
    public void setUp(){
        clockServiceStub=new ClockService();
    }

    @Test
    public void shouldReturnCurrentTime(){
        assertEquals(LocalDateTime.now().getDayOfMonth(),clockServiceStub.currentTime().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonth(),clockServiceStub.currentTime().getMonth());
        assertEquals(LocalDateTime.now().getYear(),clockServiceStub.currentTime().getYear());
        assertEquals(LocalDateTime.now().getHour(),clockServiceStub.currentTime().getHour());
        assertEquals(LocalDateTime.now().getMinute(),clockServiceStub.currentTime().getMinute());
    }

}
