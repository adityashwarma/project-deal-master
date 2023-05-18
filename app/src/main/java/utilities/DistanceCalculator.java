package utilities;

import android.location.Location;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import models.Deal;

public class DistanceCalculator {


    static double calc_dist(double dest_x, double dest_y, double org_x, double org_y) {

        dest_y = Math.toRadians(dest_y);
        org_y = Math.toRadians(org_y);
        dest_x = Math.toRadians(dest_x);
        org_x = Math.toRadians(org_x);

        // Haversine formula
        double dlon = dest_y - org_y;
        double dlat = dest_x - org_x;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(org_x) * Math.cos(dest_x)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        double d= (c * r);



        if (d < 1) {
            double scale = Math.pow(10, 3);
            d = Math.round(d * scale) / scale;
        } else {
            double scale = Math.pow(10, 1);
            d = Math.round(d * scale) / scale;
        }
        return d;

    }


    public static String distance(Deal deal, Location userLocation) {
        if (userLocation == null) {
            return "0";
        }
        double dest_x = deal.getLatitude();
        double dest_y = deal.getLongitude();
        double dist = calc_dist(dest_x, dest_y, userLocation.getLatitude(), userLocation.getLongitude());

        String k = "";
        if (dist < 1) {
            dist = dist * 1000;

            k = dist + "m";
        } else {
            k = dist + "Km";
        }
        return k;
    }

}
