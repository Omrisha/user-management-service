package demo.layout;

import javax.validation.constraints.NotBlank;

public class NameBoundary {
	@NotBlank
	private String first;
	@NotBlank
	private String last;
	
	public NameBoundary() {
	}

	public NameBoundary(String first, String last) {
		this.first = first;
		this.last = last;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}
	
	

}
