package com.ruanyun.web.service.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.ExternalAppDao;
import com.ruanyun.web.model.TExternalChannelTask;

@Service
public class ExternalAppService extends BaseServiceImpl<TExternalChannelTask>
{
	@Autowired
	private ExternalAppDao externalAppDao;
	
	public void save(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		externalAppDao.save(tExternalChannelTask, adid, key);
	}
	
	public TExternalChannelTask geTExternalTaskInfo(TExternalChannelTask tExternalChannelTask, String adid) 
	{
		return externalAppDao.getExternalTaskInfo(tExternalChannelTask, adid);
	}
	
	public int update(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		return externalAppDao.update(tExternalChannelTask, adid, key);
	}
	
	
	public int updateStatus(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		return externalAppDao.updateStatus(tExternalChannelTask, adid, key);
	}
	
	
	/**
     * 导入
     * 
     * @param file csv文件(路径+文件)
     * @return
     */
    public void importCsv()
    {
    	File file = new File("C:\\Program Files\\Apache Software Foundation\\import\\test.csv");
    	
        List<TExternalChannelTask> dataList = new ArrayList<TExternalChannelTask>();
        
        BufferedReader br = null;
        
        String tableName = null;
        
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
            			  continue;}
            		  TExternalChannelTask task = new TExternalChannelTask("1.1.1.1", pills[0].trim(), "其他渠道提供idfa", "3");
            		  //去掉重复
            		  for(TExternalChannelTask task2 : dataList) 
            		  {
            			  if(task2.getIdfa().equalsIgnoreCase(task.getIdfa())) 
            			  {
            				  continue;
            			  }
            		  }
            		  
            		  dataList.add(task);
            	  }
            }
            
            externalAppDao.saveList(dataList, tableName);
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
    }
}
