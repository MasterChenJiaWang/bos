/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Noticebill;
import com.jiawang.chen.bos.entity.User;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.jiawang.chen.bos.web.utils.BOSContext;

import cn.jiawang.chen.crm.entity.Customer;

/**
 *<p>标题: NoticebillAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午8:53:17
 *@版本 
 */
@Controller
@Scope("prototype")
public class NoticebillAction  extends BaseAction<Noticebill>{

	private static final Logger logger = Logger.getLogger(NoticebillAction.class);
	
	/**
	 * 
	 * 手机查询客户
	 *@时间 2017年2月20日 下午9:13:23
	 */
	public String findCustomerByTelephone() throws Exception{
		logger.info("正在用手机查询客户");
		String telephone = model.getTelephone();
		Customer customer = customerService.findCustomerByPhonenumber(telephone);
		String[] excludes=new String[]{ }; 
		this.WriteObject2Json(customer, excludes);
		logger.info("手机查询客户完成。。。。。");
		return NONE;
	}
	
	
	public String add(){
		logger.info("正在添加工单");
		User user = BOSContext.getLoginUser();
		model.setUser(user);
		noticebillService.save(model);
		return "addUI";
	}
}
