/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *<p>标题: ProcessInstanceAction </p>
 *<p>描述：流程实例管理 </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 上午10:31:10
 *@版本 
 */
@Controller
@Scope("prototype")
public class ProcessInstanceAction extends ActionSupport {

	@Resource
	private RuntimeService runtimeService;
	@Resource
	private RepositoryService repositoryService;
	
	private static final Logger logger = Logger.getLogger(ProcessDefinitionAction.class);


	/**
	 * 查询流程实例列表数据
	 * 
	 *@时间 2017年2月21日 上午10:32:48
	 */
	public String list(){
		logger.info("正在查询流程实例列表数据");
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
		query.orderByProcessInstanceId().desc();
		List<ProcessInstance> list = query.list();
		ActionContext.getContext().getValueStack().set("list", list);
		logger.info("查询流程实例列表数据完成。。。。。。。。。。。。。");
		return "list";
	}


	private String id;//接受流程实例id
	
	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 根据流程实例id查询对应的流程变量数据
	 * 
	 *@时间 2017年2月21日 上午10:40:05
	 */
	public String findData() throws Exception{
		Map<String, Object> map= runtimeService.getVariables(id);
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(map.toString());
		return NONE;
	}

	private String	deploymentId;
	private String	imageName;
	/**
	 * 根据流程实例id查询坐标、部署id 图片名称
	 * 
	 *@时间 2017年2月21日 上午10:47:27
	 */
	public String showPng(){
		logger.info("正在  根据流程实例id查询坐标、部署id 图片名称");
		//1、根据流程实例id查询流程实例对象
				ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
				//2、根据流程实例对象查询流程定义id
				String processDefinitionId = processInstance.getProcessDefinitionId();
				//3、根据流程定义id查询流程定义对象
				ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
				//4、根据流程定义对象查询部署id
				deploymentId = processDefinition.getDeploymentId();
				imageName = processDefinition.getDiagramResourceName();
//				String deploymentId = processDefinition.getDeploymentId();
				//查询坐标
				//1、获得当前流程实例执行到哪个节点
				String activityId = processInstance.getActivityId();//usertask1
				//2、加载bpmn（xml）文件，获得一个流程定义对象
				ProcessDefinitionEntity pd = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);//查询act_ge_bytearray
				//3、根据activitiId获取含有坐标信息的对象
				ActivityImpl findActivity = pd.findActivity(activityId);
				int x = findActivity.getX();
				int y = findActivity.getY();
				int width = findActivity.getWidth();
				int height = findActivity.getHeight();
				
				ActionContext.getContext().getValueStack().set("x", x);
				ActionContext.getContext().getValueStack().set("y", y);
				ActionContext.getContext().getValueStack().set("width", width);
				ActionContext.getContext().getValueStack().set("height", height);
				logger.info("根据流程实例id查询坐标、部署id 图片名称完成。。。。。。。。。");
				return "showPng";

	}


	/**
	 * 获取png输入流
	 * 
	 *@时间 2017年2月21日 上午10:57:19
	 */
	public String viewImage(){
		logger.info("正在获取png输入流");
		InputStream pngStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		ActionContext.getContext().getValueStack().set("pngSteam",pngStream);
		logger.info("获取png输入流完成。。。。。。。。。。。");
		return "viewImage";
	}








}
