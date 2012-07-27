
package de.ncm.x3.iam.util;


import java.io.File;

public class PathBuilder {
	
	private String path = "";
	
	public PathBuilder() {}
	
	public PathBuilder append(String s) {
		s = s.trim();
		s = StringUtil.removeLastCharacter(s, '/');
		s = StringUtil.removeLastCharacter(s, '\\');
		
		if (path.trim().length() > 0) {
			s = StringUtil.removeFirstCharacter(s, '/');
			s = StringUtil.removeFirstCharacter(s, '\\');
			this.path += File.separator + s;
		} else {
			this.path += s;
		}
		
		return this;
	}
	
	public String getPath() {
		return path;
	}
	
	public static String createPath(String... strings) {
		PathBuilder path = new PathBuilder();
		
		for (String s : strings) {
			path.append(s);
		}
		
		return path.getPath();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		PathBuilder other = (PathBuilder) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}
}
