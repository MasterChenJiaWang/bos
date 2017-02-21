/**
 * 
 */
package com.jiawang.chen.bos.web.action.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.jiawang.chen.bos.crm.CustomerService;
import com.jiawang.chen.bos.service.IDecidedzoneService;
import com.jiawang.chen.bos.service.IFunctionService;
import com.jiawang.chen.bos.service.INoticebillService;
import com.jiawang.chen.bos.service.IRegionService;
import com.jiawang.chen.bos.service.IRoleService;
import com.jiawang.chen.bos.service.IStaffService;
import com.jiawang.chen.bos.service.ISubareaService;
import com.jiawang.chen.bos.service.IUserService;
import com.jiawang.chen.bos.service.IWorkordermanageService;
import com.jiawang.chen.bos.web.utils.PageBean;
import com.jiawang.chen.bos.web.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *<p>标题: BaseAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午4:02:40
 *@版本 
 */

public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{

	@Resource
	protected IUserService userService;
	
	@Resource
	protected IStaffService staffService;
	
	@Resource
	protected IRegionService regionService;
	
	@Resource
	protected ISubareaService subareaService;
	
	@Resource
	protected IDecidedzoneService decidedzoneService;
	
	@Resource
	protected CustomerService customerService;
	
	@Resource
	protected INoticebillService noticebillService;
	
	@Resource
	protected IFunctionService functionService;
	
	@Resource
	protected IRoleService roleService;
	
	@Resource
	protected IWorkordermanageService  workordermanageService;
	//模型对象 
	protected T model;
	protected Class<T> entity;
	protected  PageBean pageBean = new PageBean();
	/* 
	 *
	 *2017年2月15日下午4:03:58
	 */
	@Override
	public T getModel() {
		return model;
	}
	protected int rows;//每页显示的记录数
	protected int page;//页码（0,5）（1,10）

	/**
	 * @param rows 要设置的 rows
	 */
	public void setRows(int rows) {
		pageBean.setPageSize(rows);
				
	}

	/**
	 * @param page 要设置的 page
	 */
	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	
	/**
	 * 
	 */
	public BaseAction(){
		ParameterizedType genericSuperclass = null;
				
			if(this.getClass().getGenericSuperclass() instanceof ParameterizedType){
				genericSuperclass=(ParameterizedType)this.getClass().getGenericSuperclass();
			}else{
				genericSuperclass=(ParameterizedType)this.getClass().getSuperclass().getGenericSuperclass();
			}	
			
//				(ParameterizedType)this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entity=(Class<T>)actualTypeArguments[0];
		DetachedCriteria  detachedCriteria =DetachedCriteria.forClass(entity);
		pageBean.setDetachedCriteria(detachedCriteria);
		try {
			model=entity.newInstance();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
//	public void pageQuery(PageBean pageBean){
//		int currentPage = pageBean.getCurrentPage();
//		int pageSize = pageBean.getPageSize();
//		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
//		detachedCriteria.setProjection(Projections.rowCount());
//		this.
//	}
	
	
	/**
	 * pageBean转json
	 * 
	 *@时间 2017年2月16日 下午3:24:01
	 */
	public void WritePageBean2Json(PageBean pageBean,String[] excludes) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(pageBean,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	/**
	 * 
	 * list转json
	 *@时间 2017年2月16日 下午3:24:22
	 */
	public void WriteList2Json(List<T> list,String[] excludes) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONArray jsonObject = JSONArray.fromObject(list,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}
	
	
	/**
	 * 通用转json
	 * 
	 *@时间 2017年2月20日 下午8:57:55
	 */
	public void WriteObject2Json(Object object,String[] excludes) throws Exception{
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludes);
		JSONObject jsonObject = JSONObject.fromObject(object,jsonConfig);
		String json = jsonObject.toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(json);
	}

	/**
	 * 转城市码
	 * 
	 *@时间 2017年2月16日 下午12:48:32
	 */
	public String string2Citycode(String city){
		
		city=city.substring(0, city.length()-1);
		String[] cityPinyin = PinYin4jUtils.stringToPinyin(city);
		String citycode = StringUtils.join(cityPinyin, "");
		return citycode;
	}
	
	/**
	 * 转城市简码
	 * 
	 *@时间 2017年2月16日 下午12:44:46
	 */
	public String string2Shortcode(String city,String province,String district){
		city=city.substring(0, city.length()-1);
		province=province.substring(0, province.length()-1);
		district=district.substring(0, district.length()-1);
		String info=province+city+district;
		String[] infoPinyin = PinYin4jUtils.getHeadByString(info);
		String shortcode = StringUtils.join(infoPinyin, "");
		return shortcode;
	}
}
