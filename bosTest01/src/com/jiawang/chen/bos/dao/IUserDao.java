/**
 * 
 */
package com.jiawang.chen.bos.dao;


import com.jiawang.chen.bos.dao.base.IBaseDao;
import com.jiawang.chen.bos.entity.User;

/**
 *<p>标题: IUserDao </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午3:42:53
 *@版本 
 */
public interface IUserDao extends IBaseDao<User>{

	public User loginByUserAndPassword(String username,String password);
	public User findUserByUsername(String username);
}
