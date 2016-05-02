package com.montiefiore.ivr.vxml;

import java.util.List;
import java.util.Map;


public class Menu {

	private String menuName;
	
	private AudioGroup defaultAudioGroup;
	
	private AudioGroup noMatchAudioGroup;
	
	private AudioGroup noInputAudioGroup;
	
	private Integer maxTries;
	
	private Map<String, MenuOption> menuOptions;
	
	@Override
	public String toString() {
		return "Menu [menuName=" + menuName + ", defaultAudioGroup="
				+ defaultAudioGroup + ", noMatchAudioGroup="
				+ noMatchAudioGroup + ", noInputAudioGroup="
				+ noInputAudioGroup + ", maxTries=" + maxTries
				+ ", menuOptions=" + menuOptions + "]";
	}

	public AudioGroup getDefaultAudioGroup() {
		return defaultAudioGroup;
	}

	public void setDefaultAudioGroup(AudioGroup defaultAudioGroup) {
		this.defaultAudioGroup = defaultAudioGroup;
	}

	public AudioGroup getNoMatchAudioGroup() {
		return noMatchAudioGroup;
	}

	public void setNoMatchAudioGroup(AudioGroup noMatchAudioGroup) {
		this.noMatchAudioGroup = noMatchAudioGroup;
	}

	public AudioGroup getNoInputAudioGroup() {
		return noInputAudioGroup;
	}

	public void setNoInputAudioGroup(AudioGroup noInputAudioGroup) {
		this.noInputAudioGroup = noInputAudioGroup;
	}

	public Integer getMaxTries() {
		return maxTries;
	}

	public void setMaxTries(Integer maxTries) {
		this.maxTries = maxTries;
	}

	public Map<String, MenuOption> getMenuOptions() {
		return menuOptions;
	}

	public void setMenuOptions(Map<String, MenuOption> menuOptions) {
		this.menuOptions = menuOptions;
	}

	public static class MenuOption{
		
		Integer menuOptionNumber;
		String menuOptionValue;
		public String getMenuOptionValue() {
			return menuOptionValue;
		}
		public void setMenuOptionValue(String menuOptionValue) {
			this.menuOptionValue = menuOptionValue;
		}
		String optionType;
		Transfer transfer;
		PlayAudioAndDisconnect playAudioAndDisconnect;
		SetICMVars setICMVars;
		InformationPrompt informationPrompt;
		SubMenu subMenu;
		
		public SubMenu getSubMenu() {
			return subMenu;
		}
		public void setSubMenu(SubMenu subMenu) {
			this.subMenu = subMenu;
		}
		@Override
		public String toString() {
			return "MenuOption [menuOptionNumber=" + menuOptionNumber
					+ ", menuOptionValue=" + menuOptionValue + ", optionType="
					+ optionType + ", transfer=" + transfer
					+ ", playAudioAndDisconnect=" + playAudioAndDisconnect
					+ ", setICMVars=" + setICMVars + ", informationPrompt="
					+ informationPrompt + ", subMenu=" + subMenu + "]";
		}
		public InformationPrompt getInformationPrompt() {
			return informationPrompt;
		}
		public void setInformationPrompt(InformationPrompt informationPrompt) {
			this.informationPrompt = informationPrompt;
		}
		public Integer getMenuOptionNumber() {
			return menuOptionNumber;
		}
		public void setMenuOptionNumber(Integer menuOptionNumber) {
			this.menuOptionNumber = menuOptionNumber;
		}
		public String getOptionType() {
			return optionType;
		}
		public void setOptionType(String optionType) {
			this.optionType = optionType;
		}
		public Transfer getTransfer() {
			return transfer;
		}
		public void setTransfer(Transfer transfer) {
			this.transfer = transfer;
		}
		public PlayAudioAndDisconnect getPlayAudioAndDisconnect() {
			return playAudioAndDisconnect;
		}
		public void setPlayAudioAndDisconnect(
				PlayAudioAndDisconnect playAudioAndDisconnect) {
			this.playAudioAndDisconnect = playAudioAndDisconnect;
		}
		public SetICMVars getSetICMVars() {
			return setICMVars;
		}
		public void setSetICMVars(SetICMVars setICMVars) {
			this.setICMVars = setICMVars;
		}
	}
	
	public static class AudioGroup{

		public AudioGroup(){

		}

		public AudioGroup(List<Prompt> prompts){
			this.prompts = prompts;

		}
		List<Prompt> prompts;

		public List<Prompt> getPrompts() {
			return prompts;
		}

		public void setPrompts(List<Prompt> prompts) {
			this.prompts = prompts;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("AudioGroup {\nprompts=").append(prompts)
			.append("\n}");
			return builder.toString();
		}
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	
}
