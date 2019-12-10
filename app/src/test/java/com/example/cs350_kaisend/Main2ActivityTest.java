package com.example.cs350_kaisend;

import org.junit.Test;

import static org.junit.Assert.*;

public class Main2ActivityTest {
    Main2Activity main2Activity = new Main2Activity();
    @Test
    public void checkEmptyString() {
        assertFalse(main2Activity.isNull(""));
    }
    @Test
    public void checkNull(){
        assertTrue(main2Activity.isNull(null));
    }
}