package ru.ifmo.lang.ketov.t09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class LineProcessor {

    private final String output;
    private List<String> lines;

    public LineProcessor(final String input, final String output) throws IOException {

        this.output = output;
        this.lines = Files.readAllLines(Paths.get(input));
    }

    public void sort() {
        lines = lines.stream().sorted().collect(Collectors.toList());
    }

    public void skip(int del) {
        lines = lines.stream().skip(del).collect(Collectors.toList());
    }

    public void limit(int save) {
        lines = lines.stream().limit(save).collect(Collectors.toList());
    }

    public void shuffle() {
        Collections.shuffle(lines);
    }

    public void distinct() {
        lines = lines.stream().distinct().collect(Collectors.toList());
    }

    public void filter(String regex) {
        lines = lines.stream().filter(i -> Pattern.compile(regex).matcher(i).matches()).collect(Collectors.toList());
    }

    public void write() throws IOException {
        Files.write(Paths.get(output), lines);
    }


    public static void main(String[] args) throws IOException {

        String input = args[0];
        String output = args[1];
        LineProcessor lp = new LineProcessor(input, output);

        String s = "";
        for (int i = 2; i <= args.length; i++) {
            args[i].equals(s);
            switch (s) {
                case ("sort"):
                    lp.sort();
                case ("skip"):
                    lp.skip(Integer.parseInt(args[i + 1]));
                case ("limit"):
                    lp.limit(Integer.parseInt(args[i + 1]));
                case ("shuffle"):
                    lp.shuffle();
                case ("distinct"):
                    lp.distinct();
                case ("filter"):
                    lp.filter(args[i++]);
            }
        }
        lp.write();
    }
}
