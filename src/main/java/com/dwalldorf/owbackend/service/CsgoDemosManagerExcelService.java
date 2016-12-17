package com.dwalldorf.owbackend.service;

import static com.dwalldorf.owbackend.Application.appInfoMarker;

import com.dwalldorf.owbackend.annotation.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CsgoDemosManagerExcelService {

    @Log
    private Logger logger;

    public Integer intFromCell(final Row row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return intFromCell(row.getCell(cellIndex));
    }

    private Integer intFromCell(final Cell cell) {
        return ((Double) cell.getNumericCellValue()).intValue();
    }

    public Long longFromCell(Row row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return longFromCell(row.getCell(cellIndex));
    }

    private long longFromCell(final Cell cell) {
        return ((Double) cell.getNumericCellValue()).longValue();
    }

    public Double doubleFromCell(final Row row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return doubleFromCell(row.getCell(cellIndex));
    }

    private double doubleFromCell(final Cell cell) {
        return cell.getNumericCellValue();
    }

    public Boolean boolFromIntCell(final Row row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return boolFromIntCell(row.getCell(cellIndex));
    }

    private boolean boolFromIntCell(final Cell cell) {
        return intFromCell(cell) == 1;
    }

    public String stringFromCell(final Row row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return stringFromCell(row.getCell(cellIndex));
    }

    private String stringFromCell(final Cell cell) {
        return cell.getStringCellValue();
    }

    public Date dateFromCell(XSSFRow row, int cellIndex) {
        if (!hasCell(row, cellIndex)) {
            return null;
        }

        return dateFromCell(row.getCell(cellIndex));
    }

    private Date dateFromCell(final Cell cell) {
        Date date = null;
        String dateString = "";
        try {
            dateString = stringFromCell(cell);
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        } catch (ParseException e) {
            logger.warn(appInfoMarker, "Could not parse '{}' to a Date", dateString);
        }

        return date;
    }

    private boolean hasCell(final Row row, int cellIndex) {
        short lastCellNum = row.getLastCellNum();
        if (lastCellNum < cellIndex) {
            logger.warn(appInfoMarker, "Wanted cell with index {} but row only has {} columns", cellIndex, lastCellNum);
            return false;
        }

        return true;
    }
}
