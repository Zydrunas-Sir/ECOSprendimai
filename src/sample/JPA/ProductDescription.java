package sample.JPA;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "product_description")
public class ProductDescription {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementation")
    @GenericGenerator(name = "incrementation", strategy = "increment")
    private int id;
    @Column(name = "catalog_no")
    private int catalogNo;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "base_price")
    private double basePrice;
    @Column(name = "discountInPercent")
    private double discountInPercent;
    @Column(name = "delivery_time_in_days_from")
    private int deliveryTimeInDaysFrom;
    @Column(name = "delivery_time_in_days_to")
    private int deliveryTimeInDaysTo;
    @Column(name = "item_package")
    private String itemPackage;
    @Column(name = "min_order_amount")
    private int minOrderAmount;
    @Column(name = "discount_group")
    private String discountGroup;
    @Column(name = "product_family")
    private String productFamily;
    @Column(name = "ean_code")
    private String eanCode;

    public ProductDescription() {
    }

    public ProductDescription(int catalogNo, String itemName, double basePrice, double discountInPercent, int deliveryTimeInDaysFrom, int deliveryTimeInDaysTo, String itemPackage, int minOrderAmount, String discountGroup, String productFamily, String eanCode) {
        this.catalogNo = catalogNo;
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.discountInPercent = discountInPercent;
        this.deliveryTimeInDaysFrom = deliveryTimeInDaysFrom;
        this.deliveryTimeInDaysTo = deliveryTimeInDaysTo;
        this.itemPackage = itemPackage;
        this.minOrderAmount = minOrderAmount;
        this.discountGroup = discountGroup;
        this.productFamily = productFamily;
        this.eanCode = eanCode;
    }

    public ProductDescription(int id, int catalogNo, String itemName, double basePrice, double discountInPercent, int deliveryTimeInDaysFrom, int deliveryTimeInDaysTo, String itemPackage, int minOrderAmount, String discountGroup, String productFamily, String eanCode) {
        this.id = id;
        this.catalogNo = catalogNo;
        this.itemName = itemName;
        this.basePrice = basePrice;
        this.discountInPercent = discountInPercent;
        this.deliveryTimeInDaysFrom = deliveryTimeInDaysFrom;
        this.deliveryTimeInDaysTo = deliveryTimeInDaysTo;
        this.itemPackage = itemPackage;
        this.minOrderAmount = minOrderAmount;
        this.discountGroup = discountGroup;
        this.productFamily = productFamily;
        this.eanCode = eanCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(int catalogNo) {
        this.catalogNo = catalogNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscountInPercent() {
        return discountInPercent;
    }

    public void setDiscountInPercent(double discountInPercent) {
        this.discountInPercent = discountInPercent;
    }

    public int getDeliveryTimeInDaysFrom() {
        return deliveryTimeInDaysFrom;
    }

    public void setDeliveryTimeInDaysFrom(int deliveryTimeInDaysFrom) {
        this.deliveryTimeInDaysFrom = deliveryTimeInDaysFrom;
    }

    public int getDeliveryTimeInDaysTo() {
        return deliveryTimeInDaysTo;
    }

    public void setDeliveryTimeInDaysTo(int deliveryTimeInDaysTo) {
        this.deliveryTimeInDaysTo = deliveryTimeInDaysTo;
    }

    public String getItemPackage() {
        return itemPackage;
    }

    public void setItemPackage(String itemPackage) {
        this.itemPackage = itemPackage;
    }

    public int getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(int minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public String getDiscountGroup() {
        return discountGroup;
    }

    public void setDiscountGroup(String discountGroup) {
        this.discountGroup = discountGroup;
    }

    public String getProductFamily() {
        return productFamily;
    }

    public void setProductFamily(String productFamily) {
        this.productFamily = productFamily;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }
}
