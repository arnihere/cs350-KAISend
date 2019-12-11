package com.example.cs350_kaisend;

import org.junit.Test;

import static org.junit.Assert.*;

public class MainActivityTest {
    MainActivity mainActivity = new MainActivity();
    @Test
    public void isAdmin() {
        assertFalse(mainActivity.isAdmin("Admin@kaist.ac.kr"));
    }
}