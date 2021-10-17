package api.core;

import java.util.Objects;

public class Justuser {
    private int user_id;
    private String weight;
    private String bust_size;
    private String height;
    private int age;
    private String body_type;
    private String horoscope;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBust_size() {
        return bust_size;
    }

    public void setBust_size(String bust_size) {
        this.bust_size = bust_size;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }
}
