package deneme;

public class Main {
    private static KesfetPenceresi kesfetPenceresi;

    public static void main(String[] args) {
        kesfetPenceresi = new KesfetPenceresi(null);

    }
    public static KesfetPenceresi getKesfetPenceresi() {
        return kesfetPenceresi;
    }

}


