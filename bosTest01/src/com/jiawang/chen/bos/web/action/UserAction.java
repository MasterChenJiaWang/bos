/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Role;
import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.service.IUserService;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.jiawang.chen.bos.web.utils.MD5Utils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

/**
 *<p>标题: UserAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午4:10:53
 *@版本 
 */
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	
	private static final Logger logger = Logger.getLogger(UserAction.class);
	private String checkcode;
	/**
	 * @param checkcode 要设置的 checkcode
	 */
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	public String login(){
		logger.info("正在登陆------------------------------");
		ValueStack stack = ActionContext.getContext().getValueStack();
		//生成的验证码
		String key=(String)ServletActionContext.getRequest().getSession().getAttribute("key");
		
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(key) && checkcode.equals(key)){
			String username = model.getUsername();
			String password = model.getPassword();
			logger.info("正在登陆------------------------------"+username);
			Subject subject = SecurityUtils.getSubject();//状态为“未认证”
			password = MD5Utils.md5(password);
			
			UsernamePasswordToken token = new UsernamePasswordToken(model.getUsername(), password);
			
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				e.printStackTrace();
				this.addActionError(this.getText("usernamenotfound"));
				logger.info("登录失败----");
				return "login";
			}catch (Exception e) {
				e.printStackTrace();
				//设置错误信息
				this.addActionError(this.getText("loginError"));
				return "login";
			}
			//获取认证信息对象中存储的User对象
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			logger.info("登录成功----");
			return "home";
		}else{
			//验证码错误,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("validateCodeError"));
			logger.info("验证码失败----");
			return "login";
		}	
			//	User user = userService.loginByUsernameAndPassword(username, password);
//			if(user!=null){
//				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
//				logger.info("登录成功----");
//				return "home";
//			}else{
//				this.addActionError(this.getText("loginError"));
//				logger.info("登录失败----");
//				return "login";
//			}
//		}else{
//			this.addActionError(this.getText("validateCodeError"));
//			logger.info("验证码错误----");
//			return "login";
//		}
	}
	
	
	public String login_back(){
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		
		//生成的验证码
		String key = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		
		//判断用户输入的验证码是否正确
		if(StringUtils.isNotBlank(checkcode) && checkcode.equals(key)){
			//验证码正确
			User user = userService.login(model);
			if(user != null){
				//登录成功,将User放入session域，跳转到系统首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return "home";
			}else{
				//登录失败，设置错误提示信息，跳转到登录页面
				this.addActionError(this.getText("loginError"));
				return "login";
			}
		}else{
			//验证码错误,设置错误提示信息，跳转到登录页面
			this.addActionError(this.getText("validateCodeError"));
			return "login";
		}
	}
	
	/**
	 * 注销用户
	 * 
	 *@时间 2017年2月16日 上午8:56:21
	 */
	public String logout(){
		logger.info("正在注销----");
		ServletActionContext.getRequest().getSession().invalidate();
		logger.info("注销成功----");
		return "login";
	}
	
	@RequiresPermissions(value="staff")
	@RequiresRoles(value="abc")
	public void eidtPassword() throws Exception{
		logger.info("正在修改用户密码----");
		User user=(User)ServletActionContext.getRequest().getSession().getAttribute("loginUser");
		
		String password = model.getPassword();
		password= MD5Utils.md5(password);
		String flag="1";
		String id = user.getId();
		try {
			userService.editpassword(password, id);
			logger.info("修改密码成功----");
		} catch (Exception e) {
			logger.info("修改密码失败----");
			flag="0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
	}
	
	
	//接收角色数据
		private String[] roleIds;
		
		
	/**
		 * @return roleIds
		 */
		public String[] getRoleIds() {
			return roleIds;
		}

		/**
		 * @param roleIds 要设置的 roleIds
		 */
		public void setRoleIds(String[] roleIds) {
			this.roleIds = roleIds;
		}

/**
 * 添加用户角色
 * 
 *@时间 2017年2月21日 上午9:02:05
 */
	public String add(){
		logger.info("正在添加用户角色");
		userService.save(model, roleIds);
		logger.info("添加用户角色完成");
		return "list";
	}
	
	/**
	 * 用户分页查询
	 * @throws IOException 
	 */
	public String pageQuery() throws Exception{
		logger.info("正在用户分页查询");
		userService.pageQuery(pageBean);
		String[] excludes = new String[]{"noticebills","roles"};
		this.WriteObject2Json(pageBean, excludes );
		logger.info("用户分页查询完成");
		return NONE;
	}
	
	
	
}
