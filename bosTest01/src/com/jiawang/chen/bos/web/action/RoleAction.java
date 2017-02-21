/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Role;
import com.jiawang.chen.bos.web.action.base.BaseAction;

/**
 *<p>标题: RoleAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午11:33:09
 *@版本 
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final Logger logger = Logger.getLogger(RoleAction.class);
	
	private String ids;

	/**
	 * @return ids
	 */
	public String getIds() {
		return ids;
	}

	/**
	 * @param ids 要设置的 ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}
	

	/**
	 * 
	 * 
	 *@时间 2017年2月20日 下午11:46:21
	 */
	public String add(){
		roleService.add(model,ids);
		logger.info("添加角色权限成功");
		return "list";
	}
	
	
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public String pageQuery() throws Exception{
		logger.info("正在分页查询角色");
		roleService.pageQuery(pageBean);
		//将PageBean对象转为json返回
		this.WriteObject2Json(pageBean, new String[]{"functions","users","currentPage","detachedCriteria","pageSize"});
		logger.info("分页查询角色完成。。。。。。。。。。。。。");
		return NONE;
	}
	
	/**
	 * 查询所有的角色数据，返回json
	 * @return
	 * @throws IOException
	 */
	public String listajax() throws Exception{
		
		logger.info("正在查询所有的角色数据，返回json");
		List<Role> list = roleService.findAll();
		String[] excludes = new String[]{"functions","users"};
		this.WriteList2Json(list, excludes );
		logger.info("查询所有的角色数据 完成。。。。。。。。。。。。。");
		return NONE;
	}
}
