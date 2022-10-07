package com.littlepaydemo.tripcalculator.util.converters;

import com.littlepaydemo.tripcalculator.util.enums.TapType;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class TapTypeConverter extends AbstractBeanField<String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if(s != null && !s.isEmpty()){
            switch (s.trim().toUpperCase()){
                case "ON":
                    return TapType.ON;
                case "OFF":
                    return TapType.OFF;
            }

        }
        return null;
    }
}
