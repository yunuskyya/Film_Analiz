
package deneme;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * IMDB uygulaması için oluşturulmuş <i>Yorum</i> clasıdır.<hr>
 * <ol>
 *  <li type="square">(f)-> <code>String yorum</code> </li> 
 *  <li type="square">(f)-> <code>Film film</code> </li>
 *  <li type="square">(f)-> <code>String date</code> </li>
 *  <li type="disc">(m)-> <code>Yorum()</code> </li>
 *  <li type="circle">(m)-> <code>setters() getters()</code> </li>
 *  <li type="circle">(m)-> <code>hash()-equals()</code> (depends yorum ve film) </li>
 * </ol>
 * @author OEkrem
 */
public class Yorum {
    private final String yorum;
    private final Film film;
    private final String date;

    public Yorum(Film film, String yorum){
        this.film = film;
        this.yorum = yorum;
        this.date = new SimpleDateFormat().format(new Date());
    }

    public String getYorum() {
        return yorum;
    }

    public Film getFilm() {
        return film;
    }

    public String getDate(){
        return date;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.yorum);
        hash = 37 * hash + Objects.hashCode(this.film);
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
        final Yorum other = (Yorum) obj;
        if (!Objects.equals(this.yorum, other.yorum)) {
            return false;
        }
        if (!Objects.equals(this.film, other.film)) {
            return false;
        }
        return true;
    }
}