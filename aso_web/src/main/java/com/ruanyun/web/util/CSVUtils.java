package com.ruanyun.web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ReflectUtils;

public class CSVUtils 
{
	/**
	   *       
	   *         导出生成csv格式的文件
	   * @author     ccg
	   * @param     titles csv格式头文
	   * @param     propertys 需要导出的数据实体的属性，注意与title一一对应
	   * @param     list 需要导出的对象集合
	   * @return
	   * @throws     IOException
	   * Created     2017年1月5日 上午10:51:44
	   * @throws     IllegalAccessException 
	   * @throws     IllegalArgumentException 
	   */
	public static <T> String exportCsv(String fileName, String[] titles, String[] propertys, List<T> list) 
			throws IOException, IllegalArgumentException, IllegalAccessException
	{
	   File csvFile = File.createTempFile(fileName, ".csv", new File("D:/"));
	    //构建输出流，同时指定编码
	    BufferedWriter ow = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(csvFile), "UTF-8"),1024);
	     
	    //csv文件是逗号分隔，除第一个外，每次写入一个单元格数据后需要输入逗号
	    for(String title : titles)
	    {
	      ow.write(title);
	      ow.write(",");
	    }
	    
	    //写完文件头后换行
	    ow.write("\r\n");
	    //写内容
	    
	    for (Object obj : list)
	    {
			List<Object> values = ReflectUtils.getFieldValuesByNames(propertys, obj);
			for (int j = 0; j < values.size(); j++) 
			{
				ow.write(getValue(values.get(j)));
	            ow.write(",");
	            continue;
			}
	      //写完一行换行
	      ow.write("\r\n");
		}
	    
	    ow.flush();
	    ow.close();
	    return "0";
	  }
	
	public static String getValue(Object obj){
		if(EmptyUtils.isNotEmpty(obj)){
			return obj.toString();
		}
		return "";
	}
}
