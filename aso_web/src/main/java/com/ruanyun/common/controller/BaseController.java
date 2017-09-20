package com.ruanyun.common.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.util.DateJsonValueProcessor;
import com.ruanyun.web.util.PropertiesUtils;

public abstract class BaseController 
{
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	/**
	 * 重定向
	 */
	public final String REDIRECT = "redirect:";
	/**
	 * 转发
	 */
	public final String FORWARD = "forward:";
	/**
	 * 消息
	 */
	public final String MSG = "msg";
	/**
	 * 成功
	 */
	public final int SUCCESS=1;
	
	/**
	 * 修改成功
	 */
	public final int SUCCESS_UPDATE=2;
	
	/**
	 * 删除成功
	 */
	public final int SUCCESS_DEL=3;
	
	/**
	 * 失败
	 */
	public final int ERROR=-1;
	
	/**
	 * 修改失败
	 */
	public final int ERROR_UPDATE=-2;
	
	/**
	 * 删除成功
	 */
	public final int ERROR_DEL=-3;
	

	/**
	 * 功能描述:写出文本
	 *
	 * @author yangliu  2013-8-26 上午09:08:36
	 * 
	 * @param response response
	 * @param data
	 */
	public void writeText(HttpServletResponse response,Object data) 
	{
		PrintWriter out=null;
		try
		{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/text");
			 out = response.getWriter();
			out.print(data);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			closeOut(out);
		}
	}
	
	/**
	 * 功能描述:输出流
	 *
	 * @author yangliu  2013-7-19 上午10:03:41
	 * 
	 * @param response 
	 * @param bs 字节数组
	 */
	protected void writeStream(HttpServletResponse response,byte [] bs) 
	{
		ServletOutputStream out = null;
		try 
		{
			out = response.getOutputStream();
			if (bs == null)
			{
				bs = new byte[0];
			}
			out.write(bs);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			if (out != null)
			{
				try 
				{
					out.flush();
					out.close();
				} 
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 功能描述: 
	 *
	 * @param response 写出文本 设置编码
	 * @param encoding 编码
	 * @param data
	 */
	
	public void writeText(HttpServletResponse response,String encoding, Object data) 
	{
			response.setCharacterEncoding(encoding);
			writeText(response, data);
	}

	
	/**
	 * 功能描述:写出json对象
	 * 
	 * @param response
	 * @param encoding 编码
	 * @param data 数据
	 */
	public void writeJsonData(HttpServletResponse response,String encoding, Object data)
	{
		PrintWriter out = null;
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor());
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor());
        config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
		try
		{
			response.setCharacterEncoding(encoding);
			out = response.getWriter();
			if (data instanceof List)
			{
                JSONArray array = JSONArray.fromObject(((List<?>) data).toArray(), config);
                System.out.println(array.toString());
                out.print(array.toString());
            } 
			else
			{
                String result = JSONObject.fromObject(data, config).toString();
                System.out.println(result);
                out.print(result);
            }
        }
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeOut(out);
		}
	}
	
