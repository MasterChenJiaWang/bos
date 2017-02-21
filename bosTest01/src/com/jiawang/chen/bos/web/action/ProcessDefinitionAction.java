/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *<p>标题: ProcessDefinitionAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 上午9:50:07
 *@版本 
 */
@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends ActionSupport {

	@Resource
	private RepositoryService repositoryService;
	
	private static final Logger logger = Logger.getLogger(ProcessDefinitionAction.class);
	/**
	 * 查询最新版本流程定义列表数据
	 * 
	 *@时间 2017年2月21日 上午9:58:18
	 */
	public String list(){
		logger.info("正在查询最新版本流程定义列表数据");
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.latestVersion();//查询最新版本
		query.orderByProcessDefinitionName().desc();//排序
		List<ProcessDefinition>   list=query.list();//执行查询
		// 压栈
		ActionContext.getContext().getValueStack().set("list", list);
		logger.info("查询最新版本流程定义列表数据完成。。。。。");
		return "list";
	}
	
	
	// 接收上传的文件
		private File zipFile;

		public void setZipFile(File zipFile) {
			this.zipFile = zipFile;
		}
	/**
	 * 部署流程定义
	 * 
	 *@时间 2017年2月21日 上午10:04:59
	 */
	public String deploy( ) throws FileNotFoundException{
		logger.info("正在部署流程定义");
		DeploymentBuilder deploymentBuilder = repositoryService
				.createDeployment();
		deploymentBuilder.addZipInputStream(new ZipInputStream(
				new FileInputStream(zipFile)));
		deploymentBuilder.deploy();
		
		logger.info("部署流程定义完成。。。。");
		return "toList";
	}
	
	
	
	//接收流程定义id
	private String id;
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 展示png图片
	 */
	public String showpng(){
		//获取png图片对应的输入流
		InputStream pngStream = repositoryService.getProcessDiagram(id);
		ActionContext.getContext().getValueStack().set("pngStream", pngStream);
		return "showpng";
	}
	
	/**
	 * 删除流程定义
	 */
	public String delete(){
		String deltag = "0";
		//根据流程定义id查询部署id
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
		query.processDefinitionId(id);//根据id过滤
		ProcessDefinition processDefinition = query.singleResult();
		String deploymentId = processDefinition.getDeploymentId();
		try{
			repositoryService.deleteDeployment(deploymentId);
		}catch (Exception e) {
			//当前要删除的流程定义正在使用
			deltag = "1";
			ActionContext.getContext().getValueStack().set("deltag", deltag);
			ProcessDefinitionQuery query2 = repositoryService
					.createProcessDefinitionQuery();
			query2.latestVersion();// 查询最新版本
			query2.orderByProcessDefinitionName().desc();// 排序
			List<ProcessDefinition> list = query2.list();// 执行查询
			// 压栈
			ActionContext.getContext().getValueStack().set("list", list);
			return "list";
		}
		return "toList";
	}

}
