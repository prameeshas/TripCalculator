package com.littlepaydemo.tripcalculator.services;

import com.littlepaydemo.tripcalculator.configuration.ApplicationConfiguration;
import com.littlepaydemo.tripcalculator.model.Tap;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
@Slf4j
public class OpenCsvFileServiceImpl implements CsvFileService {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    @Override
    public List<Tap> readFile(String filePath) throws IOException {
        log.info(String.format("Reading input file at %s", filePath));

        CSVParser csvParser = new CSVParserBuilder().withSeparator(applicationConfiguration.getCsvSeparator()).build();
        List<Tap> taps = new CsvToBeanBuilder(new FileReader(filePath))
                .withType(Tap.class)
                .withSkipLines(1)
                .build()
                .parse();
        return taps;
    }

    @Override
    public boolean writeFile(String filePath, List entry) throws Exception {

        PrintWriter writer = new PrintWriter(filePath);
        String header = "started, finished, durationInSecs, FromStopID, ToStopID, ChangeAmount, CompanyID, BusID, PAN";
        writer.println(header);

        for (Object sample : entry) {
            writer.println(sample.toString());
        }
        writer.close();

        return false;
    }

}

