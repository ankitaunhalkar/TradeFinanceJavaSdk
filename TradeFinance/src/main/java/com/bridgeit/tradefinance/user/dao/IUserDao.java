package com.bridgeit.tradefinance.user.dao;

import com.bridgeit.tradefinance.user.model.User;

public interface IUserDao {
	long save(User user);
	User getByEmail(String email);
	User getById(long id);
	boolean update(User user);
}
