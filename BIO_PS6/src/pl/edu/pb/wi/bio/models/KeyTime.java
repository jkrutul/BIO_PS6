package pl.edu.pb.wi.bio.models;

public class KeyTime {
	private Character key;
	private Long time;
	public Character getKey() {
		return key;
	}
	public void setKey(Character key) {
		this.key = key;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public KeyTime(Character key, Long time) {
		super();
		this.key = key;
		this.time = time;
	}
	
	
}
