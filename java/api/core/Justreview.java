package api.core;

import java.util.Objects;

public class Justreview {
    private String review_text;
    private String review_summary;
    private String review_data;
    private int id;

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

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getReview_summary() {
        return review_summary;
    }

    public void setReview_summary(String review_summary) {
        this.review_summary = review_summary;
    }

    public String getReview_data() {
        return review_data;
    }

    public void setReview_data(String review_data) {
        this.review_data = review_data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
