package com.littlepaydemo.tripcalculator.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CsvFileService<T, M> {
    List<M> readFile(String filePath) throws IOException;

    boolean writeFile(String filePath, List<T> entry) throws Exception;
}
