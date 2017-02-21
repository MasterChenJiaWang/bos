/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Workordermanage;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 *<p>标题: WorkordermanageAction </p>
 *<p>描述：工作单管理 </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 下午12:04:16
 *@版本 
 */
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {

	private static final Logger logger = Logger.getLogger(WorkordermanageAction.class);
	
	
	public String add() throws Exception{
		logger.info("正在添加工作单");
		String flag = "1";
		try{
			workordermanageService.save(model);
		}catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		logger.info("添加工作单完成。。。。。。。。。。");
		return NONE;
	}
	
	/**
	 * 查询start 为0的工作单
	 * 
	 *@时间 2017年2月21日 下午12:08:55
	 */
	public String list(){
		logger.info("正在查询start 为0的工作单");
		List<Workordermanage> list=workordermanageService.findListNotStart();
		ActionContext.getContext().getValueStack().set("list", list);
		logger.info("查询start 为0的工作单完成。。。。。。。。。。。。。。");
		return "list";
	}
	
	/**
	 * 启动物流配送流程
	 * 
	 *@时间 2017年2月21日 下午1:47:19
	 */
	public String start(){
		logger.info("正在启动物流配送流程");
		String id = model.getId();//工作单id
		workordermanageService.start(id);//启动流程实例
		logger.info("启动物流配送流程完成");
		return "toList";
	}
	
}
