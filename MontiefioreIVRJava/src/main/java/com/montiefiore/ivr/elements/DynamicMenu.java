package com.montiefiore.ivr.elements;

import java.util.List;
import java.util.Map;
import com.audium.server.AudiumException;
import com.audium.server.proxy.VoiceElementInterface;
import com.audium.server.session.ElementAPI;
import com.audium.server.xml.VoiceElementConfig;
import com.montiefiore.ivr.dao.DynamicFlowService;
import com.montiefiore.ivr.vxml.DynamicFlow;
import com.montiefiore.ivr.vxml.Menu;
import com.montiefiore.ivr.vxml.Menu.MenuOption;
import com.montiefiore.ivr.vxml.Prompt;

public class DynamicMenu implements VoiceElementInterface{

	@Override
	public VoiceElementConfig getConfig(String name, ElementAPI elementAPI,
			VoiceElementConfig defaults) throws AudiumException {

		
		String dnis = elementAPI.getDnis();
		DynamicFlow dynamicFlow = DynamicFlowService.getInstance().getDynamicFlowForDnis(dnis);
		
		Menu mainMenu = dynamicFlow.getMenus().get(dynamicFlow.getMainMenuName());
		elementAPI.addToLog("mainMenu Configuration", mainMenu.toString());

		String maxTries = String.valueOf(mainMenu.getMaxTries()!= null?mainMenu.getMaxTries():3);
		
		defaults.setSettingValue("max_nomatch_count", maxTries);
		elementAPI.addToLog("Set max_nomatch_count to ", maxTries);
		
		defaults.setSettingValue("max_noinput_count", maxTries);
		elementAPI.addToLog("Set max_noinput_count to ", maxTries);
		
		VoiceElementConfig.AudioGroup initial = defaults.getAudioGroup("initial_audio_group", 1);
		VoiceElementConfig.AudioGroup noMatch = defaults.getAudioGroup("nomatch_audio_group", 1);
		VoiceElementConfig.AudioGroup noInput = defaults.getAudioGroup("noinput_audio_group", 1);
		
		VoiceElementConfig.StaticAudio audioItem = defaults.new StaticAudio(" ", "");
		
		List<Prompt> defaultAudioPrompts = mainMenu.getDefaultAudioGroup().getPrompts();
		for (Prompt prompt : defaultAudioPrompts) {
			audioItem = defaults.new StaticAudio(prompt.getPromptText(), prompt.getPromptName());
			initial.addAudioItem(audioItem);
			elementAPI.addToLog("Added Static Audio to defaultAudioGroup", prompt.toString());
		}

		if(mainMenu.getNoInputAudioGroup() != null){
			List<Prompt> noInputAudioPrompts = mainMenu.getNoInputAudioGroup().getPrompts();
			for (Prompt prompt : noInputAudioPrompts ) {
				audioItem = defaults.new StaticAudio(prompt.getPromptText(), prompt.getPromptName());
				noInput.addAudioItem(audioItem);
				elementAPI.addToLog("Added Static Audio to noInputAudioGroup", prompt.toString());
			}
		}
		
		if(mainMenu.getNoMatchAudioGroup() != null){
			List<Prompt> noMatchAudioPrompts = mainMenu.getNoMatchAudioGroup().getPrompts();
			for (Prompt prompt : noMatchAudioPrompts ) {
				audioItem = defaults.new StaticAudio(prompt.getPromptText(), prompt.getPromptName());
				noMatch.addAudioItem(audioItem);
				elementAPI.addToLog("Added Static Audio to noMatchAudioGroup", prompt.toString());
			}
		}
		
		Map<String, MenuOption> menuOptions = mainMenu.getMenuOptions();
		for (String menuOptionNumber: menuOptions.keySet()) {
			
			if("maxattempts".equals(menuOptionNumber))
				continue;
			
			MenuOption menuOption = menuOptions.get(menuOptionNumber); 
			defaults.addSettingValue("dtmf_keypress", menuOption.getMenuOptionNumber() + "[" + 
					menuOption.getMenuOptionValue() + "]");
			elementAPI.addToLog("added setting dtmf_keypress", menuOption.getMenuOptionNumber() + "[" + 
					menuOption.getMenuOptionValue() + "]");
			
		}
		
		defaults.setAudioGroup(initial);
		defaults.setAudioGroup(noInput);
		defaults.setAudioGroup(noMatch);
		return defaults;
	}

}
