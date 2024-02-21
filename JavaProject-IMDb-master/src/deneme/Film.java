package deneme;

import javax.swing.*;
import java.util.ArrayList;

public class Film {

    private final int id;
    private String filmAdi;
    private ArrayList<String> kategori;
    private String filmBilgisi;
    private String yayinYili;
    private ImageIcon filmResmi;


    public Film(int id){
        this.id = id;
    }

    public Film(int id, String filmAdi, ArrayList<String> kategori, String filmBilgisi, String yayinYili, ImageIcon resimUzantisi) {
        this.id = id;
        this.filmAdi = filmAdi;
        this.kategori = kategori;
        this.filmBilgisi = filmBilgisi;
        this.yayinYili = yayinYili;
        this.filmResmi = resimUzantisi;
    }

    public int getId() {
        return id;
    }

    public String getFilmAdi() {
        return filmAdi;
    }

    public void setFilmAdi(String filmAdi) {
        this.filmAdi = filmAdi;
    }

    public ArrayList<String> getKategori() {
        return kategori;
    }

    public void setKategori(ArrayList<String> kategori) {
        this.kategori = kategori;
    }

    public String getFilmBilgisi() {
        return filmBilgisi;
    }

    public void setFilmBilgisi(String filmBilgisi) {
        this.filmBilgisi = filmBilgisi;
    }

    public String getYayinYili() {
        return yayinYili;
    }

    public void setYayinYili(String yayinYili) {
        this.yayinYili = yayinYili;
    }

    public ImageIcon getFilmResmi() {
        return filmResmi;
    }

    public void setFilmResmi(ImageIcon filmResmi) {
        this.filmResmi = filmResmi;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Film other = (Film) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}