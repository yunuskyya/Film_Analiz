package deneme;

import org.json.JSONArray;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class KesfetPenceresi {
    private JPanel panel1;
    private JRadioButton yetiskinIcerigiGizleRadioButton;
    private JRadioButton populariteRadioButton;
    private JRadioButton hasilatRadioButton;
    private JScrollPane scrollPane;
    private JLabel aksiyonLabel;
    private JLabel maceraLabel;
    private JLabel animasyonLabel;
    private JLabel komediLabel;
    private JLabel sucLabel;
    private JLabel belgeselLabel;
    private JLabel tarihLabel;
    private JLabel korkuLabel;
    private JLabel muzikLabel;
    private JLabel gizemLabel;
    private JLabel romantikLabel;
    private JLabel bilimkurguLabel;
    private JLabel gerilimLabel;
    private JLabel savasLabel;
    private JLabel batiliLabel;
    private JLabel dramaLabel;
    private JLabel aileLabel;
    private JLabel fantastikLabel;
    private JLabel kesfetLabel;
    private JTextField tarihAyarla;
    private JPanel rightPanel;
    private JButton araKucuk;
    private JButton araEsit;
    private JButton araBuyuk;
    private JLabel sayfaSayisi;
    private JTextField sayfaSimdiki;
    private JLabel birSayfaArtır;
    private JLabel birSayfaAzalt;
    private JTextField rightBottomFlickInfoTF;
    private JTextField searchTF;
    private JButton button1;
    private JPanel scrollPanePanel;
    private JPanel leftPanel;
    private JPanel rightTopPanel;
    private JPanel rightBottomPanel;
    private JLabel girisYapLabel;
    private JLabel kayitOlLabel;
    private final JPanel filmPaneli = new JPanel(new GridLayout(0, 4, 10, 10));
    private final JPanel contentPane = new JPanel();
    private final TheMovieDb theMovieDb = new TheMovieDb();
    private int siralama = 0, tur = 0, yil;
    private boolean includeAdult = true;
    private final JLabel[] kesfetLabels = new JLabel[] {kesfetLabel, aksiyonLabel, maceraLabel, komediLabel, sucLabel, belgeselLabel,
    animasyonLabel, dramaLabel, aileLabel, fantastikLabel, tarihLabel, korkuLabel, muzikLabel, gizemLabel, romantikLabel,
    bilimkurguLabel, gerilimLabel, savasLabel, batiliLabel};
    private String currentLink =
            "https://api.themoviedb.org/3/discover/movie?api_key=2f83aa9f8c12d7b99fb65e52dc811b6a&language=tr";
    private JLabel currentLabel;
    private int sayfa = 1;
    private Boolean sonAramaTarihOnce = null;
    private String query = null;
    private Kullanici kullanici;
    JFrame frame = new JFrame();


    public KesfetPenceresi(Kullanici kullanici) {
        this.kullanici = kullanici;
        frame.add(panel1);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setTitle("İSİMSİZ");

        panel1.setFocusable(true);
        rightBottomFlickInfoTF.setBorder(BorderFactory.createEmptyBorder());

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        populariteRadioButton.setSelected(true);

        currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa);
        filmPaneli.setBackground(new Color(50,50,50));
        filmPaneli.setForeground(new Color(50,50,50));
        searchField();
        filmPaneliLayers();
        scrollPaneSettings();
        solPanelItemleri();
        filmPanelOtomasyon();
        filterWRadioButton();
        yilaGoreArama();
        tarihTextArea();
        sayfaDegis();
        araFonksiyonu();
        kullaniciyaGec();
        kullaniciKontrol();

        Dimension dimension = new Dimension(900,550);
        frame.setMinimumSize(dimension);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < 19; i++) {
            kesfetListeners(kesfetLabels[i], i);
        }

    }

    public void kullaniciKontrol() {
        if (kullanici != null) {
            kayitOlLabel.setVisible(false);
            girisYapLabel.setText(kullanici.getKullaniciAdi());
        }
        else {
            kayitOlLabel.setVisible(true);
            girisYapLabel.setText("Giris Yap");
        }
    }

    private void kullaniciyaGec() {
        cursorSettings(girisYapLabel);
        cursorSettings(kayitOlLabel);
        girisYapLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (kullanici == null) {
                    new Giris(Main.getKesfetPenceresi());
                }
                else {
                    kullanici = null;
                    kullaniciKontrol();
                }
            }
        });
        kayitOlLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new KayitOl(Main.getKesfetPenceresi());
                kullaniciKontrol();
            }
        });
    }

    /**
     * Adı üstünde.
     */
    protected void sayfaDegis() {
        sayfaSayisi.setForeground(new Color(187,187,187));
        sayfaSayisi.setText(String.valueOf(theMovieDb.sayfaSayisi(currentLink,false)));
        sayfaSimdiki.setText(String.valueOf(theMovieDb.sayfaSayisi(currentLink,true)));
        cursorSettings(sayfaSimdiki);
        sayfaSimdiki.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (Integer.parseInt(sayfaSimdiki.getText()) > 500) {
                        JOptionPane.showMessageDialog(null, "Sayfa sayısı maksimum  500 olmalı.",
                                "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                    } else if (Integer.parseInt(sayfaSimdiki.getText()) < 1) {
                        JOptionPane.showMessageDialog(null, "1'den daha düşük sayı giremezsin.",
                                "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        sayfa = Integer.parseInt(sayfaSimdiki.getText());
                       refreshFilmPaneli();
                    }
                }
            }
        });
        birSayfaAzalt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sayfa > 1) {
                    sayfa -= 1;
                    refreshFilmPaneli();
                }
            }

        });
        birSayfaArtır.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sayfa < 500) {
                    sayfa += 1;
                    refreshFilmPaneli();
                }
            }
        });
    }

    /**
     * Radyo Butonlarına işlev kazandırır.
     */
    protected void filterWRadioButton() {
        cursorSetting(populariteRadioButton);
        cursorSetting(hasilatRadioButton);
        cursorSetting(yetiskinIcerigiGizleRadioButton);

        populariteRadioButton.setSelected(true);
        populariteRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siralama = 0;
                refreshFilmPaneli();
                populariteRadioButton.setForeground(new Color(255,193,7));
                hasilatRadioButton.setForeground(new Color(255,255,255));

            }
        });
        hasilatRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                siralama = 1;
                refreshFilmPaneli();
                hasilatRadioButton.setForeground(new Color(255,193,7));
                populariteRadioButton.setForeground(new Color(255,255,255));
            }
        });
        yetiskinIcerigiGizleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                includeAdult = !includeAdult;
                refreshFilmPaneli();
                if (yetiskinIcerigiGizleRadioButton.isSelected()) {
                    yetiskinIcerigiGizleRadioButton.setForeground(new Color(255,193,7));
                }
                else {
                    yetiskinIcerigiGizleRadioButton.setForeground(new Color(255,255,255));
                }
            }
        });
    }

    // Tür ScrollPane Ayarları
    protected void scrollPaneSettings() {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setBackground(new Color(60, 60, 60));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(222, 222, 222, 181);
            }
            @Override
            protected JButton createIncreaseButton(int orientation)  {
                return createZeroButton();
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }
        });
    }

    // ScrollBar butonlarını kaldırır.
    protected JButton createZeroButton() {
        JButton button = new JButton("zero button");
        Dimension zeroDim = new Dimension(0,0);
        button.setPreferredSize(zeroDim);
        button.setMinimumSize(zeroDim);
        button.setMaximumSize(zeroDim);
        return button;
    }

    /**
     * Keşfet bölümüne bir takım görsel efektler ekler.
     */
    protected Font d = new Font("JetBrain MONO", Font.BOLD, 14);
    protected Font f = new Font("JetBrain MONO", Font.BOLD, 16);
    private void filmTuruListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setFont(f);
                label.setForeground(new Color(255, 193, 7));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setFont(d);
                label.setForeground(new Color(255, 255, 255));
            }
        });
    }
    protected Font kE = new Font("JetBrain MONO", Font.BOLD, 14);
    protected Font kY = new Font("JetBrain MONO", Font.BOLD, 16);
    private void kesfetListener(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
                label.setFont(kY);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                label.setFont(kE);
            }
        });
    }



    /**
     * Sol paneldeki film türlerine işlev kazandırır.
     */
    public void kesfetListeners(JLabel label, int i) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (query == null) {
                    if (currentLabel != null) {
                        currentLabel.setOpaque(false);
                        currentLabel.setBorder(BorderFactory.createEmptyBorder());
                    }
                    currentLabel = label;
                    if (currentLabel != kesfetLabel){
                        label.setBackground(new Color(220, 53, 69));
                        label.setOpaque(true);
                    }
                    sayfa = 1;
                    tur = i;
                    refreshFilmPaneli();
                }
            }
        });
    }

    public void solPanelItemleri() {
        kesfetListener(kesfetLabel);
        for (int i = 1; i < 19; i++) {
            filmTuruListener(kesfetLabels[i]);
        }
    }

    /**
     * Film panelini yenilenmesi için gerekli fonksiyonları belirli kriterlere göre çağırır.
     */

    public void refreshFilmPaneli() {
        if (sonAramaTarihOnce == null && query == null) {
            currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa);
        }
        else if (query == null){
            currentLink = theMovieDb.linkGenerator(includeAdult, siralama, tur, yil, sayfa, sonAramaTarihOnce);
        }
        else if (sonAramaTarihOnce == null && query != null) {
            currentLink = theMovieDb.araLinkGenerator(query, includeAdult, yil, sayfa);
        }
        else {
            currentLink = theMovieDb.araLinkGenerator(query, includeAdult, yil, sayfa, sonAramaTarihOnce);
        }
        sayfaSimdiki.setText(String.valueOf(sayfa));
        filmPaneli.removeAll();
        filmPanelOtomasyon();
    }

    /**
     * Arama kutusu ve arama butonuna işlev kazandırır.
     */
    public void araFonksiyonu() {
        cursorSettings(searchTF);
        searchTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    if (searchTF.getText().isBlank()) {
                        query = null;
                        refreshFilmPaneli();
                    }
                    else {
                        query = searchTF.getText();
                        refreshFilmPaneli();
                    }
                }
            }
        });
        button1.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchTF.getText().equals("Ara")) {
                    query = null;
                    refreshFilmPaneli();
                }
                else {
                    query = searchTF.getText();
                    refreshFilmPaneli();
                }
            }
        });
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void cursorSettings(JTextField tf) {
        tf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tf.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tf.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void cursorSettings(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void cursorSetting(JRadioButton radioButton) {
        radioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                radioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                radioButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }


    /**
     * Arama kutusunun içine ipucu metni yerleştirir.
     */
    public void searchField() {
        searchTF.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 254, 163),2),
                new EmptyBorder(0, 6, 0, 0)));

        searchTF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                query = null;
                searchTF.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTF.getText().isBlank())
                    searchTF.setText("Ara");
            }
        });
    }

    /**
     * Sağ paneldeki filmlerin güncel bağlantıya göre yenilenmesini sağlar.
     */
    public void filmPanelOtomasyon() {
        new Thread(() -> {
            System.out.println(currentLink);
            int panelSayisi = new TheMovieDb().kesfetListeUzunlugu(currentLink);
            JSONArray movies = new TheMovieDb().kesfetFilmListesi(currentLink);
            for (int i = 0; i < panelSayisi; i++) {

                String currentImage = new TheMovieDb().findImage(movies, i);
                if (currentImage == null) continue;
                JLabel posterImage = new JLabel(new ImageIcon(new TheMovieDb().imgParser(currentImage, true)));
                posterImage.setBorder(new EmptyBorder(8,0,0,0));
                JPanel panel = new JPanel(new BorderLayout());
                JLabel movieTitle = new JLabel(new TheMovieDb().findTitle(movies, i));
                String originalTitle = new TheMovieDb().findOriginalTitle(movies, i);
                panel.setBorder(BorderFactory.createMatteBorder(2,2,2,2, new Color(255, 193, 7)));
                panel.setBackground(new Color(96, 96, 96));

                int id = new TheMovieDb().findId(movies, i);
                String filmTitle = new TheMovieDb().filmTitle(movies, i);
                ArrayList<String> genres = new TheMovieDb().genres(movies, i);
                String overview = new TheMovieDb().findOverview(movies, i);
                String releaseDate = new TheMovieDb().findReleaseDate(movies, i);
                ImageIcon filmImage = new TheMovieDb().backdropPic(movies, i);
                Film film = new Film(id, filmTitle, genres, overview, releaseDate, filmImage);
                makePanelsClickable(panel, originalTitle, film);

                movieTitle.setFont(f);
                movieTitle.setForeground(new Color(255, 255, 255));
                movieTitle.setHorizontalAlignment(JLabel.CENTER);
                movieTitle.setVerticalAlignment(JLabel.CENTER);

                Fader fader = new Fader( new Color(30,30,30), 10, 15 );
                fader.add(panel);
                panel.add(posterImage, BorderLayout.NORTH);
                panel.add(movieTitle, BorderLayout.CENTER);
                filmPaneli.add(panel);
                filmPaneli.revalidate();
                filmPaneli.repaint();
            }
        }).start();
    }

    public void makePanelsClickable(JPanel panel, String originalTitle, Film film) {
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setVisible(false);
                new FilmScreen(film, kullanici, Main.getKesfetPenceresi());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                rightBottomFlickInfoTF.setText("Orijinal Başlık: \"" + originalTitle + "\"");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rightBottomFlickInfoTF.setText("");

            }
        });
    }

    public void filmPaneliLayers() {
        JPanel holderPanel = new JPanel(new BorderLayout());
        holderPanel.add(filmPaneli, BorderLayout.NORTH);
        holderPanel.add(Box.createGlue(), BorderLayout.CENTER);
        filmPaneli.setBackground(new Color(56, 56, 58, 255));
        JScrollPane filmScrollPane = new JScrollPane(holderPanel);
        filmScrollPane.setBorder(BorderFactory.createEmptyBorder());
        filmScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        contentPane.setLayout(new BorderLayout());
        contentPane.add(filmScrollPane, BorderLayout.CENTER);
        rightPanel.add(contentPane, BorderLayout.CENTER);
    }

    public void yilaGoreArama() {
        Border empty = new EmptyBorder(0, 6, 0, 0);
        araEsit.setBorder(BorderFactory.createEmptyBorder());
        araBuyuk.setBorder(BorderFactory.createEmptyBorder());
        araKucuk.setBorder(BorderFactory.createEmptyBorder());

        araEsit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        sayfa = 1;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        sayfa = 1;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                    }
                    sonAramaTarihOnce = null;
                    refreshFilmPaneli();
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        araBuyuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        sayfa = 1;
                        sonAramaTarihOnce = null;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                        refreshFilmPaneli();
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        sayfa = 1;
                        sonAramaTarihOnce = false;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                        refreshFilmPaneli();
                    }
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        araKucuk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tarihAyarla.getText().isBlank()) {
                        yil = 0;
                        sayfa = 1;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.white),empty));
                        refreshFilmPaneli();
                    }
                    else {
                        yil = Integer.parseInt(tarihAyarla.getText());
                        sayfa = 1;
                        sonAramaTarihOnce = true;
                        tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.green),empty));
                        refreshFilmPaneli();
                    }
                } catch (Exception exception) {
                    tarihAyarla.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.red),empty));
                    JOptionPane.showMessageDialog(null, "Yalnızca rakam kullanarak arama yapabilirsiniz.",
                            "HATALI ARAMA", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    public void tarihTextArea() {
        cursorSettings(tarihAyarla);
        tarihAyarla.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                String tarih = tarihAyarla.getText();
                if (tarih.length() > 3) {
                    tarihAyarla.setText(tarih.substring(0, tarih.length() - 1));
                }
            }
        });
    }

    public void setKullanici(Kullanici kullanici) {
        this.kullanici = kullanici;
        kullaniciKontrol();
    }

    public void setVisible(boolean w) {
        frame.setVisible(w);
    }
}
