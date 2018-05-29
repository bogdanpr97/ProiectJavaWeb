package proiectpao.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import proiectpao.beans.Comanda;
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
    
    public static void finalizeOrder(Connection conn, String username, int pid, int sid, String nume,  String prenume, 
    		String telefon, String judet, String localitate, String strada, String bloc, String apartament, String fileName) throws SQLException {
    	
    	String sql = "select id from users where username = ? ;";
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setString(1, username);
    		try(ResultSet rs = pstm.executeQuery();) {
    			rs.next();
    			int uid = rs.getInt("id");
    			String sql2 = "insert into comenzi (user_id) values ( ? );";
    			try(PreparedStatement pstm2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);) {
    				pstm2.setInt(1, uid);
    				pstm2.executeUpdate();
    				ResultSet rs2 = pstm2.getGeneratedKeys();
    		        rs2.next();
    		        int cid = rs2.getInt(1);
    		        Produs produs = DBUtils.findProdusId(conn, pid);
    		        int pret = Math.round(produs.getPrice());
    				String sql3 = "insert into comenzi_detalii (id_comanda, id_produs, id_serviciu, pret, nume_poza) values ( ? , ? , ? , ? , ? )";
    				try(PreparedStatement pstm3 = conn.prepareStatement(sql3);) {
    					pstm3.setInt(1, cid);
    					pstm3.setInt(2, pid);
    					pstm3.setInt(3, sid);
    					pstm3.setInt(4, pret);
    					pstm3.setString(5, fileName);
    					pstm3.executeUpdate();
    					String sql4 = "insert into date_utilizator_comanda (comanda_id, u_id, nume, prenume, telefon, judet, localitate, strada, bloc, apartament)"
    							+ " values ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? );";
    					try(PreparedStatement pstm4 = conn.prepareStatement(sql4);) {
    						pstm4.setInt(1, cid);
    						pstm4.setInt(2, uid);
    						pstm4.setString(3, nume);
    						pstm4.setString(4, prenume);
    						pstm4.setString(5, telefon);
    						pstm4.setString(6, judet);
    						pstm4.setString(7, localitate);
    						pstm4.setString(8, strada);
    						pstm4.setString(9, bloc);
    						pstm4.setString(10, apartament);
    						pstm4.executeUpdate();
    						String email = DBUtils.findEmailOrder(conn, uid);
    						String host = "smtp.mail.yahoo.com";
    					    String port = "465";
    					    String emailid = null;
    					    String username2 = "robertgrmds@yahoo.com";
    					    String password = "zxc567bnM0";
    					    Properties props = System.getProperties();
    					    Session l_session = null;
    					    props.put("mail.transport.protocol", "smtp");
    					    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    					    props.put("mail.smtp.host", host);
    				        props.put("mail.smtp.auth", "true");
    				        props.put("mail.debug", "false");
    				        props.put("mail.smtp.port", port);
    				        l_session = Session.getInstance(props,
    				                new javax.mail.Authenticator() {
    				                    protected PasswordAuthentication getPasswordAuthentication() {
    				                        return new PasswordAuthentication(username2, password);
    				                    }
    				                });
    				        l_session.setDebug(true);
    				        try {
    				            MimeMessage message = new MimeMessage(l_session);
    				            emailid = "robertgrmds@yahoo.com";
    				            message.setFrom(new InternetAddress(emailid));
    				            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
    				            message.setSubject("Trimitere comanda proiectpao");
    				            message.setContent("Comanda cu numarul " + cid + " a fost inregistrata. Veti fi anuntat cand sa veniti sa o ridicati. Multumim!", "text/html");
    				            Transport.send(message);
    				        } catch (MessagingException mex) {
    				            mex.printStackTrace();
    				        } catch (Exception e) {
    				            e.printStackTrace();
    				        }
    					}
    				}
    			}
    		}
    	}
    }
    
    public static boolean verifyCompatibility(Connection conn, int id, int serviceId) throws SQLException {
    	String sql ="select * from corespondenta_p_s where id_produs = ? and id_serviciu = ? ;";
    	
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setInt(1, id);
    		pstm.setInt(2, serviceId);
    		try(ResultSet rs = pstm.executeQuery();) {
    			if(rs.next()) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public static void confirmOrder(Connection conn, int id) throws SQLException {
    	String sql = "update comenzi set status = 'pregatita' where id = ? ;";
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setInt(1, id);
    		pstm.executeUpdate();
    	}
    }
    
    public static String findEmailOrder(Connection conn, int id) throws SQLException {
    	String sql = "select email from users u join comenzi c on(u.id = c.user_id) where c.id = ? ;";
    	String email = "";
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setInt(1, id);
    		ResultSet rs = pstm.executeQuery();
    		if(rs.next()) {
    			email = rs.getString("email");
    		}
    	}
    	return email;
    }
    
    public static Comanda findOrder(Connection conn, int id) throws SQLException {
    	String sql = "select * from comenzi where id = ? ;";
    	Comanda order = null;
    	try(PreparedStatement pstm = conn.prepareStatement(sql);) {
    		pstm.setInt(1, id);
    		try(ResultSet rs = pstm.executeQuery();) {
    			String sql2 = "select * from comenzi_detalii where id_comanda = ? ;";
    			try(PreparedStatement pstm2 = conn.prepareStatement(sql2);) {
    				pstm2.setInt(1, id);
    				try(ResultSet rs2 = pstm2.executeQuery();) {
    					if(rs.next()) {
    						if(rs2.next()) {
    							User user = DBUtils.findUserId(conn, rs.getInt("user_id"));
            					Produs product = DBUtils.findProdusId(conn, rs2.getInt("id_produs"));
            					String serviceName = DBUtils.findServiciuNume(conn, rs2.getInt("id_serviciu"));
            					order = new Comanda();
            					order.setStatus(rs.getString("status"));
            					order.setData(rs.getString("data"));
            					order.setId(id);
            					order.setNameClient(user.getUserName());
            					order.setNameImg(rs2.getString("nume_poza"));
            					order.setNameService(serviceName);
            					order.setNameProduct(product.getName());
            					order.setPrice(rs2.getInt("pret"));
            					return order;
    						}
    					}
    				}
    			}
    		}
    	}
    	return order;
    }
    
    public static void resetPassword(Connection conn, String email, String parolaResetata) throws SQLException {
    	String sql = "update users set password = ? where email = ? ;";
    	
    	try(PreparedStatement pstm = conn.prepareStatement(sql)) {
    		pstm.setString(1, parolaResetata);
    		pstm.setString(2, email);
    		pstm.executeUpdate();
    	}
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
    
    public static User findUserId(Connection conn, int id) throws SQLException {
    	 
        String sql = "select * from users where id = ? ;";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("password");
            User user = new User();
            user.setUserName("username");
            user.setPassword(password);
            user.setPrivilege(rs.getInt("privilegiu"));
            user.setDisabled(rs.getString("blocat"));
            user.setEmail("email");
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
    
    public static int recordObject(Connection conn, String name, String type) throws SQLException {
    	int nr = 0;
    	if(type.equals("product")) {
			String sql = "select id from produse where nume = ? ;";
			try(PreparedStatement pstm = conn.prepareStatement(sql)) {
				pstm.setString(1, name);
				try(ResultSet rs = pstm.executeQuery();) {
					if(rs.next()) {
						int id = rs.getInt("id");
						String sql2 = "select count(*) as total from comenzi_detalii where id_produs = ? ;";
						try(PreparedStatement pstm2 = conn.prepareStatement(sql2)) {
							pstm2.setInt(1, id);
							try(ResultSet rs2 = pstm2.executeQuery();) {
								if(rs2.next()) {
									nr = rs2.getInt("total");
								}
							}
						}
					}
				}
			}
		} else if(type.equals("service")) {
			String sql = "select id from servicii where nume = ? ;";
			try(PreparedStatement pstm = conn.prepareStatement(sql)) {
				pstm.setString(1, name);
				try(ResultSet rs = pstm.executeQuery();) {
					if(rs.next()) {
						int id = rs.getInt("id");
						String sql2 = "select count(*) as total from comenzi_detalii where id_serviciu = ? ;";
						try(PreparedStatement pstm2 = conn.prepareStatement(sql2)) {
							pstm2.setInt(1, id);
							try(ResultSet rs2 = pstm2.executeQuery();) {
								if(rs2.next()) {
									nr = rs2.getInt("total");
								}
							}
						}
					}
				}
			}
		} else if(type.equals("user")) {
			if(DBUtils.findUser(conn, name) != null ) {
				String sql = "select id from users where username = ? ;";
				try(PreparedStatement pstm = conn.prepareStatement(sql);) {
					pstm.setString(1, name);
					try(ResultSet rs = pstm.executeQuery();) {
						if(rs.next()) {
							int id = rs.getInt("id");
							String sql2 = "select count(*) as total from comenzi where user_id = ? ;";
							try(PreparedStatement pstm2 = conn.prepareStatement(sql2)) {
								pstm2.setInt(1, id);
								try(ResultSet rs2 = pstm2.executeQuery();) {
									if(rs2.next()) {
										nr = rs2.getInt("total");
									}
								}
							}
						}
					}
				}
			}
		}
    	return nr;
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