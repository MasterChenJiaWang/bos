/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Function;
import com.jiawang.chen.bos.web.action.base.BaseAction;

/**
 *<p>标题: FunctionAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午10:44:49
 *@版本 
 */
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	private static final Logger logger = Logger.getLogger(FunctionAction.class);
	
	
	public String pageQuery() throws Exception{
		functionService.pageQuery(pageBean);
		String[] excludes=new String[]{"function","functions","roles","currentPage","detachedCriteria","pageSize"};
		this.WriteObject2Json(pageBean, excludes);
		logger.info("权限分页成功");
		return NONE;
	}
	
	/**
	 * 
	 * 
	 *@时间 2017年2月20日 下午10:53:45
	 */
	public String listajax() throws Exception{
		List<Function> list = functionService.findAll();
		String[] excludes = new String[]{"function","functions","roles"};
		this.WriteList2Json(list, excludes );
		logger.info("权限异步请求成功");
		return NONE;
	}
	
	/**
	 * 添加权限
	 */
	public String add(){
		functionService.add(model);
		return "list";
	}
	
	/**
	 * 
	 * 根据登陆人查询对应的菜单数据（从权限表中查询）
	 *@时间 2017年2月21日 上午9:23:26
	 */
	public String findMenu() throws Exception{
		List<Function> list=functionService.findMenu();
		String[] excludes=new String[]{"functions","function","roles"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}
}
