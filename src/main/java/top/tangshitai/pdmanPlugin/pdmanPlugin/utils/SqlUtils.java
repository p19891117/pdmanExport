package top.tangshitai.pdmanPlugin.pdmanPlugin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Entitie;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Field;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Module;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Node;

public class SqlUtils {
	public static StringBuilder sb = null;
	
	public static List<String[]> sqlToListForTable(String fname){
		List<String[]> tableStrs = new ArrayList<>();
		Pattern p = Pattern.compile("CREATE TABLE \"stategrid\".\"([a-zA-Z_]+)\"([^;]+);");
		try {
			sb = Utils.readFileContent(fname);
			Matcher m = p.matcher(sb.toString());
			while(m.find()) {
				tableStrs.add(new String[]{m.group(1),m.group(2)});
			}
			return tableStrs;
		} catch (IOException e) {
			System.out.println("读取sql脚本文件出错，程序停止");
			System.exit(0);
			return null;
		}
	}
	 
	public static Entitie tableStrToObj(String tablename, String fieldsStr){
		Entitie table = new Entitie();
		table.setTitle(tablename);
		table.setChnname(tablename);
		if(StringUtils.isBlank(fieldsStr)) {
			throw new IllegalArgumentException("数据表结构内容不能为空");
		}
		fieldsStr = fieldsStr.trim();
		if(fieldsStr.indexOf("(")==-1&&fieldsStr.lastIndexOf(")")==-1) {
			throw new IllegalArgumentException("数据表结构格式不正确，不是以()开始和结束["+fieldsStr.charAt(0)+"|"+fieldsStr.charAt(fieldsStr.length()-1)+"]\n\r"+fieldsStr);
		}
		fieldsStr = fieldsStr.substring(fieldsStr.indexOf("(")+1, fieldsStr.lastIndexOf(")")).trim();
		
		String[] fieldStrs = fieldsStr.split("\n");
		Pattern p = Pattern.compile("\"([a-zA-Z0-9_]+)\"\\s+(varchar|char|text|numeric|int2|int4|float8|int8|bool|timestamp|date)(\\(\\d+\\))*.*");
		List<Field> fields = new ArrayList<>();
		for(String fieldStr:fieldStrs) {
			if(StringUtils.isBlank(fieldStr)) 
				throw new IllegalArgumentException("数据表结构分割后的某列格式不正确:"+fieldsStr);
			fieldStr = fieldStr.trim();
			Matcher m = p.matcher(fieldStr);
			if(m.matches()) {
				//String len = StringUtils.isBlank(m.group(3))?"":m.group(3);
				Field field = new Field();
				field.setName(m.group(1));
				field.setType(sqlTypeToPdmanType(m.group(2)));
				field.setChnname(getComment(tablename,m.group(1)));
				field.setPk(getPrimaryKey(tablename, m.group(1)));
				field.setFk(getForeignKey(tablename, m.group(1)));
				if(field.isPk())
					field.setNotNull(true);
				fields.add(field);
			}else {
				throw new IllegalArgumentException("数据表结构分割后的某列格式不正确:"+fieldsStr);
			}
		}
		table.setFields(fields);
		return table;
	}
	public static void setEntitieToModule(List<Module> modules,Entitie e) {
		String modulename = null;
		if(e.getTitle().startsWith("t_cbm_")) {//检修
			modulename = "检修t_cbm";
		}else if(e.getTitle().startsWith("t_detect_")){//检测
			modulename = "检测t_detect";
		}else if(e.getTitle().startsWith("t_levt_")){//评价
			modulename = "评价t_levt";
		}else if(e.getTitle().startsWith("t_pub_")){//公用
			modulename = "公用t_pub";
		}else if(e.getTitle().startsWith("t_rtr_")){//抢修
			modulename = "抢修t_rtr";
		}else if(e.getTitle().startsWith("t_run_")){//运维
			modulename = "运维t_run";
		}else if(e.getTitle().startsWith("t_sevt_")){//评价
			modulename = "评价t_sevt";
		}else if(e.getTitle().startsWith("t_ys_")){//验收
			modulename = "验收t_ys";
		}else if(e.getTitle().startsWith("t_sec_")){
			modulename = "t_sec";
		}else if(e.getTitle().startsWith("t_equ_")){
			modulename = "t_equ";
		}else if(e.getTitle().startsWith("t_user_")){
			modulename = "t_user";
		}else {
			modulename = "其他other";
		}
		for(Module m:modules) {
			if(m.getName().equals(modulename)) {
				m.getEntities().add(e);
				m.getGraphCanvas().getNodes().add(builderNode(e.getTitle()));
				return;
			}
		}
		Module module = new Module();
		module.setName(modulename);
		module.getEntities().add(e);
		module.getGraphCanvas().getNodes().add(builderNode(e.getTitle()));
		modules.add(module);
	}
	private static Node builderNode(String tableName) {
		Node node = new Node();
		node.setShape("table");
		node.setTitle(tableName);
		node.setId(Utils.genShortUuid());
		node.setX(434);
		node.setY(80);
		return node;
	}
	private static String getComment(String tablename,String fieldname) {
		String reg = "COMMENT ON COLUMN \"stategrid\"\\.\""+tablename+"\"\\.\""+fieldname+"\" IS '([^']+)';";
		//COMMENT ON COLUMN "stategrid"."t_ys_type_equ_plan"."f_pid" IS '主键';
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sb.toString());
		if(m.find()) {
			return m.group(1);
		}
		return "";
	}
	private static boolean getPrimaryKey(String tablename,String fieldname) {
		String reg = "ALTER TABLE \"stategrid\"\\.\""+tablename+"\" ADD CONSTRAINT \"[\\w\\d_]+\" PRIMARY KEY \\(\""+fieldname+"\"\\);";
		//ALTER TABLE "stategrid"."ofmucservice" ADD CONSTRAINT "ofmucservice_pkey" PRIMARY KEY ("subdomain");
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sb.toString());
		if(m.find()) {
			return true;
		}
		return false;
	}
	private static String[] getForeignKey(String tablename,String fieldname) {
		String reg = "ALTER TABLE \"stategrid\"\\.\""+tablename+"\" ADD CONSTRAINT \"([\\w\\d_]+)\" FOREIGN KEY \\(\""+fieldname+"\"\\) REFERENCES \"stategrid\".\"([\\w\\d_]+)\" \\(\"([\\w\\d_]+)\"\\)";
		//ALTER TABLE "stategrid"."t_levt_skill_expert" ADD CONSTRAINT "fk_t_levt_s_reference_t_levt_e" FOREIGN KEY ("f_parent_pid") REFERENCES "stategrid"."t_levt_expert" ("f_pid")
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sb.toString());
		if(tablename.equals("t_run_carddetail")&&fieldname.equals("f_card_pid")) {
			System.out.println();
		}
		if(m.find()) {
			return new String[]{tablename,fieldname,m.group(2),m.group(3)};
		}
		return null;
	}
	private static  String sqlTypeToPdmanType(String sqlType) {
		if("varchar".equals(sqlType)) {
			return "DefaultString";
		}else if("int2".equals(sqlType)||"int4".equals(sqlType)||"int8".equals(sqlType)) {
			return "Integer";
		}else if("char".equals(sqlType)) {
			return "DefaultString";
		}else if("text".equals(sqlType)) {
			return "LongText";
		}else if("numeric".equals(sqlType)) {
			return "Double";
		}else if("float8".equals(sqlType)) {
			return "Double";
		}else if("bool".equals(sqlType)) {
			return "YesNo";
		}else if("timestamp".equals(sqlType)) {
			return "DateTime";
		}else if("date".equals(sqlType)) {
			return "Date";
		}else {
			throw new IllegalArgumentException("不合法参数类型:"+sqlType);
		}
	}
}
