/**
 * 
 */
package bosTest01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;


/**
 *<p>标题: POItest </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月16日 上午10:44:30
 *@版本 
 */
public class POItest {

	@Test
	public void testPOI()throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("d:\\abc.xls")));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(Row row:sheet){
			String v1 = row.getCell(0).getStringCellValue();
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1+"  "+v2+"  "+v3+"  "+v4+"  "+v5);
			
		}
	}
	/**
	 * 使用ＰＯＩ解析Ｅｘｃｅｌ文件
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	//@Test
	public void test1() throws FileNotFoundException, IOException{
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("d:\\abc.xls")));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			String v1 = row.getCell(0).getStringCellValue();
			String v2 = row.getCell(1).getStringCellValue();
			String v3 = row.getCell(2).getStringCellValue();
			String v4 = row.getCell(3).getStringCellValue();
			String v5 = row.getCell(4).getStringCellValue();
			System.out.println(v1 + " " + v2+" " + v3+ " " +v4+ " " + v5);
		}
	}
	
}
