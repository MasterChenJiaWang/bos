/**
 * 
 */
package com.jiawang.chen.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jiawang.chen.bos.dao.IUserDao;
import com.jiawang.chen.bos.dao.base.impl.BaseDaoImpl;
import com.jiawang.chen.bos.entity.User;

/**
 *<p>标题: UserDaoImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午3:43:24
 *@版本 
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	/* 
	 *
	 *2017年2月15日下午3:50:52
	 */
	@Override
	public User loginByUserAndPassword(String username, String password) {
//		String hql="from User u where u.username='"+username+"' and u.password='"+password+"'";
		String hql="from User u where u.username=? and u.password=?";
		List<User> list = this.getHibernateTemplate().find(hql, username,password);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	/* 
	 *
	 *2017年2月20日下午10:03:12
	 */
	@Override
	public User findUserByUsername(String username) {
		String hql = "FROM User u WHERE u.username = ? ";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

}
