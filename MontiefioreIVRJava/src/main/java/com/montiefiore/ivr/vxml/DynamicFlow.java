package com.montiefiore.ivr.vxml;

import java.util.Map;


public class DynamicFlow {

	private String dnisNumber;
	
	private String mainMenuName;
	
	private Map<String, Menu>  menus;
	
	private ClosedHours closedHours;
	
	private Prompt greetingPrompt;
	
	private boolean isLanguageMenuEnabled;
	
	
	
	public boolean isLanguageMenuEnabled() {
		return isLanguageMenuEnabled;
	}

	public void setLanguageMenuEnabled(boolean isLanguageMenuEnabled) {
		this.isLanguageMenuEnabled = isLanguageMenuEnabled;
	}

	public Map<String, Menu> getMenus() {
		return menus;
	}

	public void setMenus(Map<String, Menu> menus) {
		this.menus = menus;
	}

	


	public String getDnisNumber() {
		return dnisNumber;
	}



	public void setDnisNumber(String dnisNumber) {
		this.dnisNumber = dnisNumber;
	}




	@Override
	public String toString() {
		return "DynamicFlow [dnisNumber=" + dnisNumber + ", mainMenuName="
				+ mainMenuName + ", menus=" + menus + ", closedHours="
				+ closedHours + ", greetingPrompt=" + greetingPrompt
				+ ", isLanguageMenuEnabled=" + isLanguageMenuEnabled + "]";
	}

	public String getMainMenuName() {
		return mainMenuName;
	}

	public void setMainMenuName(String mainMenuName) {
		this.mainMenuName = mainMenuName;
	}

	public ClosedHours getClosedHours() {
		return closedHours;
	}



	public void setClosedHours(ClosedHours closedHours) {
		this.closedHours = closedHours;
	}



	public Prompt getGreetingPrompt() {
		return greetingPrompt;
	}



	public void setGreetingPrompt(Prompt greetingPrompt) {
		this.greetingPrompt = greetingPrompt;
	}



	public static class ClosedHours {
		
		Boolean isClosedHoursEnabled;
		
		Prompt closedHoursPrompt;
		
		String transferNumber;

		
		
		public Boolean getIsClosedHoursEnabled() {
			return isClosedHoursEnabled;
		}

		public void setIsClosedHoursEnabled(Boolean isClosedHoursEnabled) {
			this.isClosedHoursEnabled = isClosedHoursEnabled;
		}

		public Prompt getClosedHoursPrompt() {
			return closedHoursPrompt;
		}

		public void setClosedHoursPrompt(Prompt closedHoursPrompt) {
			this.closedHoursPrompt = closedHoursPrompt;
		}

		public String getTransferNumber() {
			return transferNumber;
		}

		public void setTransferNumber(String transferNumber) {
			this.transferNumber = transferNumber;
		}

		@Override
		public String toString() {
			return "ClosedHours [isClosedHoursEnabled=" + isClosedHoursEnabled
					+ ", closedHoursPrompt=" + closedHoursPrompt
					+ ", transferNumber=" + transferNumber + "]";
		}
		
		
	}
	
}
