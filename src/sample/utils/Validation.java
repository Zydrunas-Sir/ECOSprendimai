package sample.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final String FIRST_NAME_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";

    public static boolean isValidFirstName(String username) {
        Pattern pattern = Pattern.compile(FIRST_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String LAST_NAME_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";

    public static boolean isValidLastName(String username) {
        Pattern pattern = Pattern.compile(LAST_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String COMPANY_NAME_PATTERN = "^((?![\\^!@#$*~ <>?]).)((?![\\^!@#$*~<>?]).){0,255}((?![\\^!@#$*~ <>?]).)$";

    public static boolean isValidCompanyName(String username) {
        Pattern pattern = Pattern.compile(COMPANY_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String REGISTER_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zĄąČčĘęĖėĮįŠšŲųŪūŽž])(?=.*[A-ZĄąČčĘęĖėĮįŠšŲųŪūŽž])(?=.*[a-zA-Z]).\\S{7,255}$";

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGISTER_PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public static final String PASSWORD_CONTAIN_ONE_UPPERCASE = "(.*[A-Z].*)";

    public static boolean isOneUpperCaseExist(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_CONTAIN_ONE_UPPERCASE);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
    public static final String REGISTER_EMAIL_PATTERN = "^[A-Za-z0-9._%+-a-zĄąČčĘęĖėĮįŠšŲųŪūŽž]+@[A-Za-z0-9.-]+.[A-Za-z]{2,255}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGISTER_EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static final String PASSWORD_CONTAIN_ONE_SYMBOL = "(?=.*\\d)";

    public static boolean isOneDigitAre(String email) {
        Pattern pattern = Pattern.compile(PASSWORD_CONTAIN_ONE_SYMBOL);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_CATALOG_NO_PATTERN = "^[A-Za-z0-9]{1,255}$";
    public static boolean isValidCatalogNo(String catalogNo){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_CATALOG_NO_PATTERN);
        Matcher matcher = pattern.matcher(catalogNo);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_SYMBOL_PATTERN = "^((?![\\^!@#$*~ <>?]).)((?![\\^!@#$*~<>?]).){0,1024}((?![\\^!@#$*~ <>?]).)$";
    public static boolean isValidSymbol(String symbol){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SYMBOL_PATTERN);
        Matcher matcher = pattern.matcher(symbol);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_PRICE_PATTERN = "^[0-9]{1,253}(\\.?[0-9]{1,2})$";
    public static boolean isValidPrice(String price){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_PRICE_PATTERN);
        Matcher matcher = pattern.matcher(price);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_STOCK_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidStock(String stock){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_STOCK_PATTERN);
        Matcher matcher = pattern.matcher(stock);
        return matcher.find();
    }

    public static final String CATEGORY_FORM_NAME_PATTERN = "^[A-Za-z0-9 ._%+-a-zĄąČčĘęĖėĮįŠšŲųŪūŽž]{2,512}$";
    public static boolean isValidCategoryName(String name){
        Pattern pattern = Pattern.compile(CATEGORY_FORM_NAME_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    /* Naujo produkto kūrimo lango Validacija : Start */

    public static final String PRODUCT_FORM_ATSPARUMO_KLASE_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isValidAtsparumas(String atsparumo_klase) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_ATSPARUMO_KLASE_PATTERN);
        Matcher matcher = pattern.matcher(atsparumo_klase);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_AUKSTIS_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidAukstis(String aukstis) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_AUKSTIS_PATTERN);
        Matcher matcher = pattern.matcher(aukstis);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_DARBINE_TEMPERATURA_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidDarbineTemperatura(String darbine_temperatura) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_DARBINE_TEMPERATURA_PATTERN);
        Matcher matcher = pattern.matcher(darbine_temperatura);
        return matcher.find();
    }


    public static final String PRODUCT_FORM_GALIA_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidGalia(String galia) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_GALIA_PATTERN);
        Matcher matcher = pattern.matcher(galia);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_GYLIS_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidGylis(String gylis) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_GYLIS_PATTERN);
        Matcher matcher = pattern.matcher(gylis);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_KORPUSAS_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isValidKorpusas(String korpusas) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_KORPUSAS_PATTERN);
        Matcher matcher = pattern.matcher(korpusas);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_MATMENYS_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isValidMatmenys(String matmenys) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_MATMENYS_PATTERN);
        Matcher matcher = pattern.matcher(matmenys);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_PLOTIS_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidPlotis(String plotis) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_PLOTIS_PATTERN);
        Matcher matcher = pattern.matcher(plotis);
        return matcher.find();
    }


    public static final String PRODUCT_FORM_SPALVA_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{2,255}$";
    public static boolean isValidSpalva(String spalva) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SPALVA_PATTERN);
        Matcher matcher = pattern.matcher(spalva);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_SVIESOS_SRAUTAS_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidSviesosSrautas(String sviesos_srautas){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SVIESOS_SRAUTAS_PATTERN);
        Matcher matcher = pattern.matcher(sviesos_srautas);
        return matcher.find();
    }


    public static final String PRODUCT_FORM_TIPAS_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isValidTipas(String tipas) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_TIPAS_PATTERN);
        Matcher matcher = pattern.matcher(tipas);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_VARDINE_ITAMPA_PATTERN = "^[0-9]{1,11}$";
    public static boolean isValidVardineItampa(String vardine_itampa) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_VARDINE_ITAMPA_PATTERN);
        Matcher matcher = pattern.matcher(vardine_itampa);
        return matcher.find();
    }

    /* Naujo produkto kūrimo lango Validacija : End */

}
