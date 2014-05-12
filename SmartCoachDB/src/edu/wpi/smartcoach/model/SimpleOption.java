package edu.wpi.smartcoach.model;

import java.util.ArrayList;
import java.util.List;

public class SimpleOption implements OptionModel{
	
	public static final int YES = 1;
	public static final int NO = OptionQuestionModel.DEFAULT;

	public static final String YES_STR = "Yes";
	public static final String NO_STR = "No";
	
	public static List<SimpleOption> getYesNoOptions(){
		return new ArrayList<SimpleOption>(){{
			add(new SimpleOption(YES, YES_STR));
			add(new SimpleOption(NO, NO_STR));
		}};
	}
	
	
	int id;
	Object value;
	
	public SimpleOption(int id, Object val){
		this.id = id;
		this.value = val;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getText(){
		return value.toString();
	}

	@Override
	public Object getValue() {
		return value;
	}

}
