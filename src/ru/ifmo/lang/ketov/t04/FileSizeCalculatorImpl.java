package ru.ifmo.lang.ketov.t04;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileSizeCalculatorImpl implements FileSizeCalculator {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Недостаточно аргументов.");
            System.exit(1);
        }

        FileSizeCalculatorImpl f = new FileSizeCalculatorImpl();
        long size = f.getSize(args[0], args[1]);

        System.out.println("\nTotal: " + SizeFormatter.toString(size));
    }

    public long getSize(final String pathToDir, final String fileTemplate) {
        Path startPath = Paths.get(pathToDir);

        if (!Files.exists(startPath)) {
            System.out.println(pathToDir + " not found");
            System.exit(2);
        }

        try {
            FileList filelist = new FileList(fileTemplate);

            Files.walkFileTree(startPath, filelist);

            return filelist.getSize();

        } catch (IllegalArgumentException iae) {
            System.err.println("Invalid pattern; did you forget to prefix \"glob:\" or \"regex:\"?");
            System.exit(3);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(4);
        }

        return 0;
    }
}
