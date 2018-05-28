package proiectpao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proiectpao.beans.Produs;
import proiectpao.beans.Serviciu;
import proiectpao.beans.User;

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
            user.setPrivilege(rs.getInt("privilegiu"));
            user.setDisabled(rs.getString("blocat"));
            user.setEmail("email");
            return user;
        }
        return null;
    }
    
    public static void changePassowrd(Connection conn, User user, String parolaNoua) throws SQLException {
    	user.setPassword(parolaNoua);
    	String sql = "update users set password = ? where username = ? ;";
    	
    	try(PreparedStatement pstm = conn.prepareStatement(sql)) {
    		pstm.setString(1, parolaNoua);
    		pstm.setString(2, user.getUserName());
    		pstm.executeUpdate();
    	}
    }
    
    public static int statusUser(Connection conn, String username) throws SQLException {
    	String sql = "select blocat from users where username = ? ;";
    	
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setString(1, username);
    		try(ResultSet rs = pstm.executeQuery();) {
    			rs.next();
        		int status = Integer.parseInt(rs.getString("blocat"));
        		return status;
    		}
    	}
    }
    
    public static void blockUser(Connection conn, String username) throws SQLException {
    	String sql = "update users set blocat = '1' where username = ? ;";
    	
    	try (PreparedStatement pstm = conn.prepareStatement(sql)) {
    		pstm.setString(1, username);
    		pstm.executeUpdate();
    	}
    }
    
    public static void unblockUser(Connection conn, String username) throws SQLException {
    	String sql = "update users set blocat = '0' where username = ? ;";
    	
    	try (PreparedStatement pstm = conn.prepareStatement(sql)) {
    		pstm.setString(1, username);
    		pstm.executeUpdate();
    	}
    }
    
    public static void insertUser(Connection conn, String userName, String password, String email) throws SQLException {
    	String sql = "insert into users (username, password, email) values ( ? , ? , ? );";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        pstm.setString(3, email);
        pstm.executeUpdate();
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
            user.setPrivilege(rs.getInt("privilegiu"));
            user.setDisabled(rs.getString("blocat"));
            user.setEmail("email");
            return user;
        }
        return null;
    }
 
    public static User findEmail(Connection conn, String email) throws SQLException {
    	 
        String sql = "select * from users where email = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, email);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("password");
            User user = new User();
            user.setUserName(rs.getString("email"));
            user.setPassword(password);
            user.setPrivilege(rs.getInt("privilegiu"));
            user.setDisabled(rs.getString("blocat"));
            user.setEmail("email");
            return user;
        }
        return null;
    }
    
    public static List<Serviciu> queryServicii(Connection conn) throws SQLException {
    	String sql = "select * from servicii;";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	ResultSet rs = pstm.executeQuery();
    	List<Serviciu> list = new ArrayList<Serviciu>();
    	while(rs.next()) {
    		Serviciu Serviciu = new Serviciu();
    		Serviciu.setId(rs.getInt("id"));
    		Serviciu.setName(rs.getString("nume"));
    		list.add(Serviciu);
    	}
    	
    	return list;
    }
    
    private static List<Serviciu> queryServiciu(Connection conn, Produs produs) throws SQLException {
    	String sql = "select id_serviciu from corespondenta_p_s where id_produs = ? ;";
    	
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, produs.getId());
        ResultSet rs = pstm.executeQuery();
        List<Serviciu> list = new ArrayList<Serviciu>();
        while (rs.next()) {
        	String sql2 = "select * from servicii where id = ? ;";
        	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            pstm2.setInt(1, rs.getInt("id_serviciu"));
            ResultSet rs2 = pstm2.executeQuery();
            rs2.next();
            String name = rs2.getString("nume");
            Serviciu serviciu = new Serviciu();
            serviciu.setName(name);
            serviciu.setId(rs.getInt("id_serviciu"));
            list.add(serviciu);
        }
        return list;
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
            int quantity = rs.getInt("cantitate");
            int id = rs.getInt("id");
            Produs Produs = new Produs();
            Produs.setCode(code);
            Produs.setName(name);
            Produs.setPrice(price);
            Produs.setId(id);
            Produs.setQuantity(quantity);
            Produs.setListaServicii(DBUtils.queryServiciu(conn, Produs));
            list.add(Produs);
        }
        return list;
    }
 
    public static String findServiciuNume(Connection conn, int id) throws SQLException {
    	String sql = "select * from servicii where id = ? ;";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	
    	pstm.setInt(1, id);
    	ResultSet rs = pstm.executeQuery();
    	if(rs.next()) {
    		String name = rs.getString("nume");
    		return name;
    	} else {
    		return "";
    	}
    }
    
    public static Produs findProdus(Connection conn, String code) throws SQLException {
        String sql = "Select * from produse where cod = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, code);
 
        ResultSet rs = pstm.executeQuery();
 
        while (rs.next()) {
            String name = rs.getString("nume");
            float price = rs.getFloat("pret");
            int id = rs.getInt("id");
            int quantity = rs.getInt("cantitate");
            Produs Produs = new Produs(code, name, price, quantity);
            Produs.setId(id);
            Produs.setListaServicii(DBUtils.queryServiciu(conn, Produs));
            return Produs;
        }
        return null;
    }
 
    public static Produs findProdusId(Connection conn, int id) throws SQLException {
        String sql = "Select * from produse where id = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
 
        ResultSet rs = pstm.executeQuery();
 
        while (rs.next()) {
            String name = rs.getString("nume");
            float price = rs.getFloat("pret");
            String code = rs.getString("cod");
            int quantity = rs.getInt("cantitate");
            Produs Produs = new Produs(code, name, price, quantity);
            Produs.setId(id);
            Produs.setListaServicii(DBUtils.queryServiciu(conn, Produs));
            return Produs;
        }
        return null;
    }
    
    public static void updateProdus(Connection conn, Produs Produs) throws SQLException {
        String sql = "update produse set nume = ? , pret = ? , cantitate = ? where cod = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setString(1, Produs.getName());
        pstm.setFloat(2, Produs.getPrice());
        pstm.setInt(3, Produs.getQuantity());
        pstm.setString(4, Produs.getCode());
        pstm.executeUpdate();
    }
    
    public static void insertServiciu(Connection conn, Serviciu Serviciu) throws SQLException {
    	String sql = "insert into servicii (nume) values ( ? );";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	
    	pstm.setString(1, Serviciu.getName());
    	
    	pstm.executeUpdate();
    }
    
    public static void insertProdus(Connection conn, Produs Produs) throws SQLException {
        String sql = "insert into produse(cod, nume, pret, cantitate) values (? , ? , ? , ?);";
 
        PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
 
        pstm.setString(1, Produs.getCode());
        pstm.setString(2, Produs.getName());
        pstm.setFloat(3, Produs.getPrice());
        pstm.setInt(4, Produs.getQuantity());

        pstm.executeUpdate();
        ResultSet rs = pstm.getGeneratedKeys();
        rs.next();
        
        String sql2 = "insert into corespondenta_p_s(id_produs , id_serviciu) values ( ? , ? );";
        
        PreparedStatement pstm2 = conn.prepareStatement(sql2);
        pstm2.setInt(1, rs.getInt(1));
        for(int i=0; i<Produs.getListaServicii().size(); i++) {
        	pstm2.setInt(2, Produs.getListaServicii().get(i).getId());
        	pstm2.executeUpdate();
        }
    }
 
    public static void deleteProdus(Connection conn, int id) throws SQLException {
       
        String sql2 = "delete from corespondenta_p_s where id_produs = ? ;";
        
        PreparedStatement pstm2 = conn.prepareStatement(sql2);
        
        pstm2.setInt(1, id);
        
        pstm2.executeUpdate();
        
        String sql = "delete from produse where id = ? ;";
        
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setInt(1, id);
 
        pstm.executeUpdate();
    }
    
    public static void deleteServiciu(Connection conn, int id) throws SQLException {
    	String sql2 = "delete from corespondenta_p_s where id_serviciu = ? ;";
        
        PreparedStatement pstm2 = conn.prepareStatement(sql2);
        
        pstm2.setInt(1, id);
        
        pstm2.executeUpdate();
        
        String sql = "delete from servicii where id = ? ;";
        
        PreparedStatement pstm = conn.prepareStatement(sql);
 
        pstm.setInt(1, id);
 
        pstm.executeUpdate();
    }
    
}