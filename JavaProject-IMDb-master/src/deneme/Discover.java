package deneme;

public abstract class Discover extends Connection {

    public String getUrl() {
        final String s = "https://api.themoviedb.org/3/discover/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr";

        return s;
    }

    public String sorgu(String aranilacak) {
        return "&query=" + aranilacak;
    }

    public String siralama(int sirala) {
        /* 0 popülariteye göre sıralar */
        if (sirala == 0) return "&sort_by=popularity.desc";
        /* 1 hasılata göre sıralar */
        else return "&sort_by=revenue.desc";
    }

    public String sayfa(int sayfa) {
        return "&page=" + sayfa;
    }

    /* arama seçenekleri */
    public String yetiskinIcerigiGizleme() {
        return "&include_adult=true";
    }

    public String belirliTarih(int sene) {
        return "&year=" + sene;
    }
    public String tarihtenSonra(int sene) {
        return "&primary_release_date.gte=" + sene;
    }
    public String tarihtenOnce(int sene) {
        return "&primary_release_date.lte=" + sene;
    }

    public String tureGore(int secim) {
        switch (secim) {
            case 0 : return null;
            case 1 : return "&with_genres=28%2C"; // aksiyon
            case 2 : return "&with_genres=12%2C";  // macera
            case 3 : return "&with_genres=35%2C";  // komedi
            case 4 : return "&with_genres=80%2C";  // suç
            case 5 : return "&with_genres=99%2C";  // belgesel
            case 6 : return "&with_genres=16%2C";  // animasyon
            case 7 : return "&with_genres=18%2C";  // drama
            case 8 : return "&with_genres=10751%2C";  // aile
            case 9 : return "&with_genres=14%2C";  // fantastik
            case 10 : return "&with_genres=36%2C";  // tarih
            case 11 : return "&with_genres=27%2C";  // korku
            case 12 : return "&with_genres=10402%2C"; // müzik
            case 13 : return "&with_genres=9648%2C"; // gizem
            case 14 : return "&with_genres=10749%2C"; // romantik
            case 15 : return "&with_genres=878%2C"; // bilim kurgu
            case 16 : return "&with_genres=53%2C";  // gerilim
            case 17 : return "&with_genres=10752%2C"; // savaş
            case 18 : return "&with_genres=37%2C"; // western

            default: return "\0";
        }


    }

}
