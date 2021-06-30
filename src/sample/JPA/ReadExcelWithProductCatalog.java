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

        try {


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
                        case 6:
                            Double auktis =cell.getNumericCellValue();
                            if (auktis == null){productCatalog.setAukstis(0);}
                            else {productCatalog.setAukstis(auktis);}
                            break;
                        case 7:
                            Double plotis = cell.getNumericCellValue();
                            if (plotis == null){productCatalog.setPlotis(0);}
                            else {productCatalog.setPlotis(plotis);}
                            break;
                        case 8:
                            Double gylis = cell.getNumericCellValue();
                            if (gylis == null){productCatalog.setGylis(0);}
                            else {productCatalog.setGylis(gylis);}
                            break;
                        case 9:
                            String ip_klase = cell.getStringCellValue();
                            if (ip_klase.equals("")){productCatalog.setIp_klase(null);}
                            else {productCatalog.setIp_klase(ip_klase);}
                            break;
                        case 10:String spalva = cell.getStringCellValue();
                            if (spalva.equals("")){productCatalog.setSpalva(null);}
                            else {productCatalog.setSpalva(spalva);}
                            break;
                        case 11:
                            String korpusas = cell.getStringCellValue();
                            if (korpusas.equals("")){productCatalog.setKorpusas(null);}
                            else {productCatalog.setKorpusas(korpusas);}
                            break;
                        case 12:
                            String tipas = cell.getStringCellValue();
                            if (tipas.equals("")){productCatalog.setTipas(null);}
                            else {productCatalog.setTipas(tipas);}
                            break;
                        case 13:
                            Double vardine_itampa = cell.getNumericCellValue();
                            if (vardine_itampa == null){productCatalog.setVardine_itampa(0);}
                            else {productCatalog.setVardine_itampa(vardine_itampa);}
                            break;
                        case 14:
                            Double galia = cell.getNumericCellValue();
                            if (galia == null){productCatalog.setGalia(0);}
                            else {productCatalog.setGalia(galia);}
                            break;
                        case 15:
                            Double sviesos_srautas = cell.getNumericCellValue();
                            if (sviesos_srautas == null){productCatalog.setSviesos_srautas(0);}
                            else {productCatalog.setSviesos_srautas(sviesos_srautas);}
                            break;
                        case 16:
                            String atsparumo_klase = cell.getStringCellValue();
                            if (atsparumo_klase.equals("")){productCatalog.setAtsparumo_klase(null);}
                            else {productCatalog.setAtsparumo_klase(atsparumo_klase);}
                            break;
                        case 17:
                            String matmenys = cell.getStringCellValue();
                            if (matmenys.equals("")){productCatalog.setMatmenys(null);}
                            else {productCatalog.setMatmenys(matmenys);}
                            break;
                        case 18:
                            Integer darbine_temperatura = (int) cell.getNumericCellValue();
                            if (darbine_temperatura == null){productCatalog.setDarbine_temperatura(0);}
                            else {productCatalog.setDarbine_temperatura(darbine_temperatura);}
                            break;
                    }

                }
                if (productCatalog.getCatalogNo().isEmpty() && productCatalog.getSymbol().isEmpty() && productCatalog.getGroupId() == 0) {
                    break;
                }
                products.add(productCatalog);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            workbook.close();
            inputStream.close();
        }
        return products;
    }
}