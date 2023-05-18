package com.example.dealrunner;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.location.Location;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import models.Deal;
import utilities.DistanceCalculator;

@RunWith(MockitoJUnitRunner.class)
public class DistanceCalTest {

    @Mock
    Location l;

    @Before
    public void setup(){
        Mockito.when(l.getLatitude()).thenReturn(43.471627);
        Mockito.when(l.getLongitude()).thenReturn(-80.540657);
    }

    @Test
    public void testDistance(){
        Deal deal = new Deal();
        deal.setLatitude(37.2932417);
        deal.setLongitude(-121.9303883);
        assertEquals("3534.8Km", DistanceCalculator.distance(deal, l));
    }

    @Test
    public void testDistanceM(){
        Deal deal = new Deal();
        deal.setLatitude(43.473579);
        deal.setLongitude(-80.544920);
        assertEquals("407.0m", DistanceCalculator.distance(deal, l));
    }


}
