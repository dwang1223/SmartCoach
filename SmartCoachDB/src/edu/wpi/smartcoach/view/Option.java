package edu.wpi.smartcoach.view;

public class Option {
	
	private String id;
	private Object value;
	private String condition;
	
	private boolean selected;
	
	public Option(String id, Object value, String condition){
		this.id = id;
		this.value = value;	
		this.condition = condition;
	}
	
	public Option(String id, Object value){
		this(id, value, null);
	}
	
	public String getId(){
		return id;
	}
	
	public String getText(){
		return value.toString();
	}
	
	public boolean isSelected(){
		return selected;
	}
		
	public void setSelected(boolean select){
		selected = select;
	}
	
	public Object getValue(){
		return value;
	}

	public String toString(){
		try {
			return value.toString();
		} catch(Exception e){
			return "Option "+id;
		}
	}
	
	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
