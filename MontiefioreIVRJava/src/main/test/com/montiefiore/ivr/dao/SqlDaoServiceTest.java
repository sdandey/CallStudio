package com.montiefiore.ivr.dao;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.Test;

public class SqlDaoServiceTest {

	
	SqlDaoService sqlDaoService;
	@Before
	public void setUp() throws Exception {
		sqlDaoService = new SqlDaoService("jdbc:sqlserver://localhost:1433;databaseName=montiefiore", "sdandey", "sdandey1");
		
  
	}

	@Test
	public void testCheckBusinessOnorOffHours() {
		
		System.out.println("is it a business day:" + sqlDaoService.checkBusinessOnorOffHours("4048953191"));
	}

	@Test
	public void testIsHoliday() {
		
		System.out.println("is it a holiday:" + sqlDaoService.isHoliday("8643205630"));
	}

}