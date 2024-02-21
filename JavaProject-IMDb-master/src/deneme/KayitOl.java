package deneme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class KayitOl extends Dosya {
    private JTextField kullaniciTf;
    private JTextField epostaTf;
    private JPasswordField parolaTf;
    private JPasswordField parolaTekrarTf;
    private JButton kayitOlBtn;
    private JPanel panel1;
    private JLabel kullaniciLabel;
    private JLabel epostaLabel;
    private JLabel parolaLabel;
    private JLabel parolaTekrarLabel;
    private JPanel altPanel;
    private String kullaniciAdi;
    private String sifre;
    private String sifreTekrar;
    private String eposta;
    private Kullanici kullanici = null;
    JFrame frame = new JFrame();
    private KesfetPenceresi kesfetPenceresi;

    public KayitOl(KesfetPenceresi kesfetPenceresi) {
        this.kesfetPenceresi = kesfetPenceresi;
        frame.setSize(300,400);
        frame.add(panel1);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth()-frame.getWidth())/2;
        int y = (int) (dimension.getHeight()-frame.getWidth())/2;
        frame.setLocation(x, y);
        kayitOlButton();

        frame.setTitle("Kayıt Ol");

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }


    public void kullaniciAdiAl(){ kullaniciAdi = kullaniciTf.getText(); }

    public void epostaAl(){ eposta = epostaTf.getText(); }

    public void parolaAl(){ sifre=parolaTf.getText(); }

    public void parolaTekrarAl(){ sifreTekrar=parolaTekrarTf.getText(); }

    public void kayitOlButton(){
        kayitOlBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kullaniciAdiAl();
                parolaAl();
                parolaTekrarAl();
                epostaAl();
                if (!sifre.equals(sifreTekrar)) {
                    JOptionPane.showMessageDialog(null, "Parolanız uyuşmuyor.",
                            "HATALI PAROLA", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    kullanici = new Kullanici(kullaniciAdi, sifre);
                    JOptionPane.showMessageDialog(null, "Kayıt Başarıyla Oluşturuldu.",
                            "", JOptionPane.PLAIN_MESSAGE);
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    kesfetPenceresi.setKullanici(kullanici);
                    kullaniciEkle(kullanici);
                }
            }
        });
    }
}
