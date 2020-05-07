package util;

import com.google.gson.Gson;

/**
 * @author Selves
 * @Date 2020/4/30
 */
public final class GsonUtil {
    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        return GSON.fromJson(jsonStr, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
