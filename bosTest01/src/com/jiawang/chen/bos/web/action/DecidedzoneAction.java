/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Decidedzone;
import com.jiawang.chen.bos.web.action.base.BaseAction;

/**
 *<p>标题: DecidedzoneAction </p>
 *<p>描述：定区 </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月17日 下午4:29:09
 *@版本 
 */
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	private static final Logger logger = Logger.getLogger(DecidedzoneAction.class);
	
	//接受分区Id
	private String[] subareaid;
	
	private Integer[] customerIds;
	
	/**
	 * @param subareaid 要设置的 subareaid
	 */
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}

	/**
	 * 
	 * 
	 *@时间 2017年2月17日 下午4:31:27
	 */
	public String add(){
		logger.info("正在进行定区添加");
		decidedzoneService.add(model,subareaid);
		logger.info("定区添加完成！");
		return "list";
	}
	
	/**
	 * 
	 * 
	 *@时间 2017年2月17日 下午4:41:15
	 */
	public String delete(){
		logger.info("正在进行定区删除！");
		decidedzoneService.delete(model);
		logger.info("定区删除完成！");
		return "list";
	}
	
	/**
	 * 
	 * 
	 *@时间 2017年2月17日 下午4:42:08
	 */
	public String edit(){
		logger.info("正在进行定区修改！");
		decidedzoneService.edit(model);
		logger.info("定区修改完成！");
		return "list";
	}
	
	
	public String pageQuery() throws Exception{
		decidedzoneService.pageQuery(pageBean);
		String[] excludes=new String[]{"decidedzones","subareas","currentPage","detachedCriteria","pageSize"};
		this.WritePageBean2Json(pageBean, excludes);
		return NONE;
	}
	
	public String assigncustomerstodecidedzone(){
		customerService.assignCustomersToDecidedZone(customerIds,model.getId());
		return "list";
	}
}
