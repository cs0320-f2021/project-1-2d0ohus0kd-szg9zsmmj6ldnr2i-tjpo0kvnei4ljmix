package api.project1;

public class Justrent {
    String fit;
    String user_id;
    String item_id;
    int rating;
    String rented_for;
    String category;
    int size;
    int id;

    @Override
    public String toString() {
        return "Justrent{" +
                "fit='" + fit + '\'' +
                ", user_id='" + user_id + '\'' +
                ", item_id='" + item_id + '\'' +
                ", rating=" + rating +
                ", rented_for='" + rented_for + '\'' +
                ", category='" + category + '\'' +
                ", size=" + size +
                ", id=" + id +
                '}';
    }
}
