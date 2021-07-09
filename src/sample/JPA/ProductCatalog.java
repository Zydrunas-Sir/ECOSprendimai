package sample.JPA;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "product_catalog")
public class ProductCatalog {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementation")
    @GenericGenerator(name = "incrementation", strategy = "increment")
    private int id;
    @Column(name = "catalog_no")
    private String catalogNo;
    @Column(name = "symbol", length = 1024)
    private String symbol;
    @Column(name = "price_net")
    private String priceNet;
    @Column(name = "stock")
    private int stock;
    @Column(name = "group_id")
    private int groupId;
    //@ManyToOne(targetEntity = Categories.class, fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    //@JoinColumn(name = "group_id", referencedColumnName = "id")
    @Column(name = "aukstis")
    private double aukstis;
    @Column(name = "plotis")
    private double plotis;
    @Column(name = "gylis")
    private double gylis;
    @Column(name = "ip_klase")
    private String ip_klase;
    @Column(name = "spalva")
    private String spalva;
    @Column(name = "korpusas")
    private String korpusas;
    @Column(name = "tipas")
    private String tipas;
    @Column(name = "vardine_itampa")
    private double vardine_itampa;
    @Column(name = "galia")
    private double galia;
    @Column(name = "sviesos_srautas")
    private double sviesos_srautas;
    @Column(name = "atsparumo_klase")
    private String atsparumo_klase;
    @Column(name = "matmenys")
    private String matmenys;
    @Column(name = "darbine_temperatura")
    private int darbine_temperatura;
    @Column(name = "image_url", length = 1024)
    private String image_url;
    @Column(name = "date")
    private Date date;


    public ProductCatalog(int id, String catalogNo, String symbol, String priceNet, int stock, int groupId, Date date) {
        this.id = id;
        this.catalogNo = catalogNo;
        this.symbol = symbol;
        this.priceNet = priceNet;
        this.stock = stock;
        this.groupId = groupId;
        this.date = date;
    }

    public ProductCatalog(String catalogNo, String symbol, String priceNet, int stock, int groupId, double aukstis, double plotis, double gylis, String ip_klase, String spalva, String korpusas, String tipas, double vardine_itampa, double galia, double sviesos_srautas, String atsparumo_klase, String matmenys, int darbine_temperatura, String image_url, Date date) {
        this.catalogNo = catalogNo;
        this.symbol = symbol;
        this.priceNet = priceNet;
        this.stock = stock;
        this.groupId = groupId;
        this.aukstis = aukstis;
        this.plotis = plotis;
        this.gylis = gylis;
        this.ip_klase = ip_klase;
        this.spalva = spalva;
        this.korpusas = korpusas;
        this.tipas = tipas;
        this.vardine_itampa = vardine_itampa;
        this.galia = galia;
        this.sviesos_srautas = sviesos_srautas;
        this.atsparumo_klase = atsparumo_klase;
        this.matmenys = matmenys;
        this.darbine_temperatura = darbine_temperatura;
        this.image_url = image_url;
        this.date = date;
    }

    public ProductCatalog(String catalogNo, String symbol, String priceNet, int stock, int groupId, Date date) {
        this.catalogNo = catalogNo;
        this.symbol = symbol;
        this.priceNet = priceNet;
        this.stock = stock;
        this.groupId = groupId;
        this.date = date;
    }


    public ProductCatalog() {
    }

    public String getCatalogNo() {
        return catalogNo;
    }

    public void setCatalogNo(String catalogNo) {
        this.catalogNo = catalogNo;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(String priceNet) {
        this.priceNet = priceNet;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getIp_klase() {
        return ip_klase;
    }

    public void setIp_klase(String ip_klase) {
        this.ip_klase = ip_klase;
    }

    public String getSpalva() {
        return spalva;
    }

    public void setSpalva(String spalva) {
        this.spalva = spalva;
    }

    public String getKorpusas() {
        return korpusas;
    }

    public void setKorpusas(String korpusas) {
        this.korpusas = korpusas;
    }

    public String getTipas() {
        return tipas;
    }

    public void setTipas(String tipas) {
        this.tipas = tipas;
    }

    public double getAukstis() {
        return aukstis;
    }

    public void setAukstis(double aukstis) {
        this.aukstis = aukstis;
    }

    public double getPlotis() {
        return plotis;
    }

    public void setPlotis(double plotis) {
        this.plotis = plotis;
    }

    public double getGylis() {
        return gylis;
    }

    public void setGylis(double gylis) {
        this.gylis = gylis;
    }

    public double getVardine_itampa() {
        return vardine_itampa;
    }

    public void setVardine_itampa(double vardine_itampa) {
        this.vardine_itampa = vardine_itampa;
    }

    public double getGalia() {
        return galia;
    }

    public void setGalia(double galia) {
        this.galia = galia;
    }

    public double getSviesos_srautas() {
        return sviesos_srautas;
    }

    public void setSviesos_srautas(double sviesos_srautas) {
        this.sviesos_srautas = sviesos_srautas;
    }

    public String getAtsparumo_klase() {
        return atsparumo_klase;
    }

    public void setAtsparumo_klase(String atsparumo_klase) {
        this.atsparumo_klase = atsparumo_klase;
    }

    public String getMatmenys() {
        return matmenys;
    }

    public void setMatmenys(String matmenys) {
        this.matmenys = matmenys;
    }

    public int getDarbine_temperatura() {
        return darbine_temperatura;
    }

    public void setDarbine_temperatura(int darbine_temperatura) {
        this.darbine_temperatura = darbine_temperatura;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductCatalog{" +
                "id=" + id +
                ", catalogNo=" + catalogNo +
                ", symbol='" + symbol + '\'' +
                ", priceNet=" + priceNet +
                ", stock=" + stock +
                ", groupId=" + groupId +
                ", date=" + date +
                '}';
    }
}