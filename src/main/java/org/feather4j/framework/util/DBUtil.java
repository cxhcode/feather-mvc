package org.feather4j.framework.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.feather4j.framework.helper.ConfigHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jackie on 2015/12/10.
 * Email : chenxinhua@ishehui.com
 */
public final class DBUtil {

    /**
     * 数据源，static
     */
    private static DataSource DS;

    /**
     * 从数据源获得一个连接
     */
    public static Connection getConn() {
        Connection con = null;
        if (DS != null) {
            try {
                con = DS.getConnection();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            try {
                if (con != null) {
                    con.setAutoCommit(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }
        return null;
    }

    /**
     * 默认的构造函数
     */
    public DBUtil() {
    }

    /**
     * 构造函数，初始化了 DS ，指定 数据库
     */
    public DBUtil(String connectURI) {
        initDS(connectURI);
    }

    /**
     * 构造函数，初始化了 DS ，指定 所有参数
     */
    public DBUtil(String connectURI, String username, String passwd,
                  String driverClass, int initialSize, int maxActive, int maxIdle,
                  int maxWait, int minIdle) {
        initDS(connectURI, username, passwd, driverClass, initialSize, maxActive,
                maxIdle, maxWait, minIdle);
    }

    /**
     * 创建数据源，除了数据库外，都使用硬编码默认参数；
     *
     * @param connectURI 数据库
     * @return
     */
    public static void initDS(String connectURI) {
        initDS(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword(), ConfigHelper.getJdbcDriver(), 5, 100,
                30, 10000, 1);
    }

    /**
     * 指定所有参数连接数据源
     *
     * @param connectURI    数据库
     * @param username      用户名
     * @param pswd          密码
     * @param driverClass   数据库连接驱动名
     * @param initialSize   初始连接池连接个数
     * @param maxtotal      最大活动连接数
     * @param maxIdle       最大连接数
     * @param maxWaitMillis 获得连接的最大等待毫秒数
     * @param minIdle       最小连接数
     * @return
     */
    public static void initDS(String connectURI, String username, String pswd,
                              String driverClass, int initialSize, int maxtotal, int maxIdle,
                              int maxWaitMillis, int minIdle) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUsername(username);
        ds.setPassword(pswd);
        ds.setUrl(connectURI);
        ds.setInitialSize(initialSize); // 初始的连接数；
        ds.setMaxTotal(maxtotal);
        ds.setMaxIdle(maxIdle);
        ds.setMaxWaitMillis(maxWaitMillis);
        ds.setMinIdle(minIdle);
        DS = ds;
    }

    /**
     * 获得数据源连接状态
     */
    public static Map<String, Integer> getDataSourceStats() throws SQLException {
        BasicDataSource bds = (BasicDataSource) DS;
        Map<String, Integer> map = new HashMap<String, Integer>(2);
        map.put("active_number", bds.getNumActive());
        map.put("idle_number", bds.getNumIdle());
        return map;
    }

    /**
     * 关闭数据源
     */
    protected static void shutdownDataSource() throws SQLException {
        BasicDataSource bds = (BasicDataSource) DS;
        bds.close();
    }

    public static void main(String[] args) {
        DBUtil db = new DBUtil("jdbc:mysql://localhost:3306/testit");
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = db.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from test limit 1 ");
            System.out.println("Results:");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print("\t" + rs.getString(i) + "\t");
                }
                System.out.println("");
            }
            System.out.println(getDataSourceStats());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
                shutdownDataSource();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
