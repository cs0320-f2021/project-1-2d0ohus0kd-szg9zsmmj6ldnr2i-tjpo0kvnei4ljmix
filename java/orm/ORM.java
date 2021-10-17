package orm;

import api.APIenum;
import api.core.Justrent;
import api.core.Justreview;
import api.core.Justuser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ORM {
    Database database;

    public ORM(String db) throws SQLException, ClassNotFoundException {
        database = new Database(db);
    }

    public Collection getData(APIenum field) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class clazz = null;
        ResultSet rs = null;
        switch (field) {
            case RENT:
                clazz = Justrent.class;
                rs = database.sql("SELECT * FROM rent;");
                break;
            case USER:
                clazz = Justuser.class;
                rs = database.sql("SELECT * FROM users;");
                break;
            case REVIEW:
                clazz = Justreview.class;
                rs = database.sql("SELECT * FROM reviews;");
                break;
            default:
                throw new IllegalArgumentException("Not a valid field");
        }

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        List list = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        while (rs.next()) {
            Object obj = clazz.getDeclaredConstructor().newInstance();//构造业务对象实体
            for (int i = 1; i <= colCount; i++) {
                Object value = rs.getObject(i);
                for (int j = 0; j < fields.length; j++) {
                    Field f = fields[j];
                    if (f.getName().equalsIgnoreCase(rsmd.getColumnName(i))) {
                        boolean flag = f.canAccess(obj);
                        f.setAccessible(true);
                        if (f.getName().equals("user_id") || f.getName().equals("age") || f.getName().equals("rating") || f.getName().equals("size")) {
                            f.set(obj, Integer.parseInt(value.toString()));
                        } else
                            f.set(obj, value);
                        f.setAccessible(flag);
                    }
                }
            }
            list.add(obj);
        }
        return list;
    }
}
