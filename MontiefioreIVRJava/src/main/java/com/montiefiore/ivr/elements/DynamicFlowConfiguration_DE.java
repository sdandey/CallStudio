package com.montiefiore.ivr.elements;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;
import com.audium.server.voiceElement.ElementException;
import com.audium.server.voiceElement.ElementInterface;
import com.audium.server.voiceElement.ExitState;
import com.audium.server.voiceElement.Setting;
import com.audium.server.xml.DecisionElementConfig;
import com.montiefiore.ivr.IVRConstants;
import com.montiefiore.ivr.dao.DynamicFlowService;
import com.montiefiore.ivr.vxml.DynamicFlow;

public class DynamicFlowConfiguration_DE extends DecisionElementBase implements ElementInterface{

	
	/**
	 * This method returns the element name to be displayed in
	 * CVP Studio.
	 * @return the element name
	 */
	public String getElementName() {
		return "DynamicFlowConfiguration_DE";
	}

	/**
	 * This method returns the description to be displayed for
	 * the element in CVP Studio.
	 * @return the element description
	 */
	public String getDescription() {
		return "This elements retrieve dynamic flow configuration details for the DNIS";
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
		exitstate[0] = new ExitState(IVRConstants.EXITSTATE_DNISFOUND, IVRConstants.EXITSTATE_DNISFOUND, IVRConstants.EXITSTATE_DNISFOUND);
		exitstate[1] = new ExitState(IVRConstants.EXITSTATE_DNISNOTFOUND, IVRConstants.EXITSTATE_DNISNOTFOUND, IVRConstants.EXITSTATE_DNISNOTFOUND);

		return exitstate;		
	}
	
	
	
	@Override
	public String doDecision(String name, DecisionElementData data)
			throws Exception {
		
		DecisionElementConfig config = data.getDecisionElementConfig(); 
		String dnis = config.getSettingValue(IVRConstants.SETTINGS_DNISNUMBER, data);
		data.addToLog("dnis", dnis);
	
		DynamicFlow dynamicFlow = DynamicFlowService.getInstance().getDynamicFlowForDnis(dnis);
		
		if(dynamicFlow != null){
			data.setSessionData(IVRConstants.S_DYNAMICFLOW, dynamicFlow);
			data.setSessionData(IVRConstants.S_ISCLOSEDHOURSENABLED, dynamicFlow.getClosedHours().
					getIsClosedHoursEnabled());
			data.setSessionData(IVRConstants.S_ISLANGUAGEMENUENABLED, dynamicFlow.isLanguageMenuEnabled());
			if(dynamicFlow.getClosedHours().getClosedHoursPrompt() != null){
				data.setSessionData(IVRConstants.S_CLOSEDHOURSPROMPTNAME, dynamicFlow.getClosedHours().getClosedHoursPrompt().getPromptName());
				data.setSessionData(IVRConstants.S_CLOSEDHOURSPROMPTTEXT, dynamicFlow.getClosedHours().getClosedHoursPrompt().getPromptText());
			}
			
			data.setSessionData(IVRConstants.S_CLOSEDHOURSTRANSFERNUMBER, dynamicFlow.getClosedHours().getTransferNumber());
			data.setSessionData(IVRConstants.S_WELCOMEPROMPTNAME, dynamicFlow.getGreetingPrompt().getPromptName());
			data.setSessionData(IVRConstants.S_WELCOMEPROMPTTEXT, dynamicFlow.getGreetingPrompt().getPromptText());

			return "dnisFound";
		}
		else{
			data.addToLog("Dnis Number:" + dnis + " not found in json configuration file", null);
			return "dnisNotFound";
		}
		
	}

}
