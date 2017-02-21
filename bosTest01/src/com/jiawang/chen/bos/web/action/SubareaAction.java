/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.entity.Subarea;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.jiawang.chen.bos.web.utils.FileUtils;

/**
 *<p>标题: SubareaAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月17日 上午9:29:01
 *@版本 
 */
@Controller
@Scope("prototype")
public class SubareaAction  extends BaseAction<Subarea>{

	private static final Logger logger = Logger.getLogger(SubareaAction.class);
	
	/**
	 * 添加
	 * 
	 *@时间 2017年2月17日 上午9:41:34
	 */
	public String add(){
		logger.info("分区正在添加！");
		subareaService.add(model);
		logger.info("分区添加完成！");
		return "list";
	}
	
	
	/**
	 * 
	 * 修改
	 *@时间 2017年2月17日 上午9:41:47
	 */
	public String edit(){
		logger.info("分区正在修改！");
		subareaService.edit(model);
		logger.info("分区修改完成！");
		return "list";
	}
	
	
	/**
	 * 删除
	 * 
	 *@时间 2017年2月17日 上午9:42:32
	 */
	public String delete(){
		logger.info("分区正在删除！");
		subareaService.delete(model);
		logger.info("分区删除完成！");
		return "list";
	}
	
	
	/**
	 *分页
	 * 
	 *@时间 2017年2月17日 上午9:43:35
	 */
	public String pageQuery()throws Exception{
				logger.info("分区分页正在进行.......");
				//在查询之前 封装条件
				DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
				String addresskey = model.getAddresskey();
				Region region = model.getRegion();
				if(StringUtils.isNotBlank(addresskey)){
					//按照地址关键字模糊查询
					detachedCriteria2.add(Restrictions.like("addressKey", addresskey));
				}
				if(region!=null){
					//创建别名，用于夺标关联查询
					detachedCriteria2.createAlias("region", "r");
					String province = region.getProvince();
					String city = region.getCity();
					String district = region.getDistrict();
					if(StringUtils.isNotBlank(province)){
						//按照省进行模糊查询
						detachedCriteria2.add(Restrictions.like("r.province","%"+province+"%"));
					}
					if(StringUtils.isNotBlank(city)){
						//按照市进行模糊查询
						detachedCriteria2.add(Restrictions.like("r.city","%"+city+"%"));
					}
					if(StringUtils.isNotBlank(district)){
						//按照区进行模糊查询
						detachedCriteria2.add(Restrictions.like("r.district","%"+district+"%"));
					}
				}
				subareaService.pageQuery(pageBean);
				String[] executes=new String[]{"detachedCriteria","currentpage","pageSize","decidedzone","subareas","decidedzones"};
				//String[] executes=new String[]{"detachedCriteria","currentpage","pageSize","decidedzones","subareas","decidedzone"};
				this.WritePageBean2Json(pageBean, executes);
				logger.info("分区分页完成");
				return NONE;
	}
	
	
	/**
	 * 
	 * 
	 *@时间 2017年2月17日 上午10:05:23
	 */
	public String exportXls() throws IOException{
		logger.info("导出分区正在进行...............");
		List<Subarea> list = subareaService.findAll();
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet("分区数据");
		
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");
		
		for(Subarea subarea:list){
			//在首行下创建一行
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			
			Region region = subarea.getRegion();
			
			dataRow.createCell(3).setCellValue(region.getName());
			}
			String filename="分区数据.xls";
			String agent = ServletActionContext.getRequest().getHeader("User-Agent");
			filename = FileUtils.encodeDownloadFilename(filename, agent);
			//一个流两个头
			ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
			String contentType = ServletActionContext.getServletContext().getMimeType(filename);
			ServletActionContext.getResponse().setContentType(contentType);
			ServletActionContext.getResponse().setHeader("content-disposition", "attchment;filename="+filename);
			workbook.write(out);
			return NONE;
	}
	
	
	public String listajax() throws Exception{
		List<Subarea> list= subareaService.findListNotAssociation();
		String[] excludes=new String[]{"decidedzone","region"};
		this.WriteList2Json(list, excludes);
		return NONE;
	}
}
