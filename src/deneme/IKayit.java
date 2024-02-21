package deneme;

import java.util.ArrayList;

public interface IKayit {

    public static final String kullanici_adi = "root";
    public static final String password = "";
    public static final String db_kullanici = "kullanicilar"; // kullanıcı için veri tabanı
    public static final String db_film = "filmler";   // filmler için veritabanı
    public static final String host = "localhost";
    public static final int port = 3306;

    /* Kullanıcı işlemleri  -----------------------------------------------------------------------------------  */
    /**
     * Girilen kullanıcının userName ve şifre bilgileri için database'te
     * sadece ilgili kullanıcı adı var ise <b>"<code>0</code>"</b>,
     * hem kullanıcı adı hem şifre var ise <b>"<code>1</code>"</b>,
     * iki bilgi de yok ise <b>"<code>-1</code>"</b> değerini döner.
     * @param kullanici
     * @return
     */
    public int kullaniciKontrol(Kullanici kullanici); // böyle bir kullanıcı var mı, kullanıcıAdı herkesin farklı olacak
    /**
     * Veritabanında ilgili kullanıcı adı kullanılmamış ise
     * kayıt işlemi yapacaktır. İşlem başarılı ise <b>"<code>true</code>"</b>
     * değerini döner.
     * @param kullanici
     * @return
     */
    public boolean kullaniciEkle(Kullanici kullanici);
    /**
     * Veritabanında ilgili kişi kayıtlı ise silecektir.
     * İşlem başarılı olursa <b>"<code>true</code>"</b> değerini dönecektir.
     * @param kullanici
     * @return
     */
    public boolean kullaniciSil(Kullanici kullanici);

    /*Puanlama sistemi  --------------------------------------------------------------------------------------   */
    /**
     * Bu fonksiyon ilgili kullanıcının ilgili filme daha önce
     * puanlama yapıp yapmadığını bize söyler. Dönen değer <b>"<code>true</code>"</b>
     * ise puanlama yapmış demektir.
     * @param kullanici
     * @param film
     * @return
     */
    public boolean puanlamaYapilmisMi(Kullanici kullanici, Film film);
    /**
     * İlgili filmin imdb puanını hesaplar. Ve bu değeri döndürür.
     * Hiç puanlama yapılmamışsa <b>"<code>0</code>"</b> değerini döner.
     * Bunun için veritabanındaki tüm kullanıcıları teker
     * teker inceler ve bir ortalama alır.
     * @param film
     * @return
     */
    public float getOrtPuan(Film film); // virgülden sonra 1 basamak olacak şekilde

    /**
     * İlgili kullanıcının <u>ilgili filme daha önce bir puanlama yapmamış ise</u>
     * yaptığı puanlamayı veri tabanına kaydeder.
     * İşlem başarılı ise <b>"<code>true</code>"</b> değerini döner.
     * @param kullanici
     * @param puan
     * @return
     */
    public boolean puanYaz(Kullanici kullanici, Puan puan);

    /**
     * İlgili kullanıcının <u>ilgili filme yaptığı puanı</u> geri döndürür.
     * Bu filme kullanıcı puanlama yapmamış ise <b>"<code>0</code>"</b> döner.
     * @param kullanici
     * @param film
     * @return
     */
    public int puanOku(Kullanici kullanici, Film film);


    /*Yorumla alakalı fonksiyonlar -------------------------------------------------------------------------- */
    /**
     * İlgili kullanıcının ilgili film için yaptığı yorum veri tabanına kaydedilir.
     * @param kullanici
     * @param yorum
     * @return
     */
    public boolean yorumYaz(Kullanici kullanici, Yorum yorum);

    /**
     * İlgili filme ait bütün yorumlar veri tabanından alınır.
     * Ve bir <code>ArrayList-String</code> türünde geri döndürür.
     * @param film
     * @return
     */
    public ArrayList<String> yorumOku(Film film);

    /**
     * İlgili yorum içerisinde argo içerikler yok ise 'true' değerini döner.
     */
    public boolean yorumUygunMu(Yorum yorum);


    /*Film ile ilgili fonksiyonlar --------------------------------------------------------------------------- */
    /**
     * Filmleri bilgileriyle veri tabanından çeker.
     * Bir ArrayList<Film> verisi döner.
     * @return
     */
    //public ArrayList<Film> filmleriCek(String s);
    //public Film getFilm(int id);
    //public ArrayList<Film> getPopularFilms();
}
