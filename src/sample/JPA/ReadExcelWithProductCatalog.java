package sample.JPA;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ReadExcelWithProductCatalog {
    public static List<ProductCatalog> readFileUsingPOI(File file) throws IOException {
        List<ProductCatalog> products = new ArrayList<>();

        FileInputStream inputStream = new FileInputStream(file);

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        Iterator iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = (Row) iterator.next();
            if (nextRow.getRowNum() == 0)
                continue;

            ProductCatalog productCatalog = new ProductCatalog();
            Iterator cellIterator = nextRow.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = (Cell) cellIterator.next();
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex + 1) {
                    case 1:
                        productCatalog.setCatalogNo(cell.getStringCellValue());
                        break;
                    case 2:
                        productCatalog.setSymbol(cell.getStringCellValue());
                        break;
                    case 3:
                        productCatalog.setPriceNet(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case 4:
                        Integer stock = (int) cell.getNumericCellValue();
                        if (stock != null) {
                            productCatalog.setStock(stock);
                        } else {
                            productCatalog.setStock(0);
                        }
                        break;
                    case 5:
                        productCatalog.setGroupId((int) cell.getNumericCellValue());
                        break;
                }
            }
            if (productCatalog.getCatalogNo().isEmpty() && productCatalog.getSymbol().isEmpty() && productCatalog.getGroupId() == 0){
                break;
            }
            products.add(productCatalog);

        }
        workbook.close();
        inputStream.close();

        return products;
    }

}
