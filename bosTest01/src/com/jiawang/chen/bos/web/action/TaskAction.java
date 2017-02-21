/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Workordermanage;
import com.jiawang.chen.bos.service.IWorkordermanageService;
import com.jiawang.chen.bos.web.utils.BOSContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *<p>标题: TaskAction </p>
 *<p>描述：组任务 </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 下午1:54:26
 *@版本 
 */
@Controller
@Scope("prototype")
public class TaskAction extends ActionSupport {

	@Resource
	private TaskService taskService;
	@Resource
	private IWorkordermanageService workordermanageService;
	@Resource
	private RuntimeService runtimeService;
	
	private static final Logger logger = Logger.getLogger(TaskAction.class);
	/**
	 * 查询组任务
	 * 
	 *@时间 2017年2月21日 下午1:59:28
	 */
	public String findGroupTask(){
		logger.info("正在查询组任务");
		TaskQuery query = taskService.createTaskQuery();
		String candidateUser = BOSContext.getLoginUser().getId();
		//组任务过滤
		query.taskCandidateUser(candidateUser);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		logger.info("查询组任务完成。。。。。。。。。");
		return "grouptasklist";
	}
	
	
	private String taskId;
	
	/**
	 * @return taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId 要设置的 taskId
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	/**
	 * 拾取组任务
	 * 
	 *@时间 2017年2月21日 下午2:07:16
	 */
	public String showData(){
		logger.info("正在拾取组任务");
		String userId = BOSContext.getLoginUser().getId();
		taskService.claim(taskId, userId);
		logger.info("拾取组任务完成。。。。。。。。。。。。。");
		return "togrouptasklist";
	}
	
	/**
	 * 
	 * 查询个人任务
	 *@时间 2017年2月21日 下午2:12:39
	 */
	public String findPersonalTask(){
		logger.info("正在查询个人任务");
		TaskQuery query = taskService.createTaskQuery();
		String assignee = BOSContext.getLoginUser().getId();
		//个人任务过滤
		query.taskAssignee(assignee);
		List<Task> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		logger.info("查询个人任务完成。。。。。。。。。。。。。。。。");
		return "personaltasklist";
	}
	
	private Integer  check;
	


	/**
	 * @return check
	 */
	public Integer getCheck() {
		return check;
	}

	/**
	 * @param check 要设置的 check
	 */
	public void setCheck(Integer check) {
		this.check = check;
	}

	/**
	 * 办理审核工作单任务
	 * 
	 *@时间 2017年2月21日 下午2:18:45
	 */
	public String checkworkorderManage(){
		logger.info("正在办理审核工作单任务");
		// 根据任务id查询任务对象
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				// 根据任务对象查询流程实例id
				String processInstanceId = task.getProcessInstanceId();
				// 根据流程实例id查询流程实例对象
				ProcessInstance processInstance = runtimeService
						.createProcessInstanceQuery()
						.processInstanceId(processInstanceId).singleResult();
				String workordermanageId = processInstance.getBusinessKey();
				Workordermanage workordermanage = workordermanageService.findById(workordermanageId);
				if(check == null){
					//跳转到审核页面
					// 跳转到一个审核工作单页面，展示当前对应的工作单信息
					ActionContext.getContext().getValueStack().set("map", workordermanage);
					logger.info("办理审核工作单任务  失败《《《");
					return "check";
				}else{
					workordermanageService.checkWorkordermanage(taskId,check,workordermanageId);
					logger.info("办理审核工作单任务完成。。。。。。。。。。。。。");
					return "topersonaltasklist";
				}
	}
	
	
	/**
	 * 
	 * 办理出库任务
	 *@时间 2017年2月21日 下午2:34:11
	 */
	public String outStore(){
		logger.info("正在办理出库任务");
		taskService.complete(taskId);
		logger.info("办理出库任务完成。。。。。。。。。。。。");
		return "topersonaltasklist";
	}
	
	/**
	 * 办理配送任务
	 * 
	 *@时间 2017年2月21日 下午2:35:53
	 */
	public String transferGoods(){
		logger.info("正在办理配送任务");
		taskService.complete(taskId);
		
		logger.info("办理配送任务完成。。。。。。。。。。。。。");
		return "topersonaltasklist";
	}
	
	/**
	 * 
	 * 办理签收任务
	 *@时间 2017年2月21日 下午2:36:15
	 */
	public String receive(){
		logger.info("正在办理签收任务");
		taskService.complete(taskId);
		logger.info("办理签收任务完成。。。。。。。。。。。");
		return "topersonaltasklist";
	}
}
