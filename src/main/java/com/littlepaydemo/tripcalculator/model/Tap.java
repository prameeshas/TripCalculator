package com.littlepaydemo.tripcalculator.model;

import com.littlepaydemo.tripcalculator.util.enums.Stop;
import com.littlepaydemo.tripcalculator.util.enums.TapType;
import com.littlepaydemo.tripcalculator.util.converters.DateFieldConverter;
import com.littlepaydemo.tripcalculator.util.converters.DefaultFieldConverter;
import com.littlepaydemo.tripcalculator.util.converters.StopTypeConverter;
import com.littlepaydemo.tripcalculator.util.converters.TapTypeConverter;
import com.opencsv.bean.CsvCustomBindByPosition;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@ToString
public class Tap implements Comparable<Tap> {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @CsvCustomBindByPosition(position = 0, converter = DefaultFieldConverter.class)
    private String id;
    @CsvCustomBindByPosition(position = 1, converter = DateFieldConverter.class)
    private LocalDateTime dateTimeUtc;
    @CsvCustomBindByPosition(position = 2, converter = TapTypeConverter.class)
    private TapType tapType;
    @CsvCustomBindByPosition(position = 3, converter = StopTypeConverter.class)
    private Stop stopId;
    @CsvCustomBindByPosition(position = 4, converter = DefaultFieldConverter.class)
    private String companyId;
    @CsvCustomBindByPosition(position = 5, converter = DefaultFieldConverter.class )
    private String busId;
    @CsvCustomBindByPosition(position = 6, converter = DefaultFieldConverter.class)
    private String pan;

    public Tap() {
    }

    @Override
    public int compareTo(Tap o) {

        return this.dateTimeUtc.isBefore(o.dateTimeUtc) ? -1 : 1;
    }


}
