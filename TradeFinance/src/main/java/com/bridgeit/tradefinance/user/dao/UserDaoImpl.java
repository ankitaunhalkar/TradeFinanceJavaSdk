package com.bridgeit.tradefinance.user.dao;

import com.bridgeit.tradefinance.user.model.User;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements IUserDao {

	@Autowired
	SessionFactory sessionFcatory;

	@Override
	public long save(User user) {
		
		long status = (Long) sessionFcatory.openSession().save(user);
		
		return status;
	}
	
	@SuppressWarnings({ "deprecation" })
	@Override
	public User getByEmail(String email) {
		
		Criteria crt = sessionFcatory.getCurrentSession().createCriteria(User.class);
		
		crt.add(Restrictions.eq("email", email));
		
		User user = (User) crt.uniqueResult();
		
		return (user != null) ? user : null;
	}
	
	@Override
	public boolean update(User user) {
			
		sessionFcatory.getCurrentSession().update(user);
		
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public User getById(long id) {
		
		Criteria crt = sessionFcatory.getCurrentSession().createCriteria(User.class);
		
		crt.add(Restrictions.eq("id", id));
		
		User user = (User) crt.uniqueResult();
		
		return (user != null) ? user : null;
	}
}
