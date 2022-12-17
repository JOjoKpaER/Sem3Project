package utility;

public enum AuthDataEnum {

	login(0),
	registration(1),
	allow(2),
	deny(3);
	
	private final int value;
	private AuthDataEnum(int _value) {
		this.value = _value;
	}
	
	public int getValue() {
		return value;
	}
	
}
