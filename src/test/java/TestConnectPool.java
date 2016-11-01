import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 * Created by elier on 2016/4/11.
 */
public class TestConnectPool {
    private static LinkedList<Connection> connectionQueue;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized static Connection getConnection() {
        try {
            if(connectionQueue == null){
                connectionQueue = new LinkedList<Connection>();
                for(int i=0;i<10;i++){
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://192.168.190.132:3306/testdb","","");
                    connectionQueue.push(conn);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connectionQueue.poll();
    }

    public static void returnConnection(Connection conn) {
        connectionQueue.push(conn);
    }
}
