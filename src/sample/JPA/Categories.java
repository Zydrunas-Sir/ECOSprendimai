package sample.JPA;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "incrementation")
    @GenericGenerator(name = "incrementation", strategy = "increment")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "lft")
    private int lft;
    @Column(name = "rght")
    private int rght;
    @Transient
    private long countParents;

    public Categories(int id, String name, int lft, int rght) {
        this.id = id;
        this.name = name;
        this.lft = lft;
        this.rght = rght;
    }

    public Categories(int id, String name, long countParents) {
        this.id = id;
        this.name = name;
        this.countParents = countParents;
    }

    public Categories(String name, int lft, int rght) {
        this.name = name;
        this.lft = lft;
        this.rght = rght;
    }

    public Categories() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getlft() {
        return lft;
    }

    public void setlft(int lft) {
        this.lft = lft;
    }

    public int getrght() {
        return rght;
    }

    public void setrght(int rght) {
        this.rght = rght;
    }

    public long getCountParents() {
        return countParents;
    }

    public void setCountParents(long countParents) {
        this.countParents = countParents;
    }

    @Override
    public String toString() {
        return name;
    }
}
