package com.codecool.seamanager.model.user;

import java.util.Objects;
import java.util.UUID;

public class User {
	private final String id;
	private final String username;
	private final String password; //TODO - pwd hashing and salting
	private int accessLevel;

	public User(String username, String password, int accessLevel) {
		this.id = UUID.randomUUID().toString();
		this.username = username;
		this.password = password;
		this.accessLevel = accessLevel;
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id) && username.equals(user.username);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username);
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", username='" + username + '\'' +
				", accessLevel=" + accessLevel +
				'}';
	}
}
