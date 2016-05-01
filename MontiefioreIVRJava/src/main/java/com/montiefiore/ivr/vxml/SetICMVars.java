package com.montiefiore.ivr.vxml;

public class SetICMVars {

	
	private Prompt transferPrompt;
	
	private String callTypeForEnglish;
	
	private String callTypeForSpanish;
	
	private String priority;
	
	
	public Prompt getTransferPrompt(){
		return transferPrompt;
	}
	
	public void setTransferPrompt(Prompt transferPrompt){
		this.transferPrompt = transferPrompt;
	}
	public String getCallTypeForEnglish() {
		return callTypeForEnglish;
	}

	public void setCallTypeForEnglish(String callTypeForEnglish) {
		this.callTypeForEnglish = callTypeForEnglish;
	}

	public String getCallTypeForSpanish() {
		return callTypeForSpanish;
	}

	public void setCallTypeForSpanish(String callTypeForSpanish) {
		this.callTypeForSpanish = callTypeForSpanish;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "SetICMVars [callTypeForEnglish=" + callTypeForEnglish
				+ ", callTypeForSpanish=" + callTypeForSpanish + ", priority="
				+ priority + "]";
	}
}
