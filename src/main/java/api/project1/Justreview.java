package api.project1;

public class Justreview {
    String review_text;
    String review_summary;
    String review_data;
    int id;

    @Override
    public String toString() {
        return "justreview{" +
                "review_text='" + review_text + '\'' +
                ", review_summary='" + review_summary + '\'' +
                ", review_data='" + review_data + '\'' +
                ", id=" + id +
                '}';
    }
}
