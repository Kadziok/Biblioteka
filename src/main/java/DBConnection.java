import java.sql.*;

public class DBConnection {
    private static Connection con = null;

    private DBConnection(){};

    public static void initConnection(String user, String pass){
        try{
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library?" +
                            "useUnicode=true&" +
                            "useJDBCCompliantTimezoneShift=true&" +
                            "useLegacyDatetimeCode=false&serverTimezone=UTC",
                    user, pass);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static void closeConnection(){
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            con = null;
        }
    }

    public static Connection getConnection(){
        return con;
    }
}

