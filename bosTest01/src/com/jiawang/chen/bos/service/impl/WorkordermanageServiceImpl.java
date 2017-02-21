/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.IWorkordermanageDao;
import com.jiawang.chen.bos.entity.Workordermanage;
import com.jiawang.chen.bos.service.IWorkordermanageService;

/**
 *<p>标题: WorkordermanageServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 下午1:37:11
 *@版本 
 */
@Service
@Transactional
public class WorkordermanageServiceImpl  implements IWorkordermanageService{

	@Resource
	private IWorkordermanageDao workordermanageDao;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	
	@Resource
	private HistoryService historyService;
	/* 
	 *
	 *2017年2月21日下午1:37:28
	 */
	@Override
	public List<Workordermanage> findListNotStart() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Workordermanage.class);
		detachedCriteria.add(Restrictions.eq("start", "0"));
		return workordermanageDao.findByCriteria(detachedCriteria);
	}
	/* 
	 *
	 *2017年2月21日下午1:47:46
	 */
	@Override
	public void start(String id) {
		Workordermanage workordermanage = workordermanageDao.get(id);
		workordermanage.setStart("1");//已启动
		String processDefinitionKey="transfer";//流程定义key
		String businessKey=id;//业务主键 。。。。等于业务表（工作单）主键值
		Map<String, Object> map = new HashMap<String,Object>();//流程变量
		map.put("业务数据", workordermanage);
		runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey,map);
	}
	/* 
	 *
	 *2017年2月21日下午1:56:19
	 */
	@Override
	public void save(Workordermanage model) {
		
		workordermanageDao.save(model);
	}
	/* 
	 *
	 *2017年2月21日下午2:21:43
	 */
	@Override
	public Workordermanage findById(String workordermanageId) {
		
		Workordermanage workordermanage = workordermanageDao.get(workordermanageId);
		return workordermanage;
	}
	/* 
	 *
	 *2017年2月21日下午2:24:03
	 */
	@Override
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId) {
		//如果审核不通过，修改工作单start为0
		Workordermanage workordermanage = workordermanageDao.get(workordermanageId);
		//审核不通过
		//删除历史流程实例数据
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		//根据任务对象查询流程实例Id
		Map<String, Object> variables = new HashMap<String,Object>();
		variables.put("check", check);
		//办理审核工作单任务
		String processInstanceId = task.getProcessInstanceId();
		taskService.complete(taskId, variables);
		if(check==0){
			workordermanage.setStart("0");
			historyService.deleteHistoricProcessInstance(processInstanceId);
		}
	}

}
