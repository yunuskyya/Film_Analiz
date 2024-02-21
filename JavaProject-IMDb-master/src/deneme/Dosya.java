
package deneme;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * IMDB projesi için oluşturulmuş <i>Dosya</i> classıdır.<hr>
 * <ol type="circle"> Kullanıcı İşlemleri:
 *  <li> (m)-> <code>boolean kullanicKontrol(Kullanici kullanici)</code> </li>
 *  <li> (m)-> <code>boolean kullaniciEkle(Kullanici kullanici)</code> </li>
 *  <li> (m)-> <code>boolean kullaniciSil(Kullanici kullanici)</code> </li>
 * </ol> <!-- <hr> -->
 * <ol type="disc"> Puanlama İşlemleri:
 *  <li> (m)-> <code>boolean puanlamaYapilmisMi(Kullanici kullanici, Film film)</code> </li>
 *  <li> (m)-> <code>float getOrtPuan(Film film)</code> </li>
 *  <li> (m)-> <code>boolean puanYaz(Kullanici kullanici, Puan puan)</code> </li>
 *  <li> (m)-> <code>int puanOku(Kullanici kullanici, Film film)</code> </li>
 *  <li> (m)-> <code>boolean puanGuncelle(Kullanici kullanici, Puan puan)</code> </li>
 * </ol> <!-- <hr> -->
 * <ol type="circle"> Yorum İşlemleri:
 *  <li> (m)-> <code>boolean yorumYaz(Kullanici kullanici, Yorum yorum)</code> </li>
 *  <li> (m)-> <code>ArrayList-String yorumOku(Film film)</code> </li>
 * </ol> <!-- <hr> -->
 *     Buradaki fonksiyonlar, poarametrelerine null değer gönderilirse ya false ya da 0 değerini dönerler
 * @author OEkrem
 */
public class Dosya implements IKayit{

    public static final String ayrac = "-";
    public static final String k_Ayrac = " ";
    private static final String yorumDosyasi = "IMDB/yorum.bin"; // zaman, kullaniciAdi, filmID, yorum
    private static final String puanDosyasi = "IMDB/puan.bin";   // kullaniciAdi, FilmID, puan
    private static final String kullaniciDosyasi = "IMDB/kullanici.bin"; // kullaniciAdi, sifre
    //private static final String filmDosyasi = "IMDB/filmler.bin"; // filmler başka yerden çekiliyor

