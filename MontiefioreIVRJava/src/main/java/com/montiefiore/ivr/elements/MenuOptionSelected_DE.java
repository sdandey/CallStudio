package com.montiefiore.ivr.elements;

import com.audium.server.session.DecisionElementData;
import com.audium.server.voiceElement.DecisionElementBase;
import com.audium.server.voiceElement.ElementInterface;

public class MenuOptionSelected_DE extends DecisionElementBase implements ElementInterface{

	@Override
	public String doDecision(String name, DecisionElementData data)
			throws Exception {
		return null;
	}

}
