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
 * This custom-element allows to configure ICM variables 
 * @author santosh.dandey
 *
 */
public class SetICMVars extends ActionElementBase implements ElementInterface {
	
	public SetICMVars(){
		
	}
	/**
	 * This method returns the element name to be displayed in
	 * CVP Studio.
	 * @return the element name
	 */
	public String getElementName() {
		return "SetICMVars";
	}

	/**
	 * This method returns the description to be displayed for
	 * the element in CVP Studio.
	 * @return the element description
	 */
	public String getDescription() {
		return "This action element set the ICM variables.";
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
		
		settings[0] = new Setting(IVRConstants.SETTINGS_CALLTYPE_ENGLISH,
				"Call Type (English)",
				"Set Call Type for English",
				Setting.REQUIRED,
				Setting.SINGLE,
				Setting.SUBSTITUTION_ALLOWED,
				Setting.STRING
				);
		
		settings[1] = new Setting(IVRConstants.SETTINGS_CALLTYPE_SPANISH,
			"Call Type (Spanish)",
			"Set Call Type for Spanish",
			Setting.OPTIONAL,
			Setting.SINGLE,
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
	public void doAction(String string, ActionElementData actionElementData)
		throws AudiumException {
		
		ActionElementConfig config = actionElementData.getActionElementConfig();
		
		Boolean isSpanishSelected = false;
		
		if(actionElementData.getSessionData(IVRConstants.S_ISSPANISHSELECTED) != null &&
				"true".equals(actionElementData.getSessionData(IVRConstants.S_ISSPANISHSELECTED)) ){
			isSpanishSelected = true;
		}

		String callType = null;
		if(isSpanishSelected && config.getSettingValue(IVRConstants.SETTINGS_CALLTYPE_SPANISH, actionElementData) != null
				&& "".equals(config.getSettingValue(IVRConstants.SETTINGS_CALLTYPE_SPANISH, actionElementData))){
			callType = config.getSettingValue(IVRConstants.SETTINGS_CALLTYPE_SPANISH, actionElementData);
		}
		else
			callType = config.getSettingValue(IVRConstants.SETTINGS_CALLTYPE_ENGLISH, actionElementData);
		
		actionElementData.setSessionData(IVRConstants.S_CALLTYPE, callType);
	}
	
}
