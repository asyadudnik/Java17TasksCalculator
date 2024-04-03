package com.tasks.calculator.utils;

import com.google.gson.Gson;

import java.util.List;
//@Slf4j
public class JsonUtils {
    private JsonUtils() {
    }

    public static final Gson gson = new Gson();

/*
    public static String toJson(User entity){
        return gson.toJson(entity);
    }
*/
    public static<T> String toJson(T entity){
        return gson.toJson(entity);
    }

/*
    public static String toJson(List<User> entities){
        for (User user : entities) {
            if (log.isDebugEnabled())
                System.out.println(toJson(user), "\n\r");
        }
        return gson.toJson(entities);
    }
*/

    public static<T> String toJson(List<T> entities){
        for (T entity : entities) {
            //if (log.isDebugEnabled())
                System.out.println(toJson(entity)+ "\n\r");
        }
        return gson.toJson(entities);
    }

/*
    public static User fromJson(String json) {
        return gson.fromJson(json, User.class);
    }
*/
    public static<T> T fromJson(String json, Class<T> classOf) {
        return gson.fromJson(json, classOf);
    }
}
