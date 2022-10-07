package com.littlepaydemo.tripcalculator.util.converters;

import com.littlepaydemo.tripcalculator.util.enums.Stop;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

public class StopTypeConverter extends AbstractBeanField<String> {

    @Override
    protected Object convert(String s) throws CsvDataTypeMismatchException, CsvConstraintViolationException {

        if(s != null && !s.isEmpty()){
            switch (s.trim().toUpperCase()){
                case "STOP1":
                    return Stop.STOP1;
                case "STOP2":
                    return Stop.STOP2;
                case "STOP3":
                    return Stop.STOP3;

            }

        }
        return null;
    }
}
