package ru.ifmo.lang.ketov.t01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class OddEvenFileSplitter implements FileSplitter {


    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Недостаточно аргументов.");
            return;
        }

        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);

        OddEvenFileSplitter fileSplitter = new OddEvenFileSplitter();
        OddEvenFileSplitter.Config config = new OddEvenFileSplitter.Config();
        config.setSourceFilePath(args[0]);
        config.setOddLinesFilePath(args[1]);
        config.setEvenLinesFilePath(args[2]);

        fileSplitter.splitFile(config);
    }

    public void splitFile(SplitConfig config) {

        File file = new File(config.getSourceFilePath());

        try {
            int j = 0;
            Scanner scanner = new Scanner(file);
            PrintWriter odd = new PrintWriter(config.getOddLinesFilePath());
            PrintWriter even = new PrintWriter(config.getEvenLinesFilePath());


            while (scanner.hasNextLine()) {
                j++;
                String line = scanner.nextLine();

                if (j % 2 == 1) {
                    odd.println(line); // 1
                } else {
                    even.println(line); // 2
                }
            }
            scanner.close();
            odd.close();
            even.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден.");
        }
    }

    static public class Config implements SplitConfig {

        String input = "";
        String outputOdd = "";
        String outputEven = "";

        public String getSourceFilePath() {
            return input;
        }

        public String getOddLinesFilePath() {
            return outputOdd;
        }

        public String getEvenLinesFilePath() {
            return outputEven;
        }

        public void setSourceFilePath(String file) {
            input = file;
        }

        public void setOddLinesFilePath(String file) {
            outputOdd = file;
        }

        public void setEvenLinesFilePath(String file) {
            outputEven = file;
        }
    }
}



