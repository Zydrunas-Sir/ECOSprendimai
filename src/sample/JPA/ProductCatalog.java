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
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "price_net")
    private String priceNet;
    @Column(name = "stock")
    private int stock;
    //@ManyToOne(targetEntity = Categories.class, fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    //@JoinColumn(name = "group_id", referencedColumnName = "id")
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "aukstis")
    private int aukstis;
    @Column(name = "plotis")
    private int plotis;
    @Column(name = "gylis")
    private int gylis;
    @Column(name = "ip_klase")
    private String ip_klase;
    @Column(name = "spalva")
    private String spalva;
    @Column(name = "korpusas")
    private String korpusas;
    @Column(name = "tipas")
    private String tipas;
    @Column(name = "vardine_itampa")
    private int vardine_itampa;
    @Column(name = "galia")
    private int galia;
    @Column(name = "sviesos_srautas")
    private int sviesos_srautas;
    @Column(name = "atsparumo_klase")
    private String atsparumo_klase;
    @Column(name = "matmenys")
    private String matmenys;
    @Column(name = "darbine_temperatura")
    private int darbine_temperatura;
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

    public int getAukstis() {
        return aukstis;
    }

    public void setAukstis(int aukstis) {
        this.aukstis = aukstis;
    }

    public int getPlotis() {
        return plotis;
    }

    public void setPlotis(int plotis) {
        this.plotis = plotis;
    }

    public int getGylis() {
        return gylis;
    }

    public void setGylis(int gylis) {
        this.gylis = gylis;
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

    public int getVardine_itampa() {
        return vardine_itampa;
    }

    public void setVardine_itampa(int vardine_itampa) {
        this.vardine_itampa = vardine_itampa;
    }

    public int getGalia() {
        return galia;
    }

    public void setGalia(int galia) {
        this.galia = galia;
    }

    public int getSviesos_srautas() {
        return sviesos_srautas;
    }

    public void setSviesos_srautas(int sviesos_srautas) {
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

