package util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

public class JSONUtils {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static ObjectMapper getMapper() {
		return mapper;
	}

	static {
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		mapper.getDeserializationConfig()
				.set(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
						false);
	//	mapper.setDeserializationConfig(JsonSerialize.Inclusion.NON_NULL.NON_NULL);
	}

	/**
	 * 灏唈ava瀵硅薄杞崲鎴恓son瀛楃涓�
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSON(Object obj) {
		try {
			if (obj == null) {
				return null;
			} else {
				return mapper.writeValueAsString(obj);
			}
		} catch (Exception ex) {
			throw new RuntimeException("杞崲Json寮傚父!", ex);
		}
	}

	/**
	 * 灏唈son瀛楃涓叉暟缁勮浆鎹㈡垚java鐨凩ist瀵硅薄锛屽璞¤浆鎹㈡垚java鐨凪ap瀵硅薄
	 * 
	 * @param json
	 * @return
	 */
	public static Object fromJSON(String json) {
		return fromJSON(json, Object.class);
	}

	/**
	 * 灏唈son瀛楃涓叉暟缁勮浆鎹㈡垚鎸囧畾绫诲瀷鐨勫璞�
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T fromJSON(String json, Class<T> type) {
		try {
			if (json == null) {
				return null;
			} else {
				return mapper.readValue(json, type);
			}
		} catch (Exception ex) {
			throw new RuntimeException("瑙ｆ瀽Json寮傚父!", ex);
		}
	}

	/**
	 * 灏唈son瀛楃涓叉暟缁勮浆鎹㈡垚鎸囧畾绫诲瀷鐨勫璞�,鏀寔娉涘瀷</br>
	 * 
	 * <p>
	 * Map&lt;String,User&gt; result = JSONUtil.fromJSON(jsonStr, new
	 * TypeReference&lt;Map&lt;String,User&gt;&gt;(){ });
	 * </p>
	 * 
	 * @param json
	 * @param typeReference
	 * @return
	 */
	public static <T> T fromJSON(String json,
			JSONUtils.TypeReference<T> typeReference) {
		try {
			if (json == null) {
				return null;
			} else {
				return (T) mapper.readValue(json, typeReference);
			}
		} catch (Exception ex) {
			throw new RuntimeException("瑙ｆ瀽Json寮傚父!", ex);
		}
	}

	public static class TypeReference<T extends Object> extends
			org.codehaus.jackson.type.TypeReference {

		public int compareTo(Object o) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

}
