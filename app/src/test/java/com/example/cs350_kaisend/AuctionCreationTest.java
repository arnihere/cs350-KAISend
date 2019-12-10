package com.example.cs350_kaisend;

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class})
public class AuctionCreationTest {
    @Before
    public void setup() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                String b = a.toString().trim();
                return !(a != null && a.length() > 0 && b.length() > 0);
            }
        });
        PowerMockito.when(TextUtils.isDigitsOnly(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence a = (CharSequence) invocation.getArguments()[0];
                try{
                    int temp = Integer.parseInt(a.toString().trim());
                }catch (NumberFormatException e){
                    return false;
                }return true;
            }
        });
    }
    @Test
    public void emptyString() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid("a","a","","a","12","12");
        assertFalse(output);
    }
    @Test
    public void nonparseableString() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid("ss","ss","ss","a","2","a");
        assertFalse(output);
    }@Test
    public void nullValue() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid("1","1","1","1","2",null);
        assertFalse(output);
    }@Test
    public void mixedValuesString() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid("ss","ss","a","a","100","3 won");
        assertFalse(output);
    }@Test
    public void spacedEmptyStrings() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid(" "," "," "," ","100","3");
        assertFalse(output);
    }@Test
    public void validStringsWithSomeSpaces() {
        AuctionCreation auctionCreation = new AuctionCreation();
        boolean output = auctionCreation.isValid("s s","s     s","a   ","  a "," 100","3  ");
        assertTrue(output);
    }
}