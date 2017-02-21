/**
 * 
 */
package com.jiawang.chen.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jiawang.chen.bos.dao.IRegionDao;
import com.jiawang.chen.bos.dao.base.impl.BaseDaoImpl;
import com.jiawang.chen.bos.entity.Region;

/**
 *<p>����: RegionDaoImpl </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��16�� ����10:23:40
 *@�汾 
 */
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	/* 
	 *
	 *2017��2��16������5:11:58
	 */
	@Override
	public List<Region> findByQ(String q) {
		String hql="FROM Region WHERE province LIKE ? OR city LIKE ? OR district LIKE ?";
		return this.getHibernateTemplate().find(hql);
	}

	
}