	/**
	 * 功能描述:写出json对象
	 *
	 * @param response
	 * @param encoding 编码
	 * @param data 数据
	 */
	public void writeJsonDataApp(HttpServletResponse response, String encoding, Object data) 
	{
		PrintWriter out=null;
		try
		{
			response.setContentType("application/json");
			out=response.getWriter();
			JsonConfig config = new JsonConfig();
	        config.registerJsonValueProcessor(java.sql.Date.class, new DateJsonValueProcessor());
	        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor());
	        config.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor());
			if (data instanceof List)
			{
				JSONArray array = JSONArray.fromObject(((List<?>) data).toArray());
				System.out.println(array.toString());
				out.print(array.toString());
			}
			else
			{
				String result = JSONObject.fromObject(data, config).toString();
				System.out.println(result);
				out.print(result);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}finally{
			closeOut(out);
		}
	}
	
	/**
	 * 功能描述: 默认utf-8 写出json
	 *
	 * @param response
	 * @param data
	 */
	public void writeJsonDataApp(HttpServletResponse response, Object data)
	{
		this.writeJsonDataApp(response, "UTF-8", data);
	}
	/**
	 * 功能描述: 默认utf-8 写出json
	 *
	 * @param response
	 * @param data
	 */
	public void writeJsonData(HttpServletResponse response, Object data) 
	{
		this.writeJsonData(response, "UTF-8", data);
	}
	
	public void addModel(Model model, String key, Object value)
	{
		model.addAttribute(key, value);
	}
	
	/**
	 * 页面时间绑定
	 * 
	 * @param binder
	 * @param fmt
	 *            日期时间格式
	 * @param allowEmpty
	 *            是否允许为空
	 */
	public void initBinder(WebDataBinder binder, String fmt, boolean allowEmpty) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, allowEmpty));
	}

	/**
	 * 页面特殊字段时间绑定
	 * 
	 * @param binder
	 * @param field
	 *            字段名称
	 * @param fmt
	 *            日期时间格式
	 * @param allowEmpty
	 *            是否允许为空
	 */
	protected void initBinder(WebDataBinder binder, String field, String fmt,
			boolean allowEmpty)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, field, new CustomDateEditor(
				dateFormat, allowEmpty));
	}
	
	private void closeOut(PrintWriter out)
	{
		if(out != null)
		{
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 功能描述:获取页面路径 页面路径配置在 url.properties
	 *
	 * @param key 页面key
	 * @return 真实页面路径
	 */
	protected String getPagePath(String key)
	{
		String path = PropertiesUtils.URLPage.getValue(key);
		
		if(EmptyUtils.isEmpty(path))
		{
			path = PropertiesUtils.URLPage.getValue("default");
		}
		
		return path;
	}
	
	/**
	 * 重定向
	 * @param url 路径
	 * @param params 参数
	 * @return
	 */
	protected String redirect(String url,Object...params) 
	{
		StringBuffer param = new StringBuffer("");
		
		if(EmptyUtils.isNotEmpty(params))
		{
			for(Object obj : params)
			{
				param.append("/").append(obj);
			}
		}
		
		return REDIRECT + url + param;
	}
	
	/**
	 * 转发
	 * @param url  路径
	 * @param params 参数
	 * @return
	 */
	protected String forward(String url,Object...params) 
	{
		StringBuffer param = new StringBuffer("");
		if(EmptyUtils.isNotEmpty(params))
		{
			for(Object obj : params)
			{
				param.append("/").append(obj);
			}
		}
		
		return FORWARD + url + param;
	}
	
	//------------------------------------jsonp方法开始---------------------------------------------------------------------------
	
	/**
	 * 以下方法专门为了，jsonp访问而配置
	 */
	/**
	 * 功能描述:写出文本
	 * 
	 * @param response response
	 * @param data
	 */
	public void writeText(HttpServletResponse response,Object data,String callback) 
	{
		PrintWriter out = null;
		try 
		{
			response.setCharacterEncoding("UTF-8");
			 out=response.getWriter();
			 if(EmptyUtils.isNotEmpty(callback))
			 {
				 data = callback+"("+data+")";
			 }
			
			 out.print(data);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			closeOut(out);
		}
	}
	
	
	
	/**
	 * 功能描述: 
	 * 
	 * @param response 写出文本 设置编码
	 * @param encoding 编码
	 * @param data
	 */
	public void writeText(HttpServletResponse response,String encoding, Object data,String callback) 
	{
			response.setCharacterEncoding(encoding);
			writeText(response, data,callback);
	}
	
	/**
	 * 功能描述:写出json对象
	 *
	 * @param response
	 * @param encoding 编码
	 * @param data 数据
	 * @param callback jsonp 回调函数名称
	 */
	public void writeJsonData(HttpServletResponse response,String encoding, Object data,String callback)
	{
		PrintWriter out = null;
		try
		{
			response.setCharacterEncoding(encoding);
			out = response.getWriter();
			String result = "";
			if (data instanceof List)
			{
				JSONArray array = JSONArray.fromObject(((List<?>) data).toArray());
				
				result=array.toString();
			}
			else
			{
				result = JSONObject.fromObject(data).toString();
			}
			if(EmptyUtils.isNotEmpty(callback))
			{
				result = callback+"("+result+")";
			}
			out.print(result);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}finally
		{
			closeOut(out);
		}
	}
	
	/**
	 * 功能描述: 默认utf-8 写出json
	 *
	 * @param response
	 * @param data
	 */
	public void writeJsonData(HttpServletResponse response, Object data,String callback) 
	{
		this.writeJsonData(response, "UTF-8", data,callback);
	}
	
	//------------------------------------jsonp方法结束---------------------------------------------------------------------------
}
