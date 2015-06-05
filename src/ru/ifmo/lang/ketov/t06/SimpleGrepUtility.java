package ru.ifmo.lang.ketov.t06;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleGrepUtility implements Grep {

    public static void main(String[] args) {
        String regexp = "";
        String path = "";
        List<String> list;

        if (args.length < 2 || args.length > 3) {
            System.out.println("Некорректный набор аргументов.");
            return;
        }

        if (args.length == 2) {
            regexp = args[0];
            path = args[1];
        }

        if (args.length == 3) {
            regexp = args[1];
            path = args[2];
        }

        try {
            InputStream is = new FileInputStream(path);
            SimpleGrepUtility grep = new SimpleGrepUtility(is);

            if (args.length == 2) {
                list = grep.findLines(regexp);
            } else {
                if (args[0].equals("-o")) {
                    list = grep.findParts(regexp);
                } else if (args[0].equals("-v")) {
                    list = grep.findInvertMatch(regexp);
                } else {
                    System.out.println("Некорректный аргумент: " + args[0]);
                    return;
                }
            }


            for (String s : list) {
                System.out.println(s);
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }


    enum SearchMode {Lines, Parts, Invert}

    InputStream is;

    public SimpleGrepUtility(InputStream is) {
        this.is = is;
    }

    @Override
    public List<String> findLines(String regex) {
        return find(regex, SearchMode.Lines);
    }

    @Override
    public List<String> findParts(String regex) {
        return find(regex, SearchMode.Parts);
    }

    @Override
    public List<String> findInvertMatch(String regex) {
        return find(regex, SearchMode.Invert);
    }

    private ArrayList<String> find(String search, SearchMode mode) {

        Pattern regexp = Pattern.compile("(" + search + ")");
        Matcher m;
        ArrayList<String> list = new ArrayList<String>();

        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String strLine;

            while ((strLine = br.readLine()) != null) {
                m = regexp.matcher(strLine);

                switch (mode) {
                    case Lines:
                        if (m.find()) {
                            list.add(strLine);
                        }
                        break;

                    case Parts:
                        while (m.find()) {
                            list.add(m.group());
                        }
                        break;

                    case Invert:
                        if (!m.find()) {
                            list.add(strLine);
                        }
                        break;
                }
            }

            isr.close();
            br.close();

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}
