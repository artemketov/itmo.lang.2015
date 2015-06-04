package ru.ifmo.lang.ketov.t04;

import java.text.DecimalFormat;

public class SizeFormatter {
    public static String toString(long size) {
        double outSize = size;
        String[] units = {"Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes"};
        DecimalFormat formatter = new DecimalFormat("#0.00");

        for (String unit : units) {
            if (outSize > 1024) {
                outSize /= 1024;
            } else {
                return formatter.format(outSize) + " " + unit;
            }
        }
        return formatter.format(outSize);
    }
}
