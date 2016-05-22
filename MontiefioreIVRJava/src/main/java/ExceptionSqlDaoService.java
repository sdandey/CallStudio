/**
 *
 */

import java.sql.Connection;
//import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
//import java.util.ArrayList;
import java.util.Calendar;
//import java.util.List;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
//import org.joda.time.DateTimeComparator;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;

/**
 * @author smutyala
 */
public class ExceptionSqlDaoService {


    Logger logger = Logger.getLogger(ExceptionSqlDaoService.class);
    Connection connection;


    public ExceptionSqlDaoService(String connectionUrl, String username, String password) {


        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean CheckDateException(String dnisNumber) throws Throwable {

        if (isException(dnisNumber)) {
            return true;
        } else
            return false;
    }

    public boolean CheckAfterHourException(String dnisNumber) {
        if (isAfterHours(dnisNumber)) {
            return true;
        } else
            return false;
    }

    public boolean isException(String dnisNumber) {

        String sql = "select * from Exception_date where DNIS = ?";
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {

            statement = getConnection().prepareStatement(sql);

            statement.setString(1, dnisNumber);
            rs = statement.executeQuery();

            String Date_Start = null;
            String Date_End = null;


            while (rs.next()) {

                DateTime today = new DateTime();
                Time currentTime = new Time(System.currentTimeMillis());
                logger.info(currentTime.toString());
                Date_Start = rs.getString("DATE_START");
                Date_End = rs.getString("DATE_END");

                logger.info("StartDate:" + Date_Start.toString() + " EndDate:" +
                        Date_End.toString());

                Calendar fromException_Date = Calendar.getInstance();
                fromException_Date.set(Calendar.YEAR, Integer.parseInt(Date_Start.substring(0, 4)));
                fromException_Date.set(Calendar.MONTH, Integer.parseInt(Date_Start.substring(5, 7)));
                fromException_Date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(Date_Start.substring(8, 10)));
                fromException_Date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Date_Start.substring(11, 13)));
                fromException_Date.set(Calendar.MINUTE, Integer.parseInt(Date_Start.substring(14, 16)));
                fromException_Date.set(Calendar.SECOND, Integer.parseInt(Date_Start.substring(17, 19)));


                Calendar toException_Date = Calendar.getInstance();
                toException_Date.set(Calendar.YEAR, Integer.parseInt(Date_End.substring(0, 4)));
                toException_Date.set(Calendar.MONTH, Integer.parseInt(Date_End.substring(5, 7)));
                toException_Date.set(Calendar.DAY_OF_MONTH, Integer.parseInt(Date_End.substring(8, 10)));
                toException_Date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(Date_End.substring(11, 13)));
                toException_Date.set(Calendar.MINUTE, Integer.parseInt(Date_End.substring(14, 16)));
                toException_Date.set(Calendar.SECOND, Integer.parseInt(Date_End.substring(17, 19)));


                logger.info("fromException_Date:" + fromException_Date.toString());
                logger.info("toException_Date:" + toException_Date.toString());


                Calendar now = Calendar.getInstance();

                logger.info(" today:" + today.toString() + " in ms:" + today.toDate());
                logger.info("now:" + now.toString());
                if (now.after(fromException_Date) && now.before(toException_Date)) {

                    logger.info("There is an Exception");
                    return true;
                } else {
                    logger.info("fromException_Date:" + fromException_Date.toString());
                    logger.info("toException_Date:" + toException_Date.toString());
                    logger.info("now:" + now.toString());
                    logger.info("No Exception");
                    return false;
                }
            }
            logger.info("no records found for the dnis:" + dnisNumber + " keeping it open by default");

            return false;


        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            closeSqlResources(rs, statement, connection);
        }
        return false;
    }


    public boolean isAfterHours(String dnisNumber) {

        String sql = "select * from Exception_date where DNIS = ?";
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //String sendtoAH = null;
            statement = getConnection().prepareStatement(sql);
            statement.setString(1, dnisNumber);
            rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString("sendtoAH");
                logger.info("name:" + name);
                if (name.equals("1"))
                    return true;
                else
                    return false;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSqlResources(rs, statement, connection);
        }
        return false;
    }


    private void closeSqlResources(ResultSet rs, PreparedStatement statement, Connection connection) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}


