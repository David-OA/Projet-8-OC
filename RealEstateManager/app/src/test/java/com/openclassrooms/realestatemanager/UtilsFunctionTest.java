package com.openclassrooms.realestatemanager;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static com.openclassrooms.realestatemanager.utils.Utils.convertDollarToEuro;
import static com.openclassrooms.realestatemanager.utils.Utils.convertEuroToDollar;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(RobolectricTestRunner.class)
public class UtilsFunctionTest {

    private ConnectivityManager connectivityManager;
    private ShadowNetworkInfo shadowOfActiveNetworkInfo;
    private Context context;

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

    @Test
    public void getTodayDate_correct(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = dateFormat.format(new Date());

        assertEquals(todayDate, Utils.getTodayDate());
    }

    @Test
    public void checkIsInternetAvailableFalse_NoInternet() throws Exception{
        this.setUpContextInternet();
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assertFalse(Utils.isInternetAvailable(context));
    }

    @Test
    public void checkIsInternetAvailableTrue_InternetOn() throws Exception{
        this.setUpContextInternet();
        shadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assertTrue(Utils.isInternetAvailable(context));
    }

    public void setUpContextInternet(){
        context = getApplicationContext();
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        shadowOfActiveNetworkInfo = shadowOf(connectivityManager.getActiveNetworkInfo());
    }
}