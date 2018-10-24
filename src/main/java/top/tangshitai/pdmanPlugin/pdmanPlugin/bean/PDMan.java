package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.List;

public class PDMan {
	private List<Module> modules;
	private DataTypeDomain dataTypeDomains;
	private Profile profile;
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public DataTypeDomain getDataTypeDomains() {
		return dataTypeDomains;
	}
	public void setDataTypeDomains(DataTypeDomain dataTypeDomains) {
		this.dataTypeDomains = dataTypeDomains;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
