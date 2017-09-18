package com.ruanyun.web.util;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ReflectUtils;

public class ExcelUtils {
	/**
	 * 功能描述:导出execl数据 
	 *  list里只能传实体 或者map数据
	 *
	 * @author L H T  2013-12-11 下午02:00:11
	 * 
	 * @param response
	 * @param fileName excel头部名称
	 * @param list  数据
	 * @param colums  列
	 * @param headers  列头
	 * @param dateFormat 时间格式
	 * @throws Exception
	 */
	public static void exportExcel(HttpServletResponse response,
			String fileName, List<?> list, String[] colums, String[] headers,
			String dateFormat) throws Exception {
		int rowIndex = 0;
		Workbook workbook = new HSSFWorkbook(); // 创建一个工作簿
		Sheet sheet = workbook.createSheet(); // 创建一个Sheet页
		sheet.autoSizeColumn((short) 0); // 单元格宽度自适应
		Row row = sheet.createRow(rowIndex++); // 创建第一行(头部)
		CreationHelper helper = workbook.getCreationHelper();
		CellStyle style = workbook.createCellStyle(); // 设置单元格样式
		style.setDataFormat(helper.createDataFormat().getFormat(dateFormat)); // 格式化日期类型
		for (int i = 0; i < headers.length; i++) { // 输出头部
			row.createCell(i).setCellValue(headers[i]);
		}
		for (Object obj : list) {
			List<Object> values = ReflectUtils.getFieldValuesByNames(colums,obj);
			row = sheet.createRow(rowIndex++);
			for (int j = 0; j < values.size(); j++) {
				row.createCell(j).setCellValue(getValue(values.get(j)));
			}
		}
		String ddate = new SimpleDateFormat("yyyyMMddhhmmss").format(Calendar.getInstance().getTime());
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("gb2312"),"iso8859-1") + "_" + ddate + ".xls");// 设定输出文件头
		OutputStream output = response.getOutputStream();
		workbook.write(output);
		output.flush();
		output.close();
	}

	public static String getValue(Object obj){
		if(EmptyUtils.isNotEmpty(obj)){
			return obj.toString();
		}
		return "";
	}
	


}
