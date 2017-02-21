/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.IRoleDao;
import com.jiawang.chen.bos.dao.IUserDao;
import com.jiawang.chen.bos.entity.Role;
import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.service.IUserService;
import com.jiawang.chen.bos.web.utils.MD5Utils;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: UserServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午3:57:00
 *@版本 
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private  IUserDao userDao;
	
	@Resource
	private IdentityService identityService;
	
	@Resource
	private IRoleDao roleDao;
	/* 
	 *
	 *2017年2月15日下午3:57:17
	 */
	@Override
	public User loginByUsernameAndPassword(String username, String password) {

		return userDao.loginByUserAndPassword(username, password);
	}
	/* 
	 *修改密码
	 *2017年2月16日上午9:02:20
	 */
	@Override
	public void editpassword(String password, String id) {
		userDao.executeUpdate("editPassword",password,id);
	}
	/* 
	 *保存一个用户  同步到activiti的act_id_user  act_id_membership
	 *2017年2月21日上午8:17:50
	 */
	public void save(User user, String[] roleIds) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		user.setPassword(password);
		userDao.save(user);//持久对象 
//		for (String roleId : roleIds) {
//			Role role = new Role(roleId);
//			//用户关联角色
//			user.getRoles().add(role);
//		}
		
		org.activiti.engine.identity.User actUser = new UserEntity(user.getId());
		
		identityService.saveUser(actUser);
		for (String roleId : roleIds) {
			Role role = roleDao.get(roleId);
			//用户关联角色
			user.getRoles().add(role);
			identityService.createMembership(actUser.getId(), role.getName());
		}
		
	}
	/* 
	 *
	 *2017年2月21日上午8:50:11
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}
	/* 
	 *
	 *2017年2月21日上午8:55:06
	 */
	@Override
	public void add(User model) {
		String username = model.getUsername();
		String password = model.getPassword();//明文
		password = MD5Utils.md5(password);//md5加密
		model.setPassword(password);
		userDao.save(model);
	}
	/* 
	 *
	 *2017年2月21日上午8:55:06
	 */
	@Override
	public User login(User model) {
		String username = model.getUsername();
		String password = model.getPassword();//明文
		password = MD5Utils.md5(password);//md5加密
		return userDao.loginByUserAndPassword(username,password);
	}

}
