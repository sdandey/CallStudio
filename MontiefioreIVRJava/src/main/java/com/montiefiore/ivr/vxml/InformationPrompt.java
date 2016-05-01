package com.montiefiore.ivr.vxml;


public class InformationPrompt {

	private Prompt prompt;
	
	private Integer noOfRepeats;

	public Prompt getPrompt() {
		return prompt;
	}

	public void setPrompt(Prompt prompt) {
		this.prompt = prompt;
	}

	public Integer getNoOfRepeats() {
		return noOfRepeats;
	}

	public void setNoOfRepeats(Integer noOfRepeats) {
		this.noOfRepeats = noOfRepeats;
	}

	@Override
	public String toString() {
		return "Information [prompt=" + prompt + ", noOfRepeats=" + noOfRepeats
				+ "]";
	}
	
	
	
	
}
