package com.example.cs350_kaisend;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityRegisterTest {
    ActivityRegister activityRegister = new ActivityRegister();
    @Test
    public void notCompleteKaistMail() {
        String input = "team5@kaist.ac.k";
        assertFalse(activityRegister.isValidEmail(input));
    }
    @Test
    public void kaistMail() {
        String input = "team5@kaist.ac.kr";
        assertTrue(activityRegister.isValidEmail(input));
    }
    @Test
    public void gmail() {
        String input = "team5@gmail.com";
        assertFalse(activityRegister.isValidEmail(input));
    }

    @Test
    public void differentNums() {
        String pwd1 = "qwe123";
        String pwd2 = "qwe122";
        assertFalse(activityRegister.passwordMatch(pwd1,pwd2));
    }
    @Test
    public void caseSensitivity() {
        String pwd1 = "qwe123";
        String pwd2 = "qwE123";
        assertFalse(activityRegister.passwordMatch(pwd1,pwd2));
    }
    @Test
    public void nullAndSpace() {
        String pwd1 = "";
        String pwd2 = " ";
        assertFalse(activityRegister.passwordMatch(pwd1,pwd2));
    }
}