package saltsheep.util;

import java.lang.reflect.Field;

import saltsheep.itembind.ItemBind;

public class ReflectHelper {

	public static Object getMCField(Object obj,String MCP,String SRG) {
		Object val = null;
		Field valField = null;
		try {
			valField = obj.getClass().getDeclaredField(MCP);
			valField.setAccessible(true);
			val = valField.get(obj);
			ItemBind.info(val);
		}catch(Exception error) {
			try {
				valField = obj.getClass().getDeclaredField(SRG);
				valField.setAccessible(true);
				val = valField.get(obj);
				ItemBind.info(val);
			} catch (Exception e) {
				ItemBind.printError(e);
			}
		}
		return val;
	}
	
	public static Object getMCFieldByClass(Class<?> cla,Object obj,String MCP,String SRG) {
		Object val = null;
		Field valField = null;
		try {
			valField = cla.getDeclaredField(MCP);
			valField.setAccessible(true);
			val = valField.get(obj);
		}catch(Exception error) {
			try {
				valField = cla.getDeclaredField(SRG);
				valField.setAccessible(true);
				val = valField.get(obj);
			} catch (Exception e) {
				ItemBind.printError(e);
			}
		}
		return val;
	}
	
}
