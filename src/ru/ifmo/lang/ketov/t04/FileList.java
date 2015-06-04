package ru.ifmo.lang.ketov.t04;

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;

class FileList extends SimpleFileVisitor<Path> {

    private PathMatcher matcher;
    private long sizeSum;

    public long getSize() {
        return sizeSum;
    }

    public FileList(String pattern) {
        sizeSum = 0;
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attributes) {
        if (matcher.matches(path.getFileName())) {
            long size = path.toFile().length();
            sizeSum += size;
            System.out.println(path.toString() + ": " + SizeFormatter.toString(size));
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path path, IOException exc) throws IOException {
        System.out.println(path.toString() + " not readable");
        return FileVisitResult.CONTINUE;
    }
}
