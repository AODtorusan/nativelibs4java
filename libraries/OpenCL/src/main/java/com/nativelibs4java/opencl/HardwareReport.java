/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nativelibs4java.opencl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author ochafik
 */
public class HardwareReport {
	public static Map<String, Method> infoMethods(Class<?> c) {
		Map<String, Method> mets = new TreeMap<String, Method>();
		for (Method met : c.getMethods()) {
			InfoName name = met.getAnnotation(InfoName.class);
			if (name == null)
				continue;
			mets.put(name.value(), met);
		}
		return mets;
	}
	public static List<Map<String, Object>> listInfos() {
		try {
			List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
			Map<String, Method> platMets = infoMethods(CLPlatform.class);
			Map<String, Method> devMets = infoMethods(CLDevice.class);
			for (CLPlatform platform : OpenCL4Java.listPlatforms()) {
				Map<String, Object> platInfos = new TreeMap<String, Object>();
				for (Map.Entry<String, Method> platMet : platMets.entrySet())
					platInfos.put(platMet.getKey(), platMet.getValue().invoke(platform));
				for (CLDevice device : platform.listAllDevices(false)) {
					Map<String, Object> devInfos = new TreeMap<String, Object>(platInfos);
					for (Map.Entry<String, Method> devMet : devMets.entrySet())
						devInfos.put(devMet.getKey(), devMet.getValue().invoke(device));
					ret.add(devInfos);
				}
			}
			return ret;
		} catch (Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	public static String toString(Object value) {
		if (value == null)
			return "null";
		Class<?> c = value.getClass();
		try {
			if (c.isArray()) {
				if (!c.getComponentType().isPrimitive())
					c = Object[].class;
				return (String)Arrays.class.getMethod("toString", c).invoke(null, value);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return value.toString();
	}
	public static String toTable(List<Map<String, Object>> list) {
		StringBuilder b = new StringBuilder();

		b.append("<table>\n");
		if (!list.isEmpty()) {
			Set<String> keys = list.get(0).keySet();

			b.append("\t<tr valign=\"top\">\n");
			b.append("\t\t<td></td>");
			for (Map<String, Object> device : list) {
				Object value = device.get("CL_DEVICE_NAME");
				b.append("<td><b>[" + toString(value) + "]</b></td>\n");
			}
			b.append("\n");
			b.append("\t</tr>\n");

			for (String key : keys) {
				b.append("\t<tr valign=\"top\">\n");
				b.append("\t\t<td>" + key + "</td>");
				for (Map<String, Object> device : list) {
					Object value = device.get(key);
					b.append("<td>" + toString(value) + "</td>");
				}
				b.append("\n");
				b.append("\t</tr>\n");
			}
		}

		b.append("</table>\n");
		return b.toString();
	}
	public static String toHTML(List<Map<String, Object>> list) {
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
				"<body>\n" +
				toTable(list) +
				"</body></html>";
	}
	public static void main(String[] args) {
		try {
			File file = new File(args.length == 0 ? "HardwareReport.html" : args[0]);

			List<Map<String, Object>> list = listInfos();
			String html = toHTML(list);
			Writer w = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
			w.write(html);
			w.close();
			System.out.println("The OpenCL Hardware Report was successfully written to the following file:\n" +
					"\t" + file.getCanonicalPath() +
					"\n" +
					"Thanks to send it to the NativeLibs4Java group :\n" +
					"\thttp://groups.google.fr/group/nativelibs4java/");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
