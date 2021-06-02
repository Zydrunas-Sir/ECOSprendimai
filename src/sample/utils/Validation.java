package sample.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final String FIRST_NAME_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,26}$";

    public static boolean isValidFirstName(String username) {
        Pattern pattern = Pattern.compile(FIRST_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String LAST_NAME_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,26}$";

    public static boolean isValidLastName(String username) {
        Pattern pattern = Pattern.compile(LAST_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String COMPANY_NAME_PATTERN = "^((?![\\^!@#$*~ <>?]).)((?![\\^!@#$*~<>?]).){0,73}((?![\\^!@#$*~ <>?]).)$";

    public static boolean isValidCompanyName(String username) {
        Pattern pattern = Pattern.compile(COMPANY_NAME_PATTERN); //Pagal jūsu Stringę aprašytas taisyklęs sukurią šabloną
        Matcher matcher = pattern.matcher(username); //Pagal ankš// čiau sukurtą šablona yra palyginima duomenys
        return matcher.find(); //Grazina true jei atitinka validacija, false jei neatitinka
    }

    public static final String REGISTER_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zĄąČčĘęĖėĮįŠšŲųŪūŽž])(?=.*[A-ZĄąČčĘęĖėĮįŠšŲųŪūŽž])(?=.*[a-zA-Z]).\\S{8,15}$";

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(REGISTER_PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static final String REGISTER_EMAIL_PATTERN = "^[A-Za-z0-9._%+-a-zĄąČčĘęĖėĮįŠšŲųŪūŽž]+@[A-Za-z0-9.-]+.[A-Za-z]{2,50}$";

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(REGISTER_EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_CATALOG_NO_PATTERN = "^[0-9]{1,25}$";
    public static boolean isValidCatalogNo(String catalogNo){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_CATALOG_NO_PATTERN);
        Matcher matcher = pattern.matcher(catalogNo);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_SYMBOL_PATTERN = "^((?![\\^!@#$*~ <>?]).)((?![\\^!@#$*~<>?]).){0,73}((?![\\^!@#$*~ <>?]).)$";
    public static boolean isValidSymbol(String symbol){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SYMBOL_PATTERN);
        Matcher matcher = pattern.matcher(symbol);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_PRICE_PATTERN = "^[0-9.]{1,25}$";
    public static boolean isValidPrice(String price){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_PRICE_PATTERN);
        Matcher matcher = pattern.matcher(price);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_STOCK_PATTERN = "^[0-9]{1,25}$";
    public static boolean isValidStock(String stock){
        Pattern pattern = Pattern.compile(PRODUCT_FORM_STOCK_PATTERN);
        Matcher matcher = pattern.matcher(stock);
        return matcher.find();
    }
}
