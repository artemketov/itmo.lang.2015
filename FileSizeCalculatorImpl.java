package ru.ifmo.lang.ketov.t04;

import java.io.File;
import java.text.DecimalFormat;


public class FileSizeCalculatorImpl implements FileSizeCalculator {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        FileSizeCalculatorImpl f = new FileSizeCalculatorImpl();
        long size = f.getSize(args[0], args[1]);

        System.out.println("\nTotal: " + f.getFormattedSizeString(size));
    }

    public long getSize(final String pathToDir, final String fileTemplate) {
        File[] list;
        File root = new File(pathToDir);
        long sizeSum = 0;

        if (!root.exists()) {
            System.out.println(pathToDir + " not found");
            return 0;
        }

        try {
            list = root.listFiles();

            for (File currentFile : list) {
                // is directory -> recursive search
                if (currentFile.isDirectory()) {
                    sizeSum += getSize(currentFile.getAbsolutePath(), fileTemplate);
                } else {
                    // filter match
                    if (isFilterMatch(currentFile.getName(), fileTemplate)) {
                        sizeSum += currentFile.length();
                        // log founded file
                        System.out.println(currentFile + ": " + getFormattedSizeString(currentFile.length()));
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // log sum of every directory
        if (sizeSum > 0) {
            System.out.println(pathToDir + ": " + getFormattedSizeString(sizeSum));
        }

        return sizeSum;
    }

    // match name with filter
    public boolean isFilterMatch(String name, String filter) {
        if (filter.charAt(0) == '*') { // first char
            String filterEnd = filter.substring(1);
            return name.endsWith(filterEnd);
        } else if (filter.charAt(filter.length() - 1) == '*') { // last char
            String filterStart = filter.substring(0, filter.length() - 1);
            return name.startsWith(filterStart);
        } else { // not a mask
            return name.equals(filter);
        }
    }

    // create formatted string
    public String getFormattedSizeString(long size) {
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
