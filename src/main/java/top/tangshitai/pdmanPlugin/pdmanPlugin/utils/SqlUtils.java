package top.tangshitai.pdmanPlugin.pdmanPlugin.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Entitie;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Field;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Module;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Node;

public class SqlUtils {
	public static final Properties tableComment= new Properties();
	public static final Properties moduleTypes= new Properties();
	static {
		try {
			tableComment.load(SqlUtils.class.getClassLoader().getResourceAsStream("table_comment.properties"));
			moduleTypes.load(SqlUtils.class.getClassLoader().getResourceAsStream("modules.properties"));
		} catch (IOException e) {
			System.out.println("加载表名与注释映射配置文件失败,程序退出");
			System.exit(0);
		}
	}
	/**
	 * 匹配数据表结构
	 */
	public static final String reg_table="CREATE TABLE \"stategrid\".\"([\\w\\d_]+)\"([^;]+);";
	/**
	 * 匹配数据表结构中的每一列
	 */
	public static final String reg_record="\"([\\w\\d_]+)\"\\s+(bytea|varchar|char|text|numeric|int2|int4|float8|int8|bool|timestamp|date)(\\(\\d+\\))*.*";
	public static StringBuilder sb = null;
	
	public static List<String[]> sqlToListForTable(String fname){
		List<String[]> tableStrs = new ArrayList<>();
		Pattern p = Pattern.compile(reg_table);
		try {
			sb = Utils.readFileContent(fname);
			Matcher m = p.matcher(sb.toString());
			while(m.find()) {
				//表名内容处理
				String tablename = m.group(1);
				if(StringUtils.isBlank(tablename)) {
					throw new IllegalArgumentException("解析出的表名为空");
				}
				tablename = tablename.trim();
				//表结构内容处理
				String tableConstruct = m.group(2);
				if(StringUtils.isBlank(tableConstruct)) {
					throw new IllegalArgumentException("解析出的表名为空");
				}
				tableConstruct = tableConstruct.trim();
				tableStrs.add(new String[]{tablename,tableConstruct});
			}
			System.out.println("解析出数据表结构总共："+tableStrs.size());
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
		String comment = StringUtils.isBlank(tableComment.getProperty(tablename))?tablename:tableComment.getProperty(tablename);
		table.setChnname(comment);
		if(StringUtils.isBlank(fieldsStr)) {
			throw new IllegalArgumentException("数据表结构内容不能为空");
		}
		fieldsStr = fieldsStr.trim();
		if(fieldsStr.indexOf("(")==-1&&fieldsStr.lastIndexOf(")")==-1) {
			throw new IllegalArgumentException("数据表结构格式不正确，不是以()开始和结束["+fieldsStr.charAt(0)+"|"+fieldsStr.charAt(fieldsStr.length()-1)+"]\n\r"+fieldsStr);
		}
		fieldsStr = fieldsStr.substring(fieldsStr.indexOf("(")+1, fieldsStr.lastIndexOf(")")).trim();
		
		String[] fieldStrs = fieldsStr.split("\n");
		Pattern p = Pattern.compile(reg_record);
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
				if("备注".equals(field.getChnname())||"是否有效".equals(field.getChnname())||
						"创建时间".equals(field.getChnname())||"最后修改人".equals(field.getChnname())||
						"创建人".equals(field.getChnname())||"最后修改时间".equals(field.getChnname())) {
					field.setRelationNoShow(true);
				}
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
		Set<Entry<Object, Object>> entries = moduleTypes.entrySet();
		for(Entry<Object, Object> entry:entries) {
			String type = (String) entry.getKey();
			String value = (String) entry.getValue();
			if(!e.getTitle().startsWith(type)) continue;
			modulename = value;
			break;
		}
		modulename = StringUtils.isBlank(modulename)?moduleTypes.getProperty("other"):modulename;
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
		}else if("bytea".equals(sqlType)) {
			return "Binary";
		}else {
			throw new IllegalArgumentException("不合法参数类型:"+sqlType);
		}
	}
	
	public static void processType(String filename,String content) {
		try {
			int[] indexs = new int[100];
			//String content  = Utils.readFileContent(filename).toString();
			Pattern p = Pattern.compile("jAVA|oRACLE|mYSQL\":(\\{\"type\":\"[\\w\\d\\(\\)\\[\\]\\,_]+\"\\})");
			Matcher m = p.matcher(content);
			int x = 0;
			while(m.find()) {
				indexs[x]=m.start();
				x++;
			}
			StringBuilder sb = new StringBuilder(content);
			for(int index:indexs) {
				if(index==0)continue;
				char ch = sb.charAt(index);
				if(ch=='j') {
					sb = sb.replace(index, index+1, "J");
				}else if(ch=='m') {
					sb = sb.replace(index, index+1, "M");
				}else if(ch=='o') {
					sb = sb.replace(index, index+1, "O");
				}else {
					throw new RuntimeException("替换类型字符不存在"+ch);
				}
			}
			Utils.writeFileContent(filename, sb.toString());
		} catch (IOException e) {
			System.out.println("处理最终结果出错,程序停止");
			System.exit(0);
		}
		
	}
}
