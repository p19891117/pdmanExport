package top.tangshitai.pdmanPlugin.pdmanPlugin.stategrid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Entitie;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Module;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.PDMan;
import top.tangshitai.pdmanPlugin.pdmanPlugin.utils.SqlUtils;

public class StateGrid {
	public List<Module> builderModules(PDMan pdMan) {
		List<Module> modules = new ArrayList<>();
		pdMan.setModules(modules);
		//获取字符串形式的数据表结构
		List<String[]> sqlstrs = SqlUtils.sqlToListForTable("stategrid.sql");
		for(String[] sqlstr:sqlstrs) {
			//解析数据表结构为对象Entitie
			Entitie entitie = SqlUtils.tableStrToObj(sqlstr[0], sqlstr[1]);
			//将Entitie设置到对应module对象上
			SqlUtils.setEntitieToModule(modules, entitie);
		}
		return modules;
	}
	private void tableNameTofile(String[] sqlstr) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("table_comment.properties",true));
			writer.write(sqlstr[0]+"=");
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

