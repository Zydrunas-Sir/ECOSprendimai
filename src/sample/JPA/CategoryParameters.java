package sample.JPA;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "category_parameters")
public class CategoryParameters {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementation")
    @GenericGenerator(name = "incrementation", strategy = "increment")
    private int id;
    @Column(name = "aukstis")
    private boolean aukstis;
    @Column(name = "plotis")
    private boolean plotis;
    @Column(name = "gylis")
    private boolean gylis;
    @Column(name = "ip_klase")
    private boolean ip_klase;
    @Column(name = "spalva")
    private boolean spalva;
    @Column(name = "korpusas")
    private boolean korpusas;
    @Column(name = "tipas")
    private boolean tipas;
    @Column(name = "vardine_itampa")
    private boolean vardine_itampa;
    @Column(name = "galia")
    private boolean galia;
    @Column(name = "sviesos_srautas")
    private boolean sviesos_srautas;
    @Column(name = "atsparumo_klase")
    private boolean atsparumo_klase;
    @Column(name = "matmenys")
    private boolean matmenys;
    @Column(name = "darbine_temperatura")
    private boolean darbine_temperatura;

    public CategoryParameters() {
    }

    public CategoryParameters( boolean aukstis, boolean plotis, boolean gylis, boolean ip_klase, boolean spalva, boolean korpusas, boolean tipas, boolean vardine_itampa, boolean galia, boolean sviesos_srautas, boolean atsparumo_klase, boolean matmenys, boolean darbine_temperatura) {

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
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAukstis() {
        return aukstis;
    }

    public void setAukstis(boolean aukstis) {
        this.aukstis = aukstis;
    }

    public boolean isPlotis() {
        return plotis;
    }

    public void setPlotis(boolean plotis) {
        this.plotis = plotis;
    }

    public boolean isGylis() {
        return gylis;
    }

    public void setGylis(boolean gylis) {
        this.gylis = gylis;
    }

    public boolean isIp_klase() {
        return ip_klase;
    }

    public void setIp_klase(boolean ip_klase) {
        this.ip_klase = ip_klase;
    }

    public boolean isSpalva() {
        return spalva;
    }

    public void setSpalva(boolean spalva) {
        this.spalva = spalva;
    }

    public boolean isKorpusas() {
        return korpusas;
    }

    public void setKorpusas(boolean korpusas) {
        this.korpusas = korpusas;
    }

    public boolean isTipas() {
        return tipas;
    }

    public void setTipas(boolean tipas) {
        this.tipas = tipas;
    }

    public boolean isVardine_itampa() {
        return vardine_itampa;
    }

    public void setVardine_itampa(boolean vardine_itampa) {
        this.vardine_itampa = vardine_itampa;
    }

    public boolean isGalia() {
        return galia;
    }

    public void setGalia(boolean galia) {
        this.galia = galia;
    }

    public boolean isSviesos_srautas() {
        return sviesos_srautas;
    }

    public void setSviesos_srautas(boolean sviesos_srautas) {
        this.sviesos_srautas = sviesos_srautas;
    }

    public boolean isAtsparumo_klase() {
        return atsparumo_klase;
    }

    public void setAtsparumo_klase(boolean atsparumo_klase) {
        this.atsparumo_klase = atsparumo_klase;
    }

    public boolean isMatmenys() {
        return matmenys;
    }

    public void setMatmenys(boolean matmenys) {
        this.matmenys = matmenys;
    }

    public boolean isDarbine_temperatura() {
        return darbine_temperatura;
    }

    public void setDarbine_temperatura(boolean darbine_temperatura) {
        this.darbine_temperatura = darbine_temperatura;
    }
}


