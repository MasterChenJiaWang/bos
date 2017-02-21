package cn.jiawang.chen.crm.service.impl;

import java.util.List;

import org.hibernate.Session;

import cn.jiawang.chen.crm.entity.Customer;
import cn.jiawang.chen.crm.service.CustomerService;
import cn.jiawang.chen.crm.utils.HibernateUtils;

public class CustomerServiceImpl implements CustomerService {

	public List<Customer> findnoassociationCustomers() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id is null";
		List<Customer> customers = session.createQuery(hql).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public List<Customer> findhasassociationCustomers(String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where decidedzone_id = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, decidedZoneId).list();

		session.getTransaction().commit();
		session.close();

		return customers;
	}

	public void assignCustomersToDecidedZone(Integer[] customerIds, String decidedZoneId) {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		// 取消定区�?��关联客户
		String hql2 = "update Customer set decidedzone_id=null where decidedzone_id=?";
		session.createQuery(hql2).setParameter(0, decidedZoneId).executeUpdate();

		// 进行关联
		String hql = "update Customer set decidedzone_id=? where id =?";
		if (customerIds != null) {
			for (Integer id : customerIds) {
				session.createQuery(hql).setParameter(0, decidedZoneId).setParameter(1, id).executeUpdate();
			}
		}
		session.getTransaction().commit();
		session.close();
	}

	/* 
	 *
	 *2017�?�?1日上�?0:23:33
	 */
	@Override
	public Customer findCustomerByPhonenumber(String phonenumber) {

		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = "from Customer where telephone = ?";
		List<Customer> customers = session.createQuery(hql).setParameter(0, phonenumber).list();

		session.getTransaction().commit();
		session.close();
		
		if(customers!=null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

	/* 
	 *根据区间地址查询定区id
	 *2017�?�?1日上�?1:31:22
	 */
	@Override
	public String findDecidedzoneIdByPickaddress(String address) {

		Session session = HibernateUtils.openSession();
		session.beginTransaction();

		String hql = " select decidedzone_id from Customer where address = ?";
		List<String> customers = session.createQuery(hql).setParameter(0, address).list();

		session.getTransaction().commit();
		session.close();
		
		if(customers!=null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}

}
