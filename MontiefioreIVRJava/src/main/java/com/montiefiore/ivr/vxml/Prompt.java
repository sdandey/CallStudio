package com.montiefiore.ivr.vxml;

public class Prompt {

	private String promptName;
	private String promptText;
	private Boolean bargein;
	
	public Prompt(){}

	
	
	public Prompt(String promptName, String promptText) {
		super();
		this.promptName = promptName;
		this.promptText = promptText;
	}                                      



	public Prompt(String promptName, String promptText, Boolean bargein) {
		super();
		this.promptName = promptName;
		this.promptText = promptText;
		this.bargein = bargein;
	}
	
	

	public Prompt(String promptText) {
		super();
		this.promptText = promptText;
	}



	public String getPromptName() {
		return promptName;
	}

	public void setPromptName(String promptName) {
		this.promptName = promptName;
	}

	public String getPromptText() {
		return promptText;
	}

	public void setPromptText(String promptText) {
		this.promptText = promptText;
	}

	public Boolean getBargein() {
		return bargein;
	}

	public void setBargein(Boolean bargein) {
		this.bargein = bargein;
	}

	@Override
	public String toString() {
		return "Prompt [promptName=" + promptName + ", promptText="
				+ promptText + ", bargein=" + bargein + "]";
	}

}
