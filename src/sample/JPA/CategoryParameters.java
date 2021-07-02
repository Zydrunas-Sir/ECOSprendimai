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

    public CategoryParameters(int id, boolean aukstis, boolean plotis, boolean gylis, boolean ip_klase, boolean spalva, boolean korpusas, boolean tipas, boolean vardine_itampa, boolean galia, boolean sviesos_srautas, boolean atsparumo_klase, boolean matmenys, boolean darbine_temperatura) {
        this.id = id;
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



}
