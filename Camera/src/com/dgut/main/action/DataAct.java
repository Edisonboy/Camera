package com.dgut.main.action;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgut.common.util.DateUtils;
import com.dgut.common.util.StrUtils;
import com.dgut.common.util.Zipper;
import com.dgut.common.util.Zipper.FileEntry;
import com.dgut.common.web.RequestUtils;
import com.dgut.common.web.ResponseUtils;
import com.dgut.common.web.session.SessionProvider;
import com.dgut.common.web.springmvc.RealPathResolver;
import com.dgut.main.Constants;

import com.dgut.main.entity.JXField;
import com.dgut.main.manager.AdminLogMng;
import com.dgut.main.manager.JXDataBackMng;
import com.dgut.main.web.WebErrors;



@Controller
public class DataAct {
	private static String SUFFIX = "sql";

	private static String SLASH="\\";

	private static String backup_table;
	private static final Logger log = LoggerFactory
	.getLogger(DataAct.class);
	
	@RequestMapping("/data/v_list.do")
	public String list(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
//		List<String> tables = dataBackMng.listTabels();
//		model.addAttribute("tables", tables);
//		return "data/list";
		List<String> tables = null;  
        /**  
         * fix this support for mysql5.5 version <br/>  
         * 2013-3-22 yangq  
         */  
        try {  
            // tables = dataBackMng.listTabels();  
            tables = dataBackMng.listTabels(dataBackMng.getDefaultCatalog());  
        }  
        catch (SQLException e) {  
            model.addAttribute("msg", e.toString());  
            return "common/error_message";  
        }  
        model.addAttribute("tables", tables);  
        return "data/list";  
	}
	
	@RequestMapping("/data/v_listfields.do")
	public String listfiled(String tablename, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		List<JXField> list = dataBackMng.listFields(tablename);
		model.addAttribute("list", list);
		return "data/fields";
	}
	

	
	
	
	@RequestMapping("/data/o_backup.do")
	public String backup(String tableNames[], ModelMap model,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, InterruptedException {
//		String backpath = realPathResolver.get(Constants.BACKUP_PATH);
		String backpath = "c:\\BACKUP_DB";
		File backDirectory = new File(backpath);
		if (!backDirectory.exists()) {
			backDirectory.mkdir();
		}
		DateUtils dateUtils = DateUtils.getDateInstance();
		
		String name = dateUtils.getNowString() + "."+ SUFFIX;
		File file=new File(backpath,name);
		if(!file.exists()){
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
		String backFilePath = backpath + SLASH+ name;    //文件全路径
		Thread thread =new DateBackupTableThread(file,backFilePath);
		thread.start();
		return "data/backupProgress";
	}
	
	@RequestMapping("/data/v_listfiles.do")
	public String listBackUpFiles(ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		//model.addAttribute("list",resourceMng.listFile(Constants.BACKUP_PATH, false));
		return "data/files";
	}
	

	
	
	
	@RequestMapping("/data/o_backup_progress.do")
	public void getBackupProgress(HttpServletRequest request, HttpServletResponse response) throws JSONException{
		JSONObject json=new JSONObject();
		json.put("tablename", backup_table);
		json.put("progress", backup_table);
		json.put("per", progress);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	
	

	
	
	private class DateBackupTableThread extends Thread{
		private File file;
		private String backFilePath;
		//private String[] tablenames;
		public DateBackupTableThread(File file, String backFilePath) {
			super();
			this.file = file;
			this.backFilePath = backFilePath;
			//this.tablenames=tablenames;
		}
		public void run() {
//			FileOutputStream out;
//			OutputStreamWriter writer=null;
//			try {
//				out = new FileOutputStream(file);
//				writer = new OutputStreamWriter(out, "utf8");
//				writer.write(Constants.ONESQL_PREFIX + DISABLEFOREIGN);
//				for (int i=0;i<tablenames.length;i++) {
//					backup_table=tablenames[i];
//					backupTable(writer,tablenames[i]);
//				}
//				writer.write(Constants.ONESQL_PREFIX + ABLEFOREIGN);
//				backup_table="";
//				writer.close();
//				out.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			progress = 12;
			backup_table = "备份数据库中";
			/*String sql = "BACKUP DATABASE [old] TO  DISK = N'"+backFilePath+"' WITH NOFORMAT, NOINIT,  NAME = N'old-FULL BACKUP', SKIP, NOREWIND, NOUNLOAD,  STATS = 10 ";
			dataBackMng.executeSQL(sql);*/
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("mysqldump").append(" --opt").append(" -h").append("127.0.0.1");
			stringBuilder.append(" --user=").append("root") .append(" --password=").append("123456").append(" --lock-all-tables=true");
			stringBuilder.append(" --result-file=").append(backFilePath ).append(" --default-character-set=utf8 ").append("old");
			try {
				Process process = Runtime.getRuntime().exec(stringBuilder.toString());
				if (process.waitFor() == 0) {// 0 表示线程正常终止。
					progress = 100;
					backup_table="";
					return ;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

		
	}
	private static Integer progress;
	
	@Autowired
	private RealPathResolver realPathResolver;
	
	@Autowired
	private JXDataBackMng dataBackMng;
	
	/*@Autowired
	private JXResourceMng resourceMng;*/
	
	@Autowired
	private AdminLogMng cmsLogMng;
	
	@Autowired
	private SessionProvider session;
}
