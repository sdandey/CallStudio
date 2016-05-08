package com.montiefiore.ivr.elements;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.voiceElement.ElementInterface;
import com.audium.server.voiceElement.ExitState;
import com.audium.server.voiceElement.Setting;
import com.audium.server.xml.DecisionElementConfig;
import com.montiefiore.ivr.IVRConstants;
import com.montiefiore.ivr.dao.SqlDaoService;


public class CheckBusinessHoursDecisionElement extends DecisionElementBase implements ElementInterface{

	
	/**
	 * This method returns the element name to be displayed in
	 * CVP Studio.
	 * @return the element name
	 */
	public String getElementName() {
		return "CheckBusinessHours";
	}

	/**
	 * This method returns the description to be displayed for
	 * the element in CVP Studio.
	 * @return the element description
	 */
	public String getDescription() {
		return "This elements does a jdbc calls to check the business on and off hours";
	}

	/**
	 * This method returns the name of the folder to display the
	 * element as part of in CVP Studio.
	 * @return the display folder name
	 */
	public String getDisplayFolderName() {
		return IVRConstants.SETTINGS_DISPLAYFOLDERNAME;
	}

	/**
	 * This method returns an array of Setting objects to be used
	 * by CVP.
	 * @return an array of Setting objects
	 * @exception ElementException if there is an element 
	 * configuration error
	 */
	public Setting[] getSettings() throws ElementException {
		
		Setting[] settings = new Setting[1];

		settings[0] = new Setting(IVRConstants.SETTINGS_DNISNUMBER,
				"DNIS Number",
				"Pass the Dnis Number to retrieve the business hours for that number",
				Setting.REQUIRED,
				Setting.SINGLE,
				Setting.SUBSTITUTION_ALLOWED,
				Setting.STRING
				);
		settings[0].setDefaultValue("{Data.Session.Dnis}");
		
		
		return settings;
		
	}

	/**
	 * This method returns an array of ExitState objects for use
	 * by Call Studio.
	 * @see com.audium.server.voiceElement.ElementBase#getExitStates()
	 * @return an array of ExitState objects
	 * @exception ElementException if there is an element configuration error
	 */
	public final ExitState[] getExitStates() throws ElementException {

		ExitState exitstate[] = new ExitState[2];
		exitstate[0] = new ExitState(IVRConstants.EXITSTATE_OPEN, IVRConstants.EXITSTATE_OPEN, IVRConstants.EXITSTATE_OPEN);
		exitstate[1] = new ExitState(IVRConstants.EXITSTATE_CLOSED, IVRConstants.EXITSTATE_CLOSED, IVRConstants.EXITSTATE_CLOSED);

		return exitstate;		
	}
	
	
	@Override
	public String doDecision(String name, DecisionElementData decisionData)
			throws Exception {

		
		
		DecisionElementConfig config = decisionData.getDecisionElementConfig();
		
		String dnisNumber = config.getSettingValue(IVRConstants.SETTINGS_DNISNUMBER, decisionData);
		
		//The connection URL has to be set based on the environment
		SqlDaoService sqlDaoService = new SqlDaoService("jdbc:sqlserver://localhost:1433;databaseName=montiefiore", "username here", "passwored here");
		
		if(sqlDaoService.checkBusinessOnorOffHours(dnisNumber))
			return IVRConstants.EXITSTATE_OPEN;
		else
			return IVRConstants.EXITSTATE_CLOSED;
		

		
		
		//LOGIC TO RETRIEVE BUSINESS HOURS AND SET TRUE OR FALSE FLAG IS SET IN THIS ELEMENT
		
	}

	
	
}
