package api.core;

import java.util.Objects;

public class Justuser {
    String user_id;
    String weight;
    String bust_size;
    String height;
    int age;
    String body_type;
    String horoscope;

    @Override
    public String toString() {
        return "Justuser{" +
                "user_id='" + user_id + '\'' +
                ", weight='" + weight + '\'' +
                ", bust_size='" + bust_size + '\'' +
                ", height='" + height + '\'' +
                ", age=" + age +
                ", body_type='" + body_type + '\'' +
                ", horoscope='" + horoscope + '\'' +
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
        Justuser justuser = (Justuser) o;
        return age == justuser.age && Objects.equals(user_id, justuser.user_id) &&
            Objects.equals(weight, justuser.weight) &&
            Objects.equals(bust_size, justuser.bust_size) &&
            Objects.equals(height, justuser.height) &&
            Objects.equals(body_type, justuser.body_type) &&
            Objects.equals(horoscope, justuser.horoscope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, weight, bust_size, height, age, body_type, horoscope);
    }
}
