/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deneme;

import java.util.Objects;

/**
 * IMDB uygulaması için oluşturulmuş <i>Puan</i> clasıdır.<hr>
 * <ol>
 *  <li type="square">(f) <code>Film film</code> </li> 
 *  <li type="square">(f) <code>byte puan</code> </li>
 *  <li type="disc">(m)-> <code>Puan()</code> </li>
 *  <li type="circle">(m)-> <code>setters() getters()</code> </li>
 *  <li type="circle">(m)-> <code>hash()-equals()</code> (depends film ve puan) </li>
 * </ol>
 * @author OEkrem
 */
public class Puan {
    private Film film;
    private byte puan;

    public Puan(Film film, byte puan) {
        this.film = film;
        this.puan = puan;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public byte getPuan() {
        return puan;
    }

    public void setPuan(byte puan) {
        this.puan = puan;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.film);
        hash = 13 * hash + this.puan;
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
        final Puan other = (Puan) obj;
        if (this.puan != other.puan) {
            return false;
        }
        if (!Objects.equals(this.film, other.film)) {
            return false;
        }
        return true;
    }
}