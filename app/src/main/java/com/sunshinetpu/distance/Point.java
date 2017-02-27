package com.sunshinetpu.distance;

/**
 * Created by sunshine on 2/24/17.
 */

public class Point {
    private final double latitude, longitude;
    public Point(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /*
    Calculate distance between 2 points by Vincenty's formula
     */
    public static double getDistanceBetween2Points(Point firstPoint, Point secondPoint){
        double a = 6378137;
        double b = 6356752.314245;
        double f = 1 - b/a;
        double lat1 = Math.toRadians(firstPoint.latitude);
        double lat2 = Math.toRadians(secondPoint.latitude);
        double u1 = Math.atan((1-f) * Math.tan(lat1));
        double u2 = Math.atan((1-f) * Math.tan(lat2));
        double L = Math.toRadians(secondPoint.longitude - firstPoint.longitude);
        double lamda = L;
        double sinU1 = Math.sin(u1);
        double sinU2 = Math.sin(u2);
        double cosU1 = Math.cos(u1);
        double cosU2 = Math.cos(u2);
        double lamda2;
        int iterationLimit = 100;
        double cosAlphaPower2;
        double sinNguy;
        double cosNguy;
        double cos2Nguy;
        double nguy;
        do{
            double sinLamda = Math.sin(lamda);
            double cosLamda = Math.cos(lamda);
            sinNguy = Math.sqrt(Math.pow(cosU2 * sinLamda,2) + Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * cosLamda, 2));
            cosNguy = sinU1 * sinU2 + cosU1 * cosU2 * cosLamda;
            nguy = Math.atan2(sinNguy,cosNguy);

            double sinAlpha = (cosU1 * cosU2 * sinLamda)/sinNguy;
            cosAlphaPower2 = 1 - Math.pow(sinAlpha,2);
            cos2Nguy = cosNguy - (2 * sinU1 * sinU2)/cosAlphaPower2;

            double C = f * cosAlphaPower2 * (4 + f* (4 - 3 * cosAlphaPower2)) / 16;
            lamda2 = lamda;
            lamda = L + (1 - C) * f * sinAlpha * (nguy + C * sinNguy * (cos2Nguy + C * cosNguy *(-1 + 2 * Math.pow(cos2Nguy,2))));
        }while(Math.abs(lamda - lamda2)> 1e-12 && --iterationLimit > 0 );

        if(iterationLimit == 0){
            return -1;
        }
        double uPower2 = cosAlphaPower2 * (a * a - b * b)/ (b * b);
        double A = 1 + uPower2 * (4096 + uPower2 * (-768 + uPower2 * (320 - 175 * uPower2)))/ 16384;
        double B = uPower2 * (256 + uPower2 * (-128 + uPower2 * (74 - 47 * uPower2)))/1024;
        double deltaNguy = B * sinNguy * (cos2Nguy + B * (cosNguy * (-1 + 2 * Math.pow(cos2Nguy,2)) - B * cos2Nguy *
                (-3 + 4 * Math.pow(sinNguy,2)) * (-3 + 4 * Math.pow(cos2Nguy,2))/6) / 4);

        return b * A * (nguy - deltaNguy);
    }

}
