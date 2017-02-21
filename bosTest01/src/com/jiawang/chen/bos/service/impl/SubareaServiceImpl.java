/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.ISubareaDao;
import com.jiawang.chen.bos.entity.Subarea;
import com.jiawang.chen.bos.service.ISubareaService;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: SubareaServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月17日 上午9:32:16
 *@版本 
 */
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {

	@Resource
	private ISubareaDao subareaDao;
	/* 
	 *
	 *2017年2月17日上午9:36:14
	 */
	@Override
	public void add(Subarea subarea) {
			subareaDao.save(subarea);
	}

	/* 
	 *
	 *2017年2月17日上午9:36:14
	 */
	@Override
	public void edit(Subarea subarea) {
		subareaDao.update(subarea);
	}

	/* 
	 *
	 *2017年2月17日上午9:36:14
	 */
	@Override
	public void delete(Subarea subarea) {
		subareaDao.delete(subarea);
	}

	/* 
	 *
	 *2017年2月17日上午9:36:14
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		subareaDao.pageQuery(pageBean);
	}

	/* 
	 *
	 *2017年2月17日上午10:06:59
	 */
	@Override
	public List<Subarea> findAll() {
		return subareaDao.find();
	}

	/* 
	 *
	 *2017年2月17日下午4:08:57
	 */
	@Override
	public List<Subarea> findListNotAssociation() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findByCriteria(detachedCriteria);
	}

}
