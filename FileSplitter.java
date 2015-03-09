
public interface FileSplitter {
    /**
     * Разбивает файл на два отдельных
     * @param config настройки расположения файлов
     */
    void splitFile(SplitConfig config);

    /**
     * Настройки для разбивания файла на части
     */
    interface SplitConfig {

        /**
         * @return путь до файла с входными данными
         */
        String getSourceFilePath();

        /**
         * @return путь до результирующего файла с нечетными строками
         * (файл будет создан в процессе работы)
         */
        String getOddLinesFilePath();

        /**
         * @return путь до результирующего файла с четными строками
         * (файл будет создан в процессе работы)
         */
        String getEvenLinesFilePath();
    }
}
