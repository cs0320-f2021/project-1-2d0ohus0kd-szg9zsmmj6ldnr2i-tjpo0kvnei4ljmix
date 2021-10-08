package api.core;

import java.util.Objects;

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
}
