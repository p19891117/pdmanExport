package top.tangshitai.pdmanPlugin.pdmanPlugin.bean;

import java.util.List;

public class Profile {
		private List<Field> defaultFields;

		public List<Field> getDefaultFields() {
			return defaultFields;
		}

		public void setDefaultFields(List<Field> defaultFields) {
			this.defaultFields = defaultFields;
		}

		public Profile(List<Field> defaultFields) {
			super();
			this.defaultFields = defaultFields;
		}

		public Profile() {
			super();
		}
		
}
