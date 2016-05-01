package com.montiefiore.ivr.vxml;


public class Transfer {

	private Prompt transferPrompt;
	
	private String transferNumber;

	public Prompt getTransferPrompt() {
		return transferPrompt;
	}

	public void setTransferPrompt(Prompt transferPrompt) {
		this.transferPrompt = transferPrompt;
	}

	public String getTransferNumber() {
		return transferNumber;
	}

	public void setTransferNumber(String transferNumber) {
		this.transferNumber = transferNumber;
	}

	@Override
	public String toString() {
		return "Transfer [transferPrompt=" + transferPrompt
				+ ", transferNumber=" + transferNumber + "]";
	}
	
	
	
}
