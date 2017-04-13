package com.practice.li.rollingdesign.mvpframe;

import java.lang.reflect.ParameterizedType;

/**
 * Created by baixiaokang on 16/4/30.
 */

/**
 * 用于获取已实现的抽象或接口类的对象。
 */
public class TUtil {
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass()))//通过反射取得父类泛型类型数组
                    .getActualTypeArguments()[i])//取得数组中的某一类型
                    .newInstance();//构造该类型对象
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过类全路径名获取类的字节码
     * @param className
     * @return
     */
    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
