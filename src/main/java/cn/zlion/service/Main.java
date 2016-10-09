package cn.zlion.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zzs on 10/8/16.
 */
public class Main {

    public static void main(String[] args){
        String url = "jdbc:postgresql://localhost:5432/znsh_cluster?searchpath=TestApp";
        String username = "dbuser";
        String password = "948926865";

        try{
            Class.forName("org.postgresql.Driver").newInstance();

            Connection conn= DriverManager.getConnection(url,username,password);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from \"TestApp\".\"Course\"");

            while (rs.next()){
                System.out.println("pk:"+rs.getString("pk")+" / pk_j:"+rs.getString("pk_j")+" / name:"+rs.getString("name"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
