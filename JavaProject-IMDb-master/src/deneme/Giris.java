package deneme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Giris {
    private JTextField kullaniciAdiGirTf;
    private JButton girisYapButton;
    private JPanel panel1;
    private JPasswordField parolaGirTf;
    private JLabel sifreLabel;
    JFrame frame = new JFrame();
    private KesfetPenceresi kesfetPenceresi;
    private Kullanici kullanici = null;

    public Giris(KesfetPenceresi kesfetPenceresi){
        this.kesfetPenceresi = kesfetPenceresi;
        frame.setSize(250,290);
        frame.add(panel1);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (dimension.getWidth()-frame.getWidth())/2;
        int y = (int) (dimension.getHeight()-frame.getWidth())/2;
        frame.setLocation(x, y);
        girisButton();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
    public void girisButton(){
        girisYapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                kullanici = new Kullanici(kullaniciAdiGirTf.getText() ,parolaGirTf.getText());
                if(new Dosya().kullaniciKontrol(kullanici) == 1){
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    kesfetPenceresi.setKullanici(kullanici);
                }else {
                    JOptionPane.showMessageDialog(null, "Hatalı Bilgi girdiniz.\nLütfen tekrar deneyiniz.",
                            "Hatalı Giriş", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}


