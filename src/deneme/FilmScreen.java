package deneme;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FilmScreen {
    private Kullanici user = null;
    private Film film = null;
    private Dosya dosya = new Dosya();

    JFrame frame = new JFrame();
    JTextArea filmHakkinda = new JTextArea();
    JTextArea yorumYeri = new JTextArea();
    JTextArea yorumlar = new JTextArea();
    JTextArea puanim = new JTextArea();
    JButton yorumYap = new JButton();
    JButton geri = new JButton();
    JSlider puan = new JSlider();
    JScrollPane kaydir;
    JPanel panel_Yorumlar = new JPanel();
    JPanel panel_FilmHakkinda = new JPanel();
    JLabel resim = new JLabel();

    public FilmScreen(Film film, Kullanici user, KesfetPenceresi kesfetPenceresi){
        this.film = film; //--------
        this.user= user; //---------
        Color color = new Color(50, 50, 50);

        geri.setBounds(1390,700,60,30);
        geri.setText("Geri");
        geri.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //-----------
                frame.setVisible(false); //you can't see me!
                kesfetPenceresi.setVisible(true);
                frame.dispose(); //Destroy the JFrame object
            }
        });


        yorumYap.setBounds(1350,240,100,30);
        yorumYap.setText("Yorum Yap");
        yorumYap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //---------- buralar güncellendi
                if (user == null){
                    JOptionPane.showMessageDialog(frame.getContentPane(), "Giriş Yapmadan yorum yapamazsınız.\nLütfen üye olunuz.",
                            "Lütfen Giriş Yapınız", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    // eğer yorumda uygunsuz kelime yok ise
                    if(!yorumYeri.getText().trim().equals("")){
                        if(dosya.yorumUygunMu(new Yorum(film, yorumYeri.getText()))){
                            //yorumu dosyaya yazdır.
                            dosya.yorumYaz(user, new Yorum(film, yorumYeri.getText()));
                            yorumlariGuncelle();
                            yorumYeri.setText("");
                        }else{
                            JOptionPane.showMessageDialog(frame.getContentPane(), "Yorumunuzda uygunsuz içerik tespit edildi.\nYorumunuz yayınlanamadı.",
                                    "Uygunsuz İçerik", JOptionPane.ERROR_MESSAGE);
                            yorumYeri.setText("");
                        }
                    }else{
                        JOptionPane.showMessageDialog(frame.getContentPane(), "Boş yapıyosunuz.\nBir kaç harfe falan mı bassan acaba.",
                                "Boş Yorum", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        puanim.setText("Bu filme puanım :");
        puanim.setBounds(850,240,300,30);
        puanim.setBackground(color);
        puanim.setEditable(false);
        puanim.setFont(new Font(null,Font.PLAIN,15));
        puanim.setForeground(Color.white);

        puan.setMaximum(10);
        puan.setValue(5);;
        puan.setBounds(850,270,300,30);
        puan.setBackground(color);
        //---- puanlama için addChangeListener eklendi------
        puan.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(user != null){
                    if(!puan.getValueIsAdjusting()){
                        dosya.puanYaz(user, new Puan(film, (byte)puan.getValue()));
                        filmBilgisiGuncelle();
                        JOptionPane.showMessageDialog(frame.getContentPane(), "Bu filme notunuz: " + puan.getValue()
                                + "\nFilmi puanladığınız için teşekkürler.:)", "Paunlama Yapıldı", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame.getContentPane(), "Giriş Yapmadan puanlayamazsınız.\nLütfen üye olunuz.",
                            "Lütfen Giriş Yapınız", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        yorumYeri.setBounds(850,20,600,200);
        yorumYeri.setEditable(true);
        yorumYeri.setLineWrap(true);
        yorumYeri.setFont(new Font(null,Font.PLAIN,20));
        yorumYeri.setWrapStyleWord(true);

        yorumlar.setLineWrap(true);
        yorumlar.setWrapStyleWord(true);
        yorumlar.setBounds(850,350,600,350);
        yorumlar.setFont(new Font(null,Font.PLAIN,25));
        yorumlar.setBackground(color);
        yorumlar.setEditable(false);
        yorumlar.setForeground(Color.white);
        yorumlariGuncelle(); // --- yorumları yazıyor bu fonksiyon

        panel_Yorumlar.setBorder(new TitledBorder(new EtchedBorder(), "Display Area"));
        yorumlar.setRows(1);
        yorumlar.setColumns(10);
        yorumlar.setEditable(false);
        //yorumlar.setMaximumSize(new Dimension(1, 1));
        kaydir = new JScrollPane(yorumlar);
        panel_Yorumlar.add(kaydir);
        panel_Yorumlar.add(yorumlar);
        panel_Yorumlar.setAutoscrolls(true);
        //panel1.setMaximumSize(new Dimension(1, 1));

        filmHakkinda.setBounds(10,20,750,725);
        filmHakkinda.setFont(new Font(null,Font.PLAIN,25));
        filmHakkinda.setBackground(color);
        filmHakkinda.setForeground(Color.white);
        filmBilgisiGuncelle();
        filmHakkinda.setAutoscrolls(true);
        filmHakkinda.setLineWrap(true);
        filmHakkinda.setEditable(false);
        filmHakkinda.setWrapStyleWord(true);
        resim = new JLabel(film.getFilmResmi());
        resim.setBounds(50,20, 750, 280);
        filmHakkinda.setBounds(10, 320, 830, 425);
        panel_FilmHakkinda.add(resim);
        panel_FilmHakkinda.add(filmHakkinda);

        frame.add(panel_FilmHakkinda);
        frame.add(resim);
        frame.add(panel_Yorumlar);
        frame.add(geri);
        frame.add(puanim);
        frame.add(puan);
        frame.add(yorumYap);
        frame.add(yorumlar);
        frame.add(yorumYeri);
        frame.add(filmHakkinda);
        frame.add(kaydir);

        frame.setLayout(null);
        frame.setTitle(film.getFilmAdi() + " - " + film.getYayinYili());
        frame.getContentPane().setBackground(color);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 800);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

    }
    //----- gerekli fonksiyonlar eklendi
    private void yorumlariGuncelle(){
        if(dosya.yorumOku(film).size() == 0)
            yorumlar.setText(" Bu film hakkında herhangi bi yorum bulunmamaktadır.. :(");
        else{
            yorumlar.setText("");
            for(String yorum: dosya.yorumOku(film)){
                String[] tmp = yorum.split("-");
                if(user != null && yorum.split("-")[1].equals(user.getKullaniciAdi())){
                    yorumlar.setText(" " + yorumlar.getText() + "\n >" + tmp[0] + " | " + tmp[1] + " : " + tmp[3]);
                }else{
                    yorumlar.setText(" " + yorumlar.getText() + "\n " + tmp[0] + " | " + tmp[1] + " : " + tmp[3]);
                }
            }
        }
    }

    private void filmBilgisiGuncelle(){
        String kategori = "";
        for(String g : film.getKategori()){
            kategori += g + ".";
        }
        StringBuilder str = new StringBuilder(kategori);
        str.deleteCharAt(str.length()-1);
        kategori = str.toString();
        kategori = kategori.replace(".", " • " );

        filmHakkinda.setText(
                film.getFilmAdi() + "\n" + "Yayın Yılı : " + film.getYayinYili().replace("-",".")
                        + "\n" + film.getFilmBilgisi() +
                        "\n" + kategori + "\n" + "Film notu : " + dosya.getOrtPuan(film)
        ); //------ setText güncllendi
    }

}