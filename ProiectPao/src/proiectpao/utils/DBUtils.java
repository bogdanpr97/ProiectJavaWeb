package proiectpao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proiectpao.beans.*;

public class DBUtils {
 
    public static User findUser(Connection conn, //
            String userName, String password) throws SQLException {
 
        String sql = "Select * from users where username = ? and password= ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }
 
    public static User findUser(Connection conn, String userName) throws SQLException {
 
        String sql = "select * from users where username = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("password");
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            return user;
        }
        return null;
    }
 
    public static List<Produs> queryProdus(Connection conn) throws SQLException {
        String sql = "select * from produse;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        ResultSet rs = pstm.executeQuery();
        List<Produs> list = new ArrayList<Produs>();
        while (rs.next()) {
            String code = rs.getString("cod");
            String name = rs.getString("nume");
            float price = rs.getFloat("pret");
            Produs Produs = new Produs();
            Produs.setCode(code);
            Produs.setName(name);
            Produs.setPrice(price);
            list.add(Produs);
        }
        return list;
    }
 
    public static Produs findProdus(Connection conn, String code) throws SQLException {
        String sql = "Select * from produse where cod = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, code);
 
        ResultSet rs = pstm.executeQuery();
 
        while (rs.next()) {
            String name = rs.getString("nume");
            float price = rs.getFloat("pret");
            Produs Produs = new Produs(code, name, price);
            return Produs;
        }
        return null;
    }
 
    public static void updateProdus(Connection conn, Produs Produs) throws SQLException {
        String sql = "update produse set nume = ? , pret = ? where cod = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, Produs.getName());
        pstm.setFloat(2, Produs.getPrice());
        pstm.setString(3, Produs.getCode());
        pstm.executeUpdate();
    }
 
    public static void insertProdus(Connection conn, Produs Produs) throws SQLException {
        String sql = "insert into produse(cod, nume, pret) values (? , ? , ?);";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, Produs.getCode());
        pstm.setString(2, Produs.getName());
        pstm.setFloat(3, Produs.getPrice());
 
        pstm.executeUpdate();
    }
 
    public static void deleteProdus(Connection conn, String code) throws SQLException {
        String sql = "delete From produse where cod = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, code);
 
        pstm.executeUpdate();
    }
 
}