package api.core;

import java.util.Objects;

public class Justrent {
    private String fit;
    private String user_id;
    private String item_id;
    private int rating;
    private String rented_for;
    private String category;
    private int size;
    private int id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Justrent justrent = (Justrent) o;
        return rating == justrent.rating && size == justrent.size && id == justrent.id &&
            Objects.equals(fit, justrent.fit) &&
            Objects.equals(user_id, justrent.user_id) &&
            Objects.equals(item_id, justrent.item_id) &&
            Objects.equals(rented_for, justrent.rented_for) &&
            Objects.equals(category, justrent.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fit, user_id, item_id, rating, rented_for, category, size, id);
    }

    public String getFit() {
        return fit;
    }

    public void setFit(String fit) {
        this.fit = fit;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRented_for() {
        return rented_for;
    }

    public void setRented_for(String rented_for) {
        this.rented_for = rented_for;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
