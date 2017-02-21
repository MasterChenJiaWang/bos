/**
 * 
 */
package com.jiawang.chen.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jiawang.chen.bos.dao.IFunctionDao;
import com.jiawang.chen.bos.dao.base.impl.BaseDaoImpl;
import com.jiawang.chen.bos.entity.Function;

/**
 *<p>标题: FunctionDaoImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午10:48:56
 *@版本 
 */
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	/* 
	 *根据用户id 查询对应的权限
	 *2017年2月21日上午8:35:25
	 */
	@Override
	public List<Function> findListByUserid(String userid) {
		String hql="select distinct f from Function f left outer join f.roles"
				+ "r left outer join r.users u where u.id=?";
		//List<Function> list= (List<Function>)this.getHibernateTemplate().get(hql, userid);
		List<Function> list = this.getHibernateTemplate().find(hql, userid);
		return list;
	}

	/* 
	 *查询所有的菜单
	 *2017年2月21日上午9:27:24
	 */
	@Override
	public List<Function> findAllMenu() {
    
		String hql="FROM Function f WHERE f.generatemenu='1' ORDER BY f.zindex DESC";
		
		return this.getHibernateTemplate().find(hql);
	}

	/* 
	 *根据用户id查询对应的菜单
	 *2017年2月21日上午9:27:24
	 */
	@Override
	public List<Function> findMenuByUserid(String id) {
		String hql="select distinct f from Function f left outer join f.roles r"
				+ "left outer join r.roles u where u.id=? and f.generatemenu='1' order by f.zindex desc";
		return this.getHibernateTemplate().find(hql, id);
				
	}

}
