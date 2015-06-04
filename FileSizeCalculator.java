package ru.ifmo.lang.ketov.t04;


public interface FileSizeCalculator {
    /**
     * Подсчитывает суммарный размер удовлетворяющих шаблону файлов, расположенных в указаной папке
     * @param pathToDir корневая папка, в которой осуществлять поиск файлов
     * @param fileTemplate шаблон имени файла
     * @return размер в байтах
     */
    long getSize(final String pathToDir, final String fileTemplate);
}

