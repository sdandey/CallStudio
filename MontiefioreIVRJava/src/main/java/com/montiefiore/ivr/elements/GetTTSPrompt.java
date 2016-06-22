package com.montiefiore.ivr.elements;

/**
 * Created by smutyala on 6/21/2016.
 */

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.*;
import com.audium.server.xml.DecisionElementConfig;
import com.montiefiore.ivr.IVRConstants;
import com.montiefiore.ivr.dao.SqlDaoService;

/**
 * @author smutyala
 */
public class GetTTSPrompt extends DecisionElementBase implements ElementInterface {

    /**
     * This method returns element name to be displayed
     *
     * @ return element name
     */
    public String getElementName() {
        return "GetTTS";
    }

    /**
     * This method displays the information regarding the element
     * in CVP Studio
     *
     * @ return Description
     */
    public String getDescription() {
        return "This element gets the TTS string stored in the DataBase";
    }

    /**
     * This method returns the name of the Display folder
     * in which the element is a part of in'
     * CVP Studio
     *
     * @return DisplayFolderName
     */

    public String getDisplayFolderName() {
        return IVRConstants.SETTINGS_DISPLAYFOLDERNAME;
    }

    /**
     * This method returns an array of setting objects to be used by the CVP
     *
     * @return an array of SettingObjects
     * @throws ElementException if there is an element configuration error
     */
    public Setting[] getSettings() throws ElementException {

        Setting[] settings = new Setting[1];
        settings[0] = new Setting(IVRConstants.SETTINGS_DNISNUMBER,
                "DNIS Number",
                "Pass the DNIS Number to retrieve the prompt from the DataBase",
                Setting.REQUIRED,
                Setting.SINGLE,
                Setting.SUBSTITUTION_ALLOWED,
                Setting.STRING
        );
        settings[0].setDefaultValue("{Data.Session._dnis}");
        return settings;

    }

    /**
     * This method returns an array of ExitState Objects used in
     * CVP Studio
     *
     * @return ExitState Objects
     * @throws ElementException if element configuration error
     * @see com.audium.server.voiceElement.ElementBase#getExitStates()
     */

    public final ExitState[] getExitStates() throws ElementException {

        ExitState[] exitstate = new ExitState[2];
        exitstate[0] = new ExitState(IVRConstants.EXITSTATE_FOUND, IVRConstants.EXITSTATE_FOUND, IVRConstants.EXITSTATE_FOUND);
        exitstate[1] = new ExitState(IVRConstants.EXITSTATE_NOTFOUND, IVRConstants.EXITSTATE_NOTFOUND, IVRConstants.EXITSTATE_NOTFOUND);

        return exitstate;
    }

    @Override

    public String doDecision(String name, DecisionElementData decisionData) throws Exception {

        DecisionElementConfig config = decisionData.getDecisionElementConfig();
        String dnisNumber = config.getSettingValue(IVRConstants.SETTINGS_DNISNUMBER, decisionData);
        //The connection URL has to be set based on the environment
        SqlDaoService sqlDaoService = new SqlDaoService("jdbc:sqlserver://ybccc01:1433;database=ACD_Hours", "wa", "java1cti");
        String retrieveTTS = sqlDaoService.getPromptText(dnisNumber);
        //String prompt = config.getSettingValue(IVRConstants.SETTINGS_PROMPT, decisionData);
        if (retrieveTTS.length() > 0) {
            decisionData.setSessionData("retrieveTTS", retrieveTTS);
            return IVRConstants.EXITSTATE_FOUND;
        } else {
            return IVRConstants.EXITSTATE_NOTFOUND;
        }
    }


}



