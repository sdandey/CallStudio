package com.montiefiore.ivr.elements;

import com.audium.server.AudiumException;
import com.audium.server.session.ActionElementData;
import com.audium.server.voiceElement.ActionElementBase;
import com.audium.server.voiceElement.ElementData;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.voiceElement.ElementInterface;
import com.audium.server.voiceElement.Setting;
import com.audium.server.xml.ActionElementConfig;
import com.montiefiore.ivr.IVRConstants;


/**
 * This custom element is used to set name/value pairs into
 * session.
 * @author Santosh Dandey
 */

public class SetSessionVariables extends ActionElementBase 
	implements ElementInterface {

	/**
	 * Default Constructor.
	 */
	public SetSessionVariables() {
	}

	/**
	 * This method returns the element name to be displayed in
	 * CVP Studio.
	 * @return the element name
	 */
	public String getElementName() {
		return "SetSessionVariables";
	}

	/**
	 * This method returns the description to be displayed for
	 * the element in CVP Studio.
	 * @return the element description
	 */
	public String getDescription() {
		return "This action element will set variables into the session.";
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
		
		Setting[] settings = new Setting[2];
		settings[0] = new Setting("varName",
			"Var Name",
			"Sesson Variable Name",
			Setting.REQUIRED,
			Setting.REPEATABLE,
			Setting.SUBSTITUTION_ALLOWED,
			Setting.STRING);
		settings[1] = new Setting("varValue",
			"Var Value",
			"Variable Value",
			Setting.REQUIRED,
			Setting.REPEATABLE,
			Setting.SUBSTITUTION_ALLOWED,
			Setting.STRING);
		return settings;
	}

	/**
	 * This method returns an array of element data objects needing 
	 * to be set explicitly by the element in CVP.
	 * @return an array of ElementData objects
	 * @exception ElementException if there is an element 
	 * configuration error
	 */
	public ElementData[] getElementData() throws ElementException {
		return null;
	}

	/**
	 * This method is considered to be a main-method for the action-element.  this method 
	 * gets invoked as the element gets executed. As action-element has only one exit-state it
	 * doesn't return any values.   
	 * @param name of the element 
	 * @param actionElementData object
	 * @exception throws an AudiumException
	 */
	public void doAction(String s, ActionElementData data)
		throws AudiumException {
		
		ActionElementConfig config = data.getActionElementConfig();
		String[] varNames = config.getSettingValues("varName",data);
		String[] varValues = config.getSettingValues("varValue",data);
		for (int i=0; i < varNames.length; i++) {
			if (varNames[i] != null && !"".equals(varNames[i]) &&
				varValues[i] != null && !"".equals(varValues[i])) {
				data.setSessionData(varNames[i], varValues[i]);
				data.addToLog("Setting session var", "Key: " + 
					varNames[i]+ "/Val: " + varValues[i]);
			}
		}
		
	}
	
}
