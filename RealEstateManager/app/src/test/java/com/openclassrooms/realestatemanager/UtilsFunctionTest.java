package com.openclassrooms.realestatemanager;

import static com.openclassrooms.realestatemanager.utils.Utils.convertDollarToEuro;
import static com.openclassrooms.realestatemanager.utils.Utils.convertEuroToDollar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsFunctionTest {

    @Test
    public void convertDollarsToEurosIsCorrect() throws Exception {
        int dollars = 1000;
        assertEquals(812,convertDollarToEuro(dollars));
    }

    @Test
    public void convertEurosToDollarsIsCorrect() throws Exception {
        int euros = 812;
        assertEquals(1000, convertEuroToDollar(euros));
    }
}