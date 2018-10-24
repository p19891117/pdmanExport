package top.tangshitai.pdmanPlugin.pdmanPlugin;

import java.io.IOException;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.Module;
import top.tangshitai.pdmanPlugin.pdmanPlugin.bean.PDMan;
import top.tangshitai.pdmanPlugin.pdmanPlugin.stategrid.StateGrid;
import top.tangshitai.pdmanPlugin.pdmanPlugin.utils.Utils;


public class App {
	public static void main(String[] args) throws IOException {
		test1();
	}
	public static void test1() throws IOException {
		JSONObject jobj = (JSONObject) JSONObject.parse(Utils.readFileContent("stategrid.pdman.json").toString());
		PDMan pdman = jobj.toJavaObject(PDMan.class);
		StateGrid sg = new StateGrid();
		List<Module> modules = sg.builderModules(pdman);
		pdman.setModules(modules);
		String result = JSONObject.toJSONString(pdman);
		Utils.writeFileContent("stategrid.pdman.json", result);
	}
	
	
	
	
	public static void test2() throws IOException {
		JSONObject jobj = (JSONObject) JSONObject.parse(Utils.readFileContent("aaaa.pdman.json").toString());
		PDMan pdman = jobj.toJavaObject(PDMan.class);
		String result = JSONObject.toJSONString(pdman);
		System.out.println(result);
	}
}
