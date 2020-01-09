package com.ruanyun.web.service.background;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.background.TPhoneUdidWithIdfaDao;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;
import com.ruanyun.web.producer.UdidQueueConsumer;
import com.ruanyun.web.util.ExcelUtils;

@Service
public class UdidService extends BaseServiceImpl<TPhoneUdidWithIdfa>{
	@Autowired
	@Qualifier("tPhoneUdidWithIdfaDao")
	private TPhoneUdidWithIdfaDao tPhoneUdidWithIdfaDao;

	public TPhoneUdidWithIdfa getUdidByIdfa(String idfa, String tableName) 
	{
		return tPhoneUdidWithIdfaDao.getUdidByIdfa(idfa, tableName);
	}
	
	public int getUdidSumedNum(String udidType,String tableName,String time)
	{
		return tPhoneUdidWithIdfaDao.getUdidSumedNum(udidType, tableName,time);
	}
	
	public void savePhoneInfo(TPhoneUdidWithIdfa model,String tableName)
	{
		tPhoneUdidWithIdfaDao.savePhoneInfo(model, tableName);
	}
	
	public  List<TPhoneUdidModel> getUdidFromFile()
    {
    	File file = new File("C:\\Program Files\\Apache Software Foundation\\import\\udid.csv");
    	
        List<TPhoneUdidModel> dataList = new ArrayList<TPhoneUdidModel>();
        String time = TimeUtil.GetdayDate();
        BufferedReader br = null;
        try
        { 
            br = new BufferedReader(new FileReader(file));
            String line = ""; 
            while ((line = br.readLine()) != null)
            { 
            	  if(line.trim() != "")
            	  {  
            		  String[] pills = line.split(",");
            		  if(pills.length == 2 || pills.length == 0) 
            		  { continue;}
            		  TPhoneUdidModel udidmodel = new TPhoneUdidModel(pills[0].trim(), 0, time);
            		  //去掉重复
            		  for(TPhoneUdidModel task2 : dataList) 
            		  {
            			  if(task2.getUdid().equalsIgnoreCase(udidmodel.getUdid())) 
            			  {
            				  continue;
            			  }
            		  }
            		  
            		  dataList.add(udidmodel);
            	  }
            }
        }
        catch (Exception e) 
        {
        	 e.printStackTrace();
        }
        finally
        {
            if(br!=null)
            {
                try 
                {
                    br.close();
                    br=null;
                }
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }
        
        return dataList;
}
	
	 public static void main(String[] args) {
			System.out.print(shengchengidfaStr());
		}
	 
	 public static String shengchengidfaStr()
	    {
		    //List<String> res = new ArrayList<String>();
			List<TPhoneUdidModel> result = new ArrayList<TPhoneUdidModel>();
	    	File file = new File("C:\\Program Files\\Apache Software Foundation\\import\\idfa.csv");
	    	String time = TimeUtil.GetdayDate();
	        List<TPhoneUdidModel> dataList = new ArrayList<TPhoneUdidModel>();
	        
	        BufferedReader br = null;
	        String tableName = null;
	        String flag = "1";
	        try
	        { 
	            br = new BufferedReader(new FileReader(file));
	            String line = ""; 
	            while ((line = br.readLine()) != null)
	            { 
	            	  if(line.trim() != "") {  
	            		  String[] pills = line.split(",");
	            		  if(pills.length == 2) {
	            			  tableName = pills[0].trim();
	            			  flag = pills[1].trim();
	            			  continue;}
	            		  
	            		  if(pills == null || pills.length <= 0) {
	            			  continue;
	            		  }
	            		  
	            		  TPhoneUdidModel udidmodel = new TPhoneUdidModel(pills[0].trim(), 0, time);
	            		
	            		  dataList.add(udidmodel);
	            	  }
	            }

	        }
	        catch (Exception e) 
	        {
	        	 e.printStackTrace();
	        }
	        finally
	        {
	            if(br!=null)
	            {
	                try 
	                {
	                    br.close();
	                    br=null;
	                }
	                catch (IOException e) 
	                {
	                    e.printStackTrace();
	                }
	            }
	        }
	        
	        String resultss = null;

	        for(TPhoneUdidModel sre : dataList) {
	        	resultss =  "'"+sre.getUdid() + "'" + "," + resultss;
	        }
	        return resultss;
	}
	 
	
	 
	 public List<String> importCsv()
	    {
		    List<String> res = new ArrayList<String>();
			List<TPhoneUdidModel> result = new ArrayList<TPhoneUdidModel>();
	    	File file = new File("C:\\Program Files\\Apache Software Foundation\\import\\udid.csv");
	    	String time = TimeUtil.GetdayDate();
	        List<TPhoneUdidModel> dataList = new ArrayList<TPhoneUdidModel>();
	        
	        BufferedReader br = null;
	        String tableName = null;
	        String flag = "1";
	        try
	        { 
	            br = new BufferedReader(new FileReader(file));
	            String line = ""; 
	            while ((line = br.readLine()) != null)
	            { 
	            	  if(line.trim() != "") {  
	            		  String[] pills = line.split(",");
	            		  if(pills.length == 2) {
	            			  tableName = pills[0].trim();
	            			  flag = pills[1].trim();
	            			  continue;}
	            		  
	            		  if(pills == null || pills.length <= 0) {
	            			  continue;
	            		  }
	            		  
	            		  TPhoneUdidModel udidmodel = new TPhoneUdidModel(pills[0].trim(), 0, time);
	            		  if(flag.equals("2")) {
	            			  tPhoneUdidWithIdfaDao.deleteModel(udidmodel , tableName);
	            			  continue; 
	            		  }
	            		  //去掉重复
	            		  boolean isexist =  false;
	            		  for(TPhoneUdidModel task2 : dataList) 
	            		  {
	            			  if(task2.getUdid().equalsIgnoreCase(udidmodel.getUdid())) 
	            			  {
	            				  isexist = true;
	            				  break;
	            			  }
	            		  }
	            		  
	            		  if(!isexist)
	            		  {
	            			  dataList.add(udidmodel);
	            		  }
	            	  }
	            }
	            
	            if(tableName != null) {
	            	//先判断是否重复
	            
	            	List<TPhoneUdidModel> modelList = tPhoneUdidWithIdfaDao.getTPhoneUdidModel(tableName);
	            	List<String> udidList = new ArrayList<String>();
	            	for(TPhoneUdidModel b : modelList) {
	            		udidList.add(b.getUdid());
	            	}
	            	
	            	for(TPhoneUdidModel model : dataList) {
	            		if(udidList.contains(model.getUdid())) {
	            			result.add(model);
	            		}
	            	}
	            	
	            	//去掉所有重复项
	            	dataList.removeAll(result);
	            	if(!dataList.isEmpty()) {
	            		tPhoneUdidWithIdfaDao.saveList(dataList, tableName);
	            	}
	            }
	        }
	        catch (Exception e) 
	        {
	        	 tableName = null;
	        	 e.printStackTrace();
	        }
	        finally
	        {
	            if(br!=null)
	            {
	                try 
	                {
	                    br.close();
	                    br=null;
	                }
	                catch (IOException e) 
	                {
	                    e.printStackTrace();
	                }
	            }
	        }
	        res.add(tableName);
	        res.add("udid重复数量:" + result.size());
	        return res;
	    }
	 
	 
	 	//队列生产udid
		@SuppressWarnings("rawtypes")
		public void activated(HttpServletResponse response, String type)
		{
			try
			{
				//udid生产者
				String endname = type.replace("_", ",");
				UdidQueueConsumer s = new UdidQueueConsumer(endname,false);
				List list = tPhoneUdidWithIdfaDao.activated(type);
				for(Object o : list) 
				{
					String udid = o.toString();
					udid = udid.substring(6,udid.length() - 1);
					s.sendMessage(udid, endname.toLowerCase());
					System.out.println("---" + udid + "---");
					tPhoneUdidWithIdfaDao.updateUdidStatus(udid,type,1);
				}
				
				s.close();
			} 
			catch (IOException | TimeoutException e)
			{
				e.printStackTrace();
			}
		}
		
		public int updateUdidStatus(String udid,String tableName,int status)
		{
			return tPhoneUdidWithIdfaDao.updateUdidStatus(udid,tableName,status);
		}
		
		
		public void exprotPhoneUdid(HttpServletResponse response, List<TPhoneUdidWithIdfa> udids)
		{
			String fileName = "test_udid";
			String[] columns = {"udid","phoneModel"};
			String[] headers = {"udid","机型"};
			try {
				ExcelUtils.exportExcel(response, fileName, udids, columns, headers,
				SysCode.DATE_FORMAT_STR_L);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
