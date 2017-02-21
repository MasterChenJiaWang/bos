/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IUserService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午3:59:03
 *@版本 
 */
public interface IUserService {
	public User loginByUsernameAndPassword(String username,String password);
	public void editpassword(String password,String id);
	/**
	 * 
	 *@时间 2017年2月21日 上午8:17:35
	 */
	public void save(User user, String[] roleIds);
	/**
	 * 
	 *@时间 2017年2月21日 上午8:50:02
	 */
	public void pageQuery(PageBean pageBean);
	/**
	 * 添加用户
	 *@时间 2017年2月21日 上午8:52:28
	 */
	public void add(User model);
	/**
	 * 
	 *@时间 2017年2月21日 上午8:55:00
	 */
	public User login(User model);
	
}
