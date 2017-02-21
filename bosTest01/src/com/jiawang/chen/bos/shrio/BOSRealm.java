/**
 * 
 */
package com.jiawang.chen.bos.shrio;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.jiawang.chen.bos.dao.IFunctionDao;
import com.jiawang.chen.bos.dao.IUserDao;
import com.jiawang.chen.bos.entity.Function;
import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.web.action.DecidedzoneAction;

/**
 *<p>标题: BOSRealm </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午9:56:38
 *@版本 
 */
public class BOSRealm extends AuthorizingRealm {

	@Resource
	private IUserDao userDao;
	
	@Resource
	private IFunctionDao functionDao;
	
	private static final Logger logger = Logger.getLogger(BOSRealm.class);
	
	/* 
	 *授权方法
	 *2017年2月20日下午9:58:30
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
	
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//		info.addStringPermission("staff");//为当前用户授予staff权限
//		//TODO 根据当前登录用户查询数据库，获取其对应的权限数据
//		info.addRole("staff");//为当前用户授予staff角色
		
		User user=(User)principals.getPrimaryPrincipal();
		List<Function> list=null;
		if(user.getUsername().equals("chenjiawang")){
			 list = functionDao.find();
		}else{
			list=functionDao.findListByUserid(user.getId());
		}
		for(Function function:list){
			info.addStringPermission(function.getCode());
		}
		return info;
		
		

	}

	/* 
	 * 认证方法
	 *2017年2月20日下午9:58:30
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		logger.info("认证方法。。。。");
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username = upToken.getUsername();//从令牌中获得用户名
		
		logger.info("令牌中的用户名"+username);
		
		User user = userDao.findUserByUsername(username);// 从令牌中获得用户名
		if (user == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			String password = user.getPassword();// 获得数据库中存储的密码
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象
			 * 参数二：从数据库中查询出的密码
			 * 参数三：当前realm的名称
			 */
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,
					password, this.getClass().getSimpleName());
			return info;//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
		}	
	}

}
