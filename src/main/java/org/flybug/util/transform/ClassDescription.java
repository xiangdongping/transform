package org.flybug.util.transform;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 缓存的类描述
 * Created by xdp on 2018/7/1.
 */
public class ClassDescription {

    //get 方法集合
    private Map<String,Method> getMethods=new HashMap<>();
    //set 方法集合
    private Map<String,Method> setMethods=new HashMap<>();
    //属性
    private Map<String,Field> fields=new HashMap<>();
    //类
    private  Class clazz;
    //类名
    private String className;



    public  static  ClassDescription build(Class clazz){

        ClassDescription desc=new ClassDescription();

        Field[] declaredFields = clazz.getDeclaredFields();
        Method[] declaredMethod=clazz.getMethods();




        // 解析所有的属性，忽略 静态 常量 transient修饰 的字段
        for (Field declaredField : declaredFields) {
            int modifiers = declaredField.getModifiers();
            if(Modifier.isStatic(modifiers)
                    ||Modifier.isFinal(modifiers)
                    ||Modifier.isTransient(modifiers)){
                continue;
            }

            String filedName = declaredField.getName();


            String methodSuffix = filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
            String settingName = "set" + methodSuffix;
            String gettingName = "get" + methodSuffix;

            try {

                Method setMethod = clazz.getDeclaredMethod(settingName, declaredField.getType());
                Method getMethod = clazz.getDeclaredMethod(gettingName, null);
                desc.setMethods.put(filedName,setMethod);
                desc.getMethods.put(filedName,getMethod);


            }catch (Throwable throwable){
                throw  new RuntimeException(throwable);
            }
            declaredField.setAccessible(true);
            desc.fields.put(filedName,declaredField);
        }
        desc.clazz=clazz;
        desc.className=clazz.getName();
        return  desc;
    }




    public Map<String, Method> getGetMethods() {
        return getMethods;
    }

    public Map<String, Method> getSetMethods() {
        return setMethods;
    }

    public Map<String, Field> getFields() {
        return fields;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getClassName() {
        return className;
    }
}
