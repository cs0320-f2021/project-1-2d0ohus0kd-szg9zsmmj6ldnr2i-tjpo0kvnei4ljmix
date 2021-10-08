package api.core;

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
}
