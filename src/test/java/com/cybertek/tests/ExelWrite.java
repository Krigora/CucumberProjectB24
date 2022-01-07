package com.cybertek.tests;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExelWrite {
    String filePath = "Employees.xlsx";

    @Test
    public void writeToExcelSheet() throws IOException {

        //open the Employees.xlsx using Apache POI
        FileInputStream in = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(in);

        //Goto "data" sheet, or goto first sheet by index
        XSSFSheet dataSheet = workbook.getSheetAt(0);

        // add a column name "salary" and save into file
        XSSFCell salaryColCell = dataSheet.getRow(0).createCell(3);
        salaryColCell.setCellValue("Salary");

        XSSFCell salary1 = dataSheet.getRow(1).createCell(3);
        salary1.setCellValue(111000);

        XSSFCell salary2 = dataSheet.getRow(2).createCell(3);
        salary2.setCellValue(118000);

        XSSFCell salary3 = dataSheet.getRow(3).createCell(3);
        salary3.setCellValue(148000);

        // save the changes:
        // open to write to the file: FileInputStream -> reading | FileOutputStream -> writing

        FileOutputStream outputStream = new FileOutputStream(filePath);
        // save/write changes to the workbook
        workbook.write(outputStream);
        outputStream.close();
        workbook.close();
        in.close();

    }
}
