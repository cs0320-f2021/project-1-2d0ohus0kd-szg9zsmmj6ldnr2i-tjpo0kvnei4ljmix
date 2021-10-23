package edu.brown.cs.student.main.commands;

import api.APIenum;
import api.apiImplementation;
import api.apiInterface;
import api.core.Justuser;
import edu.brown.cs.student.main.AggregateALL;
import edu.brown.cs.student.main.Command;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;

public class APITest extends Command {

    public APITest(){

    }
    @Override
    public String run(String arg) {
        try {
            apiInterface api = new apiImplementation();
            Collection<Justuser> justusers = api.getFromJson(APIenum.USER, "data/project-1/justusersSMALL.json");
            for (Justuser justuser : justusers) {
                System.out.println(justuser);
            }
            Collection<Justuser> justusers2 = api.getFromApi(APIenum.USER);
            for (Justuser justuser : justusers2) {
                System.out.println(justuser);
            }
            AggregateALL all = new AggregateALL();
            Collection<Justuser> justusers3 = all.getAllData(APIenum.USER);
            for (Justuser justuser : justusers3) {
                System.out.println(justuser);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "test success";
    }

}
