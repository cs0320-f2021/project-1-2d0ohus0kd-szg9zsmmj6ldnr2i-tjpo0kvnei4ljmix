package edu.brown.cs.student.main.commands;


import api.APIenum;
import api.core.Justrent;
import api.core.Justreview;
import api.core.Justuser;
import edu.brown.cs.student.main.Command;
import orm.ORM;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;


public class ORMTest extends Command {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {



    }

    @Override
    public String run(String argString) {
        try {
            ORM orm = new ORM("data/project-1/runwaySMALL.sqlite3");
            Collection<Justuser> justusers = orm.getData(APIenum.USER);
            for (Justuser justuser : justusers) {
                System.out.println(justuser);
            }
            Collection<Justrent> justrents = orm.getData(APIenum.RENT);
            for (Justrent justrent : justrents) {
                System.out.println(justrent);
            }
            Collection<Justreview> justreviews = orm.getData(APIenum.REVIEW);
            for (Justreview review : justreviews) {
                System.out.println(review);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "test success";
    }
}
