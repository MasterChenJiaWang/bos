/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.io.IOError;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Staff;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.jiawang.chen.bos.web.utils.PageBean;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *<p>标题: StaffAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午7:19:08
 *@版本 
 */
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>{

	private static final Logger logger = Logger.getLogger(StaffAction.class);
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}

	
	/**
	 * 取派员 分页
	 * 
	 *@时间 2017年2月16日 上午8:44:23
	 */
	public String pageQuery() throws Exception{
		
		logger.info("正在进行取派员分页-------");
		staffService.pageQuery(pageBean);
		//将PageBean对象转为json返回
		String[] excludes=new String[]{"decidedzones","currentPage","detachedCriteria","pageSize"};
		this.WritePageBean2Json(pageBean, excludes);
		logger.info("取派员分页成功-------");
		return NONE;
	}
	
	/**
	 * 取派员 添加
	 * 
	 *@时间 2017年2月16日 上午8:46:44
	 */
	public String  add(){
		logger.info("正在添加 取派员-------");
		staffService.save(model);
		logger.info("取派员添加成功-------");
		return "list";
	}
	
	/**
	 * 删除取派员  批量删除
	 * 
	 *@时间 2017年2月16日 上午8:50:20
	 */
	public String delete(){
		logger.info("正在进行取派员删除-------");
	//	String ids = model.getId();
		staffService.deleteBatch(ids);
		logger.info("取派员删除成功-------");
		return "list";
	}
	
	/**
	 *还原 
	 *@时间 2017年2月16日 下午3:17:24
	 */
	public String restore( ){
		logger.info("正在进行取派员还原-------");
	//	String ids = model.getId();
		staffService.restoreBatch(ids);
		logger.info("取派员还原成功-------");
		return "list";
	}
	
	/**
	 * 查询
	 * 
	 *@时间 2017年2月16日 下午3:26:21
	 */
	public String listajax() throws Exception{
		 List<Staff> list = staffService.findNotDelete();
		 String[] excludes=new String[]{"decidedzones"};
		this .WriteList2Json(list, excludes);
			return NONE;
	}
}
