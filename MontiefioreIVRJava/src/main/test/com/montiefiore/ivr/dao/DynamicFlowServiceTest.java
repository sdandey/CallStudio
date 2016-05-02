package com.montiefiore.ivr.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.montiefiore.ivr.dao.DynamicFlowService;

public class DynamicFlowServiceTest {

	DynamicFlowService dynamicFlowService = null;
	
	private static final String VALID_DNIS = "7928115";
	
	@Before
	public void setUp() throws Exception {
		dynamicFlowService = DynamicFlowService.getInstance();
	}

	@Test
	public void testGetDynamicFlowForDnis() {
		assertNotNull(dynamicFlowService.getDynamicFlowForDnis(VALID_DNIS));
	}

}
