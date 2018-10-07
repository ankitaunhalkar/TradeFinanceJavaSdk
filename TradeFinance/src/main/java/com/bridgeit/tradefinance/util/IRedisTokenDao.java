package com.bridgeit.tradefinance.util;

public interface IRedisTokenDao {
	void setToken(String key, String uname);
	String getToken(String key);
	void deleteToken(String key);
}
