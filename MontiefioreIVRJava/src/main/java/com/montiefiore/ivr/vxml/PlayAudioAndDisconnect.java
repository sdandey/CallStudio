package com.montiefiore.ivr.vxml;

import java.util.List;


public class PlayAudioAndDisconnect {

	private List<Prompt> audioPrompt;

	public List<Prompt> getAudioPrompt() {
		return audioPrompt;
	}

	public void setAudioPrompt(List<Prompt> audioPrompt) {
		this.audioPrompt = audioPrompt;
	}

	@Override
	public String toString() {
		return "PlayAudioAndDisconnect [audioPrompt=" + audioPrompt + "]";
	}
	
	
}