    @Override
    public int kullaniciKontrol(Kullanici kullanici) {
        if(kullanici == null) return -1;
        try(Scanner scanner = new Scanner(new FileReader(kullaniciDosyasi))){
            while(scanner.hasNextLine()){
                String[] temp = scanner.nextLine().split(ayrac);
                if(temp.length == 2){
                    if(kullanici.getKullaniciAdi().equals(temp[0]) && kullanici.getSifre().equals(temp[1]))
                        return 1;
                    else if(kullanici.getKullaniciAdi().equals(temp[0]))
                        return 0;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya-kullaniciKontrol()-FilneNotfoundException : " + kullaniciDosyasi);
        }
        return -1;
    }

    @Override
    public boolean kullaniciEkle(Kullanici kullanici) {
        if(kullanici == null) return false;
        if(kullaniciKontrol(kullanici) == -1){
            try(BufferedWriter br = new BufferedWriter(new FileWriter(kullaniciDosyasi, true))){
                br.write(kullanici.getKullaniciAdi() + k_Ayrac + kullanici.getSifre() + "\n");
                System.out.println("Kullanıcı eklendi : " + kullanici.getKullaniciAdi());
                return true;
            } catch (IOException ex) {
                System.out.println("Dosya-kullaniciEkle()- IOException : " + kullaniciDosyasi);
            }
        }else{
            System.out.println("Böyle bir kullanıcı zaten bulunuyor..");
        }
        return false;
    }

    @Override
    public boolean kullaniciSil(Kullanici kullanici) {
        if( kullanici == null ) return false;
        long ilgiliSatirBasi;
        if(kullaniciKontrol(kullanici) != -1){
            try(RandomAccessFile raf = new RandomAccessFile(kullaniciDosyasi, "rw")){
                raf.seek(0);
                while(raf.getFilePointer() < raf.length()){
                    ilgiliSatirBasi = raf.getFilePointer();
                    String yazi = raf.readLine();
                    if(kullanici.getKullaniciAdi().equals(yazi.split(k_Ayrac)[0])){
                        raf.seek(ilgiliSatirBasi);
                        for(int i = 0; i<yazi.length(); i++)
                            raf.writeByte(' ');
                        raf.writeByte('\n');
                        return true;
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Dosya-kullaniciSil()-FileNotFoundExcepiton : " + kullaniciDosyasi);
            } catch (IOException ex) {
                System.out.println("Dosya-kullaniciSil()-IOExcepiton : " + kullaniciDosyasi);
            }
        }else
            System.out.println("Böyle bir kullanıcı zaten bulunmuyor..");

        return false;
    }

    @Override
    public boolean puanlamaYapilmisMi(Kullanici kullanici, Film film) {
        if (kullanici == null || film == null) return false;
        if(kullaniciKontrol(kullanici) != -1){
            try(Scanner scanner = new Scanner(new FileReader(puanDosyasi))){
                while(scanner.hasNext()){
                    String[] yazi = scanner.nextLine().split(ayrac);
                    if(yazi.length == 3)
                        if(yazi[0].equals(kullanici.getKullaniciAdi()) && Integer.parseInt(yazi[1]) == film.getId())
                            return true;

                }
            } catch (FileNotFoundException ex) {
                System.out.println("Dosya-puanlamaYapilmismi()-FileNotFoundException : " + puanDosyasi);
            }
        }
        return false;
    }

    @Override
    public float getOrtPuan(Film film) {
        if(film == null) return 0;
        float toplam = 0;
        int kisi = 0;
        try(Scanner scanner = new Scanner(new FileReader(puanDosyasi))){
            while(scanner.hasNext()){
                String[] yazi = scanner.nextLine().split(ayrac);
                if(yazi.length == 3){
                    if(Integer.parseInt(yazi[1]) == film.getId()){
                        toplam += Integer.parseInt(yazi[2]);
                        kisi++;
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya-getOrtPuan()-FileNotFoundException : " + puanDosyasi);
        }
        if(kisi == 0 || toplam == 0)
            return 0;
        return (float)(toplam/kisi);
    }

    @Override
    public boolean puanYaz(Kullanici kullanici, Puan puan) {
        if(kullanici == null || puan == null) return false;
        if(puanlamaYapilmisMi(kullanici, puan.getFilm())){
            puanGuncelle(kullanici, puan);
            // return false;
        }else{
            try(BufferedWriter br = new BufferedWriter(new FileWriter(puanDosyasi, true))){
                br.write(kullanici.getKullaniciAdi() + ayrac + puan.getFilm().getId() + ayrac + puan.getPuan() + "\n");
            } catch (IOException ex) {
                System.out.println("Dosya-puanYaz()-IOException : " + puanDosyasi);
            }
        }
        return true;
    }

    @Override
    public int puanOku(Kullanici kullanici, Film film) {
        if(kullanici == null || film == null) return 0;
        if(puanlamaYapilmisMi(kullanici, film)){
            try(Scanner scanner = new Scanner(new FileReader(puanDosyasi))){
                while(scanner.hasNext()){
                    String[] yazi = scanner.nextLine().split(ayrac);
                    if(yazi.length == 3)
                        if(yazi[0].equals(kullanici.getKullaniciAdi()) && Integer.parseInt(yazi[1]) == film.getId())
                            return Integer.parseInt(yazi[2]);

                }
            } catch (FileNotFoundException ex) {
                System.out.println("Dosya-puanOku()-FileNotFoundExcepiton : " + puanDosyasi);
            }
        }
        return 0;
    }

    private boolean puanGuncelle(Kullanici kullanici, Puan puan) {
        if(kullanici == null || puan == null) return false;
        long ilgiliSatirBasi = 0;
        String yazi = " ";
        if(kullaniciKontrol(kullanici) != -1){
            if(puanlamaYapilmisMi(kullanici, puan.getFilm())){
                try(RandomAccessFile raf = new RandomAccessFile(puanDosyasi, "rw")){
                    while(ilgiliSatirBasi < raf.length()){
                        ilgiliSatirBasi = raf.getFilePointer();
                        yazi = raf.readLine();
                        if(yazi.split(ayrac)[0].equals(kullanici.getKullaniciAdi()) &&
                                Integer.parseInt(yazi.split(ayrac)[1]) == puan.getFilm().getId()){
                            break;
                        }
                    }
                    raf.seek(ilgiliSatirBasi);
                    raf.writeBytes(kullanici.getKullaniciAdi() + ayrac + puan.getFilm().getId() + ayrac);
                    if(puan.getPuan() == 10){
                        raf.writeBytes("10\n");
                    }else{
                        raf.writeBytes("0" + puan.getPuan() + "\n");
                    }
                    return true;
                } catch (FileNotFoundException ex) {
                    System.out.println("Dosya-puanGuncelle()-FileNotFoundException : " + puanDosyasi);
                } catch (IOException ex) {
                    System.out.println("Dosya-puanGuncelle()-IOException : " + puanDosyasi);
                }
            }
        }
        return false;
    }

    @Override
    public boolean yorumYaz(Kullanici kullanici, Yorum yorum) {
        if(kullanici == null || yorum == null) return false;
        try(BufferedWriter br = new BufferedWriter(new FileWriter(yorumDosyasi, true))){
            br.write(yorum.getDate() + ayrac + kullanici.getKullaniciAdi() + ayrac +
                    yorum.getFilm().getId() + ayrac + yorum.getYorum() + "\n");
            return true;
        } catch (IOException ex) {
            System.out.println("Dosya-yorumYaz()-IOException : " + yorumDosyasi);
        }
        return false;
    }

    @Override
    public ArrayList<String> yorumOku(Film film) {
        if(film == null) return new ArrayList<>();
        ArrayList<String> yorumlar = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(yorumDosyasi))){
            while(scanner.hasNext()){
                String yazi = scanner.nextLine();
                String[] tmp = yazi.split(ayrac);
                if(!yazi.trim().equals(""))
                    if(tmp.length == 4)
                        if(Integer.parseInt(tmp[2]) == film.getId())
                            yorumlar.add(yazi);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya-yorumOku()-FileNotFoundException : " + yorumDosyasi);
        }
        return yorumlar;
    }

    @Override
    public boolean yorumUygunMu(Yorum yorum){
        if(yorum == null) return false;
        String yazi = yorum.getYorum().toLowerCase().trim();
        char[] temp = yazi.toCharArray();
        for(int i = 0; i < yazi.length(); i++){
            if(temp[i] == 'ş') temp[i] = 's';
            else if(temp[i] == 'ğ') temp[i] = 'g';
            else if(temp[i] == 'ç') temp[i] = 'c';
            else if(temp[i] == 'ö') temp[i] = 'o';
            else if(temp[i] == 'ı') temp[i] = 'i';
        }
        //String[] yazi2 = temp.toString().split(" ");

        HashSet<String> argolar = new HashSet<String>();
        argolar.add("aptal");argolar.add("mal");argolar.add("okuz");argolar.add("gerizekali");
        argolar.add("hiyar");argolar.add("angut");argolar.add("salak");argolar.add("kokos");

        for(String tmp2 : argolar){
            if(String.valueOf(temp).contentEquals(tmp2))
                return false;
        }
        return true;
    }


    /**************************************************************************/
    /**
     * @param s
     */
    /*
    @Override
    public ArrayList<Film> filmleriCek(String s){ // sadece belli bir sayıda filmleri dönecek mesela 25 tane film
        ArrayList<Film> filmler = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(filmDosyasi))){
            while (scanner.hasNext()) {
                String[] temp = scanner.nextLine().split(ayrac);
                filmler.add(new Film(Integer.parseInt(temp[0]), temp[1], temp[2], temp[3], temp[4]));
                if(filmler.size() >= 25)
                    break;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya-filmleriCek()-FileNotFoundException : " + filmDosyasi);
        }
        return filmler;
    }
    @Override
    public Film getFilm(int id){
        try(Scanner scanner = new Scanner(new FileReader(filmDosyasi))){
            while(scanner.hasNext()){
                String[] yazi = scanner.nextLine().split(ayrac);
                if(Integer.parseInt(yazi[0]) == id){
                    return new Film(id, yazi[1], yazi[2], yazi[3], yazi[4]);
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Dosya-getFilm()-FileNotFoundException : " + filmDosyasi);
        }
        return null;
    }
    @Override
    public ArrayList<Film> getPopularFilms() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    */

}