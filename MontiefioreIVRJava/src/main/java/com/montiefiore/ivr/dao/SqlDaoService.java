package com.montiefiore.ivr.dao;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SqlDaoService {

    final static Logger logger = Logger.getLogger(SqlDaoService.class);
    private Connection connection;

	
	public SqlDaoService(String connectionUrl, String username, String password){
		
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(connectionUrl,username,password);
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

	public boolean checkBusinessOnorOffHours(String dnisNumber){

        return !isHoliday(dnisNumber) && isNormalBusinessHours(dnisNumber);
    }


    private boolean isNormalBusinessHours(String dnisNumber){

		String sql = "select * from operatinghours where dnis = ?";
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			
			connection = getConnection();
			
			statement = connection.prepareStatement(sql);

			statement.setString(1, dnisNumber);
			rs = statement.executeQuery();


            String startTime;
            String endTime;
            String openDays;

            DateTime today = new DateTime();
			Time currentTime = new Time(System.currentTimeMillis());
			
			logger.info(currentTime.toString());

			while(rs.next()){
				openDays = rs.getString("DAYS");
				

				if(openDays.length() == 7 && openDays.charAt(today.getDayOfWeek()-1) == '1'){

					startTime = rs.getString("STARTTIME");
					endTime = rs.getString("ENDTIME");

					logger.info("StartTime:" + startTime.toString() + " EndTime:" + 
							endTime.toString() );

					Calendar fromOperatingHours = Calendar.getInstance();
					fromOperatingHours.set(Calendar.HOUR_OF_DAY, Integer.parseInt(startTime.substring(0, 2)));
					fromOperatingHours.set(Calendar.MINUTE, Integer.parseInt(startTime.substring(3, 5)));
					fromOperatingHours.set(Calendar.SECOND, Integer.parseInt(startTime.substring(6, 8)));
					

					Calendar toOperatingHours = Calendar.getInstance();
					toOperatingHours.set(Calendar.HOUR_OF_DAY, Integer.parseInt(endTime.substring(0, 2)));
					toOperatingHours.set(Calendar.MINUTE, Integer.parseInt(endTime.substring(3, 5)));
					toOperatingHours.set(Calendar.SECOND, Integer.parseInt(endTime.substring(6, 8)));

					
					logger.info("fromOperatingHours:" + fromOperatingHours.toString());
					
					
					Calendar now = Calendar.getInstance();
					logger.info(" currentTime:" + currentTime.toString() + " in ms:" + currentTime.getTime());
					
					if(now.after(fromOperatingHours) && now.before(toOperatingHours))	{
						logger.info("Call came in operating hours");
						return true;
					}
					else{
						logger.info("Call Came after hours");
						return false;
					}
					
				}
			}
			
			logger.info("no records found for the dnis:" + dnisNumber + " keeping it open by default");
			
			return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 

		finally{
			closeSqlResources(rs, statement, connection);
		}
		return false;

	}

    public String getPromptText(String dnis) {
        String prompt = " ";

        String sql = "select * from operatinghours where dnis = ?";
        ResultSet rs = null;
        Connection connection = null;
        PreparedStatement statement = null;


        try {

            statement = getConnection().prepareStatement(sql);

            statement.setString(1, dnis);
            rs = statement.executeQuery();

            while (rs.next()) {
                prompt = rs.getString("PROMPT");
                logger.info("Prompt:" + prompt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeSqlResources(rs, statement, connection);
        }
        return prompt;
    }


	public boolean isHoliday(String dnisNumber){

		String sql = "select * from dnis_holidays where dnis = ?";
		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {

			statement = getConnection().prepareStatement(sql);

			statement.setString(1, dnisNumber);
			rs = statement.executeQuery();

			List<Date> dates = new ArrayList<>();
			while(rs.next())
				dates.add(rs.getDate("date"));

			DateTime today = new DateTime();
			DateTimeFormatter fmt = DateTimeFormat.forPattern("d MMMM, yyyy");
			logger.info("today date:" + today.toString(fmt));


			for (Date holiday : dates) {
				DateTime holiday1 = new DateTime(holiday);
				logger.info("holiday date:" + holiday1.toString(fmt));

				if(DateTimeComparator.getDateOnlyInstance().compare(today, holiday) == 0){
					return true;
				}
			}
			return false;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally{
			closeSqlResources(rs, statement, connection);
		}
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
                Timestamp startDate = rs.getTimestamp("DATE_START");
                Timestamp endDate = rs.getTimestamp("DATE_END");

                logger.info("StartDate:" + new DateTime(startDate).toString() + " EndDate:" +
                        new DateTime(endDate).toString());


                if (today.isAfter(startDate.getTime()) && today.isBefore(endDate.getTime())) {
                    logger.info("call came during exception time");
                    return true;
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

	private void closeSqlResources(ResultSet rs, PreparedStatement statement, Connection connection){

		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			finally{
				if(statement != null){
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}

					finally{
						if(connection != null){
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
