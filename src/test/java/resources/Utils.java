package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class Utils {
    public static RequestSpecification req;

    public RequestSpecification
    requestSpecification() throws IOException {

        if (req == null) {
            PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
            req = new RequestSpecBuilder().setBaseUri(getGlobalValues("baseUrl"))
                    .addQueryParam("key", "qaclick123").
                            addFilter(RequestLoggingFilter.logRequestTo(log)).
                            addFilter(ResponseLoggingFilter.logResponseTo(log)).
                            setContentType(ContentType.JSON).build();
            return req;
        }
        return req;
    }

    public String getGlobalValues(String key) throws IOException {
        Properties prop = new Properties();

        FileInputStream fs = new FileInputStream("src" +
                "\\test\\java\\resources\\global.properties");

        prop.load(fs);
        return prop.getProperty(key);
    }

    public String getJSONPath(Response response, String path){
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(path).toString();
    }

    public ArrayList<String> getDataFromExcelSheet(String sheetName,String columnName, String rowName)
            throws IOException {
        ArrayList<String> a = new ArrayList<>();
        FileInputStream fis = new FileInputStream(getGlobalValues("XLSXFileLocation"));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int no_of_sheets = workbook.getNumberOfSheets();
        for (int i=0; i<no_of_sheets;i++){

            if(workbook.getSheetName(i).equalsIgnoreCase(sheetName)){
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> row = sheet.iterator();
                Row firstRow = row.next();
                Iterator<Cell> ce = firstRow.cellIterator();
                int k=0;
                int column =0;

                while (ce.hasNext()){
                    Cell firstRowCell = ce.next();
                    if (firstRowCell.getStringCellValue().equalsIgnoreCase(columnName)){
                        column=k;
                    }
                    k++;
                }
                while (row.hasNext()){
                    Row r = row.next();
                    if(r.getCell(column).getStringCellValue().equalsIgnoreCase(rowName)){
                        Iterator<Cell> c = r.cellIterator();
                        while (c.hasNext()){
                            Cell ce_val = c.next();
                            if(ce_val.getCellType()== CellType.STRING){
                                a.add(ce_val.getStringCellValue());
                            }else {
                                a.add(NumberToTextConverter.toText(ce_val.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }
        return a;
    }
}
