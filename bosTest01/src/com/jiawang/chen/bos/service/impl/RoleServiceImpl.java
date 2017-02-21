/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.IRoleDao;
import com.jiawang.chen.bos.entity.Function;
import com.jiawang.chen.bos.entity.Role;
import com.jiawang.chen.bos.service.IRoleService;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: RoleServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午11:36:25
 *@版本 
 */
@Service
@Transactional
public class RoleServiceImpl  implements IRoleService{

	@Resource
	private  IRoleDao roleDao;
	@Resource
	private IdentityService IdentityService;
	/* 
	 *
	 *2017年2月20日下午11:36:51
	 */
	@Override
	public void add(Role model,String ids) {
		roleDao.save(model);
//		String[] functionIds = ids.split(",");
//		for(String fid:functionIds){
//			Function function = new Function(fid);
//			//角色关联
//			model.getFunctions().add(function);
//		}
		//使用角色的名称作为组的id
		Group group = new GroupEntity(model.getName());
		IdentityService.saveGroup(group);
		String[] functionIds = ids.split(",");
		for(String fid:functionIds){
			Function function = new Function(fid);
			//角色关联
			model.getFunctions().add(function);
		}
	}
	/* 
	 *
	 *2017年2月20日下午11:49:02
	 */
	@Override
	public List<Role> findAll() {
		List<Role> list = roleDao.find();
		return list;
	}
	/* 
	 *
	 *2017年2月20日下午11:49:02
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		
		roleDao.pageQuery(pageBean);
	}

//	/**
//	 * 
//	 * 
//	 *@时间 2017年2月21日 上午11:07:47
//	 */
//	public void save(Role role,String ids){
//		
//		roleDao.
//	}
	
	
	
}
