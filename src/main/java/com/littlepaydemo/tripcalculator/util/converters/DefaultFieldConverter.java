package com.littlepaydemo.tripcalculator.util.converters;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class DefaultFieldConverter extends AbstractBeanField<String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
       return s!= null ? s.trim() : null;
    }
}
