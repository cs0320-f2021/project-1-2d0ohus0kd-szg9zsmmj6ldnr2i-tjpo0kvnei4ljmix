package api.core;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Justreview that = (Justreview) o;
        return id == that.id && Objects.equals(review_text, that.review_text) &&
            Objects.equals(review_summary, that.review_summary) &&
            Objects.equals(review_data, that.review_data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(review_text, review_summary, review_data, id);
    }
}
