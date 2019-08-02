package com.yaoshun.util.collection;

import com.yaoshun.schedule.model.JobModel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fizz
 * @since 2019/7/24 10:15
 */
public class MapUtils {

	public static Map<String, Object> toMap(Object obj) throws IllegalAccessException {
		Field[] dfs = obj.getClass().getDeclaredFields();
		Map<String, Object> map = new HashMap<>();
		for (Field df : dfs) {
			df.setAccessible(true);
			map.put(df.getName(), df.get(obj));
		}
		return map;
	}

	public static void main(String[] args) throws IllegalAccessException {
		JobModel jm = new JobModel();
		jm.setId(1L);
		jm.setJobName("name");
		toMap(jm);
	}
}
