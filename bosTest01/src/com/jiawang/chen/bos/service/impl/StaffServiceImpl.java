/**
 * 
 */
package com.jiawang.chen.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiawang.chen.bos.dao.IStaffDao;
import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.entity.Staff;
import com.jiawang.chen.bos.service.IStaffService;
import com.jiawang.chen.bos.web.action.StaffAction;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: StaffServiceImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午7:07:09
 *@版本 
 */
@Service
@Transactional
public class StaffServiceImpl implements IStaffService{

	private static final Logger logger = Logger.getLogger(StaffServiceImpl.class);
	@Resource
	private IStaffDao staffDao;
	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public void save(Staff staff) {
		logger.info("service-->>>>>取派员正在保存----");
		staffDao.save(staff);
		logger.info("service-->>>>>取派员保存成功----");
	}

	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public void update(Staff staff) {
		logger.info("service-->>>>>取派员正在修改----");
		staffDao.update(staff);
		logger.info("service-->>>>>取派员修改成功----");
	}

	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public void delete(Staff staff) {
		logger.info("service-->>>>>取派员删除----");
		staffDao.delete(staff);
		logger.info("service-->>>>>取派员删除成功----");
	}

	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		staffDao.pageQuery(pageBean);
	}

	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public void deleteBatch(String ids) {
			
		String[] idStaff = ids.split(",");
	//	if(idStaff!=null && idStaff.length>0){
			for(String id:idStaff){
				staffDao.executeUpdate("staff.delete", id);
			}
	//	}
	}

	/* 
	 *
	 *2017年2月15日下午7:07:21
	 */
	@Override
	public Staff findById(String id) {
		
		return staffDao.get(id);
		
	}

	/* 
	 *还原
	 *2017年2月16日下午2:54:05
	 */
	@Override
	public void restoreBatch(String ids) {
		String[] idStaff = ids.split(",");
		if(idStaff!=null && idStaff.length>0){
			for(String id:idStaff){
				Staff staff = staffDao.get(id);
				String deltag = staff.getDeltag();
				if(deltag=="1"){
					staffDao.executeUpdate("staff.restore", id);
				}
				
			}
		}
	}

	/* 
	 *查询没有作废的取派员 
	 *2017年2月16日下午3:28:30
	 */
	@Override
	public List<Staff> findNotDelete() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Staff.class);
		detachedCriteria.add(Restrictions.ne("deltag","1"));
		return staffDao.findByCriteria(detachedCriteria);
		
	}


}
