package edu.brown.cs.student.main;

import api.APIenum;
import api.apiImplementation;
import api.apiInterface;
import api.core.Justrent;
import api.core.Justreview;
import api.core.Justuser;
import orm.ORM;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AggregateALL {
    public Collection getAllData(APIenum field) throws SQLException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        apiInterface api = new apiImplementation();
        ORM orm = new ORM("data/project-1/runwaySMALL.sqlite3");

        switch (field) {
            case RENT:
                Collection<Justrent> justrents = api.getFromApi(APIenum.RENT);
                List<Justrent> results1 = new ArrayList<Justrent>();
                Collection<Justrent> list1 = orm.getData(APIenum.RENT);
                for (Justrent ele : list1) {
                    if (!results1.contains(ele)) {
                        results1.add(ele);
                    }
                }
                for (Justrent ele : justrents) {
                    if (!results1.contains(ele)) {
                        results1.add(ele);
                    }
                }
                return results1;
            case USER:
//                Collection<Justuser> justusers = api.getFromJson(APIenum.USER, "data/project-1/justusersSMALL.json");
                Collection<Justuser> justusers = api.getFromApi(APIenum.USER);
                List<Justuser> results2 = new ArrayList<Justuser>();
                Collection<Justuser> list2 = orm.getData(APIenum.USER);
                for (Justuser ele : list2) {
                    if (!results2.contains(ele)) {
                        results2.add(ele);
                    }
                }
                for (Justuser ele : justusers) {
                    if (!results2.contains(ele)) {
                        results2.add(ele);
                    }
                }
                return results2;
            case REVIEW:
                Collection<Justreview> justreviews = api.getFromApi(APIenum.REVIEW);
                List<Justreview> results3 = new ArrayList<Justreview>();
                Collection<Justreview> list3 = orm.getData(APIenum.REVIEW);
                for (Justreview ele : list3) {
                    if (!results3.contains(ele)) {
                        results3.add(ele);
                    }
                }
                for (Justreview ele : justreviews) {
                    if (!results3.contains(ele)) {
                        results3.add(ele);
                    }
                }
                return results3;
            default:
                throw new IllegalArgumentException("Not a valid field");
        }

    }
}
