package test.utility;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcel {

    private String filePath ;
    private int columnLength;

    public ReadExcel(String path){
        this.filePath = path ;
    }
    public List<Map> read(String sheetName) {
        try {
            File excelFile = new File(filePath);
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workBook.getSheet(sheetName);
            Iterator<Row> rowIt = sheet.iterator();
            boolean firstRow = true ;
            String[] columnNames = new String[50] ;
            List<Map> records = new ArrayList<Map>() ;
            int rowTotal = sheet.getLastRowNum() + 1 ;
          //  System.out.println("rowTotal "+rowTotal);
            int currentRow = 1 ;

            while(rowIt.hasNext() && currentRow <= rowTotal ) {
                boolean isEmptyCell = true ;
                currentRow++ ;
                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                int index = 0;
                Map record = new HashMap();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(CellType.STRING);

                    if(!cell.toString().isEmpty())
                            isEmptyCell = false;
                    if(firstRow){
                        if(cell.toString().isEmpty())
                        {
                            this.columnLength=index;
                            break;
                        }
                        columnNames[index++]=cell.toString() ;
                        this.columnLength=index;
                    } else {
                        if(index < this.columnLength) {
                            record.put(columnNames[cell.getColumnIndex()],cell.getStringCellValue().trim() );
                            index++;
                        }
                        else
                            break;
                    }
                }
                if(firstRow)
                    firstRow = false ;
                else {
                    if(!isEmptyCell)
                        records.add(record);
                }
            }
            workBook.close();
            fis.close();
            return records ;

        } catch (Exception ex){
            ex.printStackTrace();
            return null ;

        }

    }

}
