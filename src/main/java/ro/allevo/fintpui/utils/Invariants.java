package ro.allevo.fintpui.utils;

import java.util.Arrays;
import java.util.List;

public class Invariants {

	public static final List<String> RULES_ACTIONS =  Arrays.asList(
			"Aggregate", "Assemble", "ChangeHoldStatus", "ChangeValueDate",
			"Complete", "Disassemble", "HoldQueue", "MoveTo", "ReleaseQueue",
			"SendReply", "TransformMessage", "UpdateLiquidities", "WaitOn");
	
	public static final List<String> RULES_ACTIONS_NO_PARAM =  Arrays.asList(
			"Aggregate", "ChangeValueDate",
			"Complete","HoldQueue", "ReleaseQueue", "UpdateLiquidities");
	
	public static final List<String> RULES_ACTIONS_WITH_PARAM =  Arrays.asList(
			"Assemble", "ChangeHoldStatus",
			"Disassemble", "MoveTo", "SendReply", "TransformMessage", "WaitOn");
	
	public static final List<String> RULES_TYPES = Arrays.asList(
			"Normal", "beforeStart", "afterStop");
	
}
