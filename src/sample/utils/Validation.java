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

    public static final String PRODUCT_FORM_SYMBOL_PATTERN = "^((?![\\^!@#$*~ <>?]).)((?![\\^!@#$*~<>?]).){1,1024}((?![\\^!@#$*~ <>?]).)$";
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

    public static final String PRODUCT_FORM_KORPUSO_MEDZIAGA_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isValidKorpusas(String korpusas) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_KORPUSO_MEDZIAGA_PATTERN);
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

    public static final String PRODUCT_FORM_IP_KLASE_PATTERN = "^[0-9a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{1,255}$";
    public static boolean isValidIpKlase(String ip_klase) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_IP_KLASE_PATTERN);
        Matcher matcher = pattern.matcher(ip_klase);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_MECHANICAL_RESISTANCE_IK = "^[0-9]{1,10}$";
    public static boolean isValidMechanicalResistanceIk(String mr_ik) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_MECHANICAL_RESISTANCE_IK);
        Matcher matcher = pattern.matcher(mr_ik);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_NUMBER_OF_MODULES_PATTERN = "^[0-9]{1,10}$";
    public static boolean isValidNumberOfModules(String numberOfModules) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_NUMBER_OF_MODULES_PATTERN);
        Matcher matcher = pattern.matcher(numberOfModules);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_DIAMETER = "^[0-9]{1,10}$";
    public static boolean isValidDiameter(String diameter) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_DIAMETER);
        Matcher matcher = pattern.matcher(diameter);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_LENGTH = "^[0-9]{1,10}$";
    public static boolean isValidLength(String length) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_LENGTH);
        Matcher matcher = pattern.matcher(length);
        return matcher.find();
    }

    public static final String PRODUCT_MODULE_COUNT_PATTERN = "^[0-9]{1,10}$";
    public static boolean isModuleCount(String moduleCount) {
        Pattern pattern = Pattern.compile(PRODUCT_MODULE_COUNT_PATTERN);
        Matcher matcher = pattern.matcher(moduleCount);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_MASS_PATTERN = "^[0-9]{1,10}$";
    public static boolean isMass(String mass) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_MASS_PATTERN);
        Matcher matcher = pattern.matcher(mass);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_SHELL_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isShell(String shell) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SHELL_PATTERN);
        Matcher matcher = pattern.matcher(shell);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_ISOLATION_PATTERN = "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isIsolation(String isolation) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_ISOLATION_PATTERN);
        Matcher matcher = pattern.matcher(isolation);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_CONDUCTOR_PATTERN = "^[0-9]{1,10}$";
    public static boolean isConductor(String conductor) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_CONDUCTOR_PATTERN);
        Matcher matcher = pattern.matcher(conductor);
        return matcher.find();

    }

    public static final String PRODUCT_FORM_LIGHT_COLOR_TEMPERATURE_PATTERN= "^[0-9]{1,10}$";
    public static boolean isLightColor(String lightColorTemperature) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_LIGHT_COLOR_TEMPERATURE_PATTERN);
        Matcher matcher = pattern.matcher(lightColorTemperature);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_MAX_OPERATION_TEMPERATURE_PATTERN= "^[0-9]{1,10}$";
    public static boolean isOperationTemerature(String operationTemperature) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_MAX_OPERATION_TEMPERATURE_PATTERN);
        Matcher matcher = pattern.matcher(operationTemperature);
        return matcher.find();
    }

    public static final String PRODUCT_FORM_SHUTDOWN_CAPABILITY_PATTERN= "^[0-9]{1,10}$";
    public static boolean isShutDown(String shutdownCapability) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SHUTDOWN_CAPABILITY_PATTERN);
        Matcher matcher = pattern.matcher(shutdownCapability);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_SHUTDOWN_CHARACTERISTIC_PATTERN= "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isShutDownCh(String shutdownCharacteristic) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SHUTDOWN_CHARACTERISTIC_PATTERN);
        Matcher matcher = pattern.matcher(shutdownCharacteristic);
        return matcher.find();
    }
    public static final String PRODUCT_FORM_CPR_CLASS_PATTERN= "^[a-zA-ZĄąČčĘęĖėĮįŠšŲųŪūŽž]{3,255}$";
    public static boolean isCprClass(String cpr) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_CPR_CLASS_PATTERN);
        Matcher matcher = pattern.matcher(cpr);
        return matcher.find();

    }

    public static final String PRODUCT_FORM_MECHANICAL_RESISTANCE_PATTERN= "^[0-9]{1,10}$";
    public static boolean isResistence(String mechanicalResistance) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_MECHANICAL_RESISTANCE_PATTERN);
        Matcher matcher = pattern.matcher(mechanicalResistance);
        return matcher.find();

    }

    public static final String PRODUCT_FORM_CROSSCUT_PATTERN= "^[0-9]{1,10}$";
    public static boolean isCrosscut(String crosscut) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_CROSSCUT_PATTERN);
        Matcher matcher = pattern.matcher(crosscut);
        return matcher.find();

    }

    public static final String PRODUCT_FORM_LEAKAGE_CURRENT_PATTERN= "^[0-9]{1,10}$";
    public static boolean isLeakageCurrent(String leakageCurrent) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_LEAKAGE_CURRENT_PATTERN);
        Matcher matcher = pattern.matcher(leakageCurrent);
        return matcher.find();

    }
    public static final String PRODUCT_FORM_SIZE_PATTERN= "^[0-9]{1,10}$";
    public static boolean isSize(String size) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_SIZE_PATTERN);
        Matcher matcher = pattern.matcher(size);
        return matcher.find();

    }

    public static final String PRODUCT_FORM_AREA_PATTERN= "^[0-9]{1,10}$";
    public static boolean isArea(String area) {
        Pattern pattern = Pattern.compile(PRODUCT_FORM_AREA_PATTERN);
        Matcher matcher = pattern.matcher(area);
        return matcher.find();

    }
    /* Naujo produkto kūrimo lango Validacija : End */

}
