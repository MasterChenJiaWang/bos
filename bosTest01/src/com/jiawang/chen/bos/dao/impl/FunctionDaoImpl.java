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
 *<p>����: FunctionDaoImpl </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��20�� ����10:48:56
 *@�汾 
 */
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao{

	/* 
	 *�����û�id ��ѯ��Ӧ��Ȩ��
	 *2017��2��21������8:35:25
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
	 *��ѯ���еĲ˵�
	 *2017��2��21������9:27:24
	 */
	@Override
	public List<Function> findAllMenu() {
    
		String hql="FROM Function f WHERE f.generatemenu='1' ORDER BY f.zindex DESC";
		
		return this.getHibernateTemplate().find(hql);
	}

	/* 
	 *�����û�id��ѯ��Ӧ�Ĳ˵�
	 *2017��2��21������9:27:24
	 */
	@Override
	public List<Function> findMenuByUserid(String id) {
		String hql="select distinct f from Function f left outer join f.roles r"
				+ "left outer join r.roles u where u.id=? and f.generatemenu='1' order by f.zindex desc";
		return this.getHibernateTemplate().find(hql, id);
				
	}

}
