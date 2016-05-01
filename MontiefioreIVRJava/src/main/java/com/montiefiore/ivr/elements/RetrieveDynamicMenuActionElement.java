package com.montiefiore.ivr.elements;

import com.audium.server.session.ActionElementData;
import com.audium.server.voiceElement.ActionElementBase;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.voiceElement.ElementInterface;
import com.audium.server.voiceElement.ExitState;
import com.audium.server.voiceElement.Setting;
import com.montiefiore.ivr.IVRConstants;

public class RetrieveDynamicMenuActionElement extends ActionElementBase implements ElementInterface{

	
	
	/**
	 * This method returns the element name to be displayed in
	 * CVP Studio.
	 * @return the element name
	 */
	public String getElementName() {
		return "RetrieveDynamicMenuAE";
	}

	/**
	 * This method returns the description to be displayed for
	 * the element in CVP Studio.
	 * @return the element description
	 */
	public String getDescription() {
		return "This elements does a jdbc calls to retrieve dynamic menu information for the DNIS";
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

	

	@Override
	public void doAction(String arg0, ActionElementData arg1) throws Exception {
		
		//ACTUAL CODE GOES HERE
	}

}
