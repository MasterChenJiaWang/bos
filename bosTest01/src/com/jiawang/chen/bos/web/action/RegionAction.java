/**
 * 
 */
package com.jiawang.chen.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.junit.Test.None;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jiawang.chen.bos.entity.Region;
import com.jiawang.chen.bos.entity.Staff;
import com.jiawang.chen.bos.web.action.base.BaseAction;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: RegionAction </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午11:24:46
 *@版本 
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	private static final Logger logger = Logger.getLogger(RegionAction.class);
	private File myFile;
	
	/**
	 * @param myFile 要设置的 myFile
	 */
	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}
	
	/**
	 * 
	 * 
	 *@时间 2017年2月17日 上午9:15:07
	 */
	public String add(){
		logger.info("正在进行区域添加-------");
		regionService.save(model);
		logger.info("区域添加成功-------");
		return "list";
	}
	
	
	/**
	 * 
	 * 修改
	 *@时间 2017年2月17日 上午9:15:21
	 */
	public String edit(){
		logger.info("正在进行区域修改-------");
		regionService.update(model);
		logger.info("区域修改成功-------");
		return "list";
	}
	
	
	/**
	 * 导入
	 * 
	 *@时间 2017年2月16日 下午1:47:47
	 */
	public String importXls() throws Exception{
		String flag="1";
		logger.info("正在进行区域导入-------");
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(myFile));
			//获得第一个sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<>();
			for(Row row:sheet){
				//得到行数
				int rowNum = row.getRowNum();
				if(rowNum==0){
					//第一行，标题行，忽略
					continue;
				}
				
				String id = row.getCell(0).getStringCellValue();//id
				String province = row.getCell(1).getStringCellValue();//省份
				String city= row.getCell(2).getStringCellValue();//城市
				String district = row.getCell(3).getStringCellValue();//区域·
				String postcode = row.getCell(4).getStringCellValue();//邮编
				String citycode = string2Citycode(city);
				String shortcode = string2Shortcode(city, province, district);
				Region region = new Region(id, province, city, district, postcode, shortcode, citycode, null);
				list.add(region);
			}
			regionService.saveBacth(list);
		} catch (Exception e) {
			flag="0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		logger.info("区域导入成功-------");
		return NONE;

	}
	
	
	/**
	 * 分页
	 * 
	 *@时间 2017年2月16日 下午1:31:09
	 */
	public String pageQuery( ) throws Exception{
		logger.info("正在进行区域分页-------");
		regionService.pageQuery(pageBean);
		String[] excludes=new String[]{"decidedzones","currentPage","detachedCriteria","pageSize","subareas"};
		WritePageBean2Json(pageBean, excludes);
		logger.info("区域分页成功-------");
		return NONE;
	}
	
	
	//模糊查询条件
	private String q;
	
	/**
	 * @return q
	 */
	public String getQ() {
		return q;
	}
	
	/**
	 * @param q 要设置的 q
	 */
	public void setQ(String q) {
		this.q = q;
	}

	
	/**
	 * 
	 * 
	 *@时间 2017年2月16日 下午4:55:41
	 */
	public String listajax() throws Exception{
List<Region> list=null;
		
		if(StringUtils.isNotBlank(q)){
			list=regionService.findByQ(q);
		}else{
			list=regionService.findAll();
		}
				
		String[] excludes=new String[]{"subareas"};
		this.WriteList2Json(list,excludes);
		return NONE;
	}
	
	
}
