package com.ssafy.jhtrip.user.model.dto;

public class Security {
	private String userId;
	private String salt;
	private int iteration;
	private String algo;
	
	public Security() {}

	public Security(String userId, String salt, int iteration, String algo) {
		super();
		this.userId = userId;
		this.salt = salt;
		this.iteration = iteration;
		this.algo = algo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getIteration() {
		return iteration;
	}

	public void setIteration(int iteration) {
		this.iteration = iteration;
	}

	public String getAlgo() {
		return algo;
	}

	public void setAlgo(String algo) {
		this.algo = algo;
	}

	@Override
	public String toString() {
		return "Security [userId=" + userId + ", salt=" + salt + ", iteration=" + iteration + ", algo=" + algo + "]";
	}
	
}
