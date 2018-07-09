package org.flybug.util.transform.impl;

import org.flybug.util.transform.ClassDescription;
import org.flybug.util.transform.TransForm;
import org.flybug.util.transform.annotaction.CopyIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 转换抽象实现
 * Created by xdp on 2018/7/1.
 */
public abstract class AbstractTranForm implements TransForm{
    /**
     * 日志记录器
     */
    public Logger logger= LoggerFactory.getLogger(getClass());

    /**
     * 缓冲
     */
    protected  Map<Class,ClassDescription> cacheClazzs=new ConcurrentHashMap<Class, ClassDescription>();

    /**
     * 是否忽略源null值
     */
    private  boolean isIgnoreSourceNull=true;


    /**
     * 空参数
     */
    protected final Object[] nullArgs =new Object[]{};



    /**
     * 获取class Description
     * 该方法在缓存中没有 将
     * class 加入缓存返回
     * @param clazz
     * @return
     */
    public  ClassDescription resolve(Class clazz){
        ClassDescription classDescription = cacheClazzs.get(clazz);
        if(classDescription == null){
            classDescription=ClassDescription.build(clazz);
            cacheClazzs.put(clazz,classDescription);
        }
        return  classDescription;
    }


    @Override
    public <T> T transform(Object source, Class<T> targetClass) {

        Object targetEntity=null;
        try {
            targetEntity = targetClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        fill(source,targetEntity);

        return  (T)targetEntity;
    }

    @Override
    public void fill(Object source, Object target, boolean ignoreNull) {
        assertNotNull(source,"source can't is null");
        assertNotNull(target,"target can't is null");

        ClassDescription sourceDesc= resolve(source.getClass());
        ClassDescription targetDesc= resolve(target.getClass());

        Map<String, Field> fields = sourceDesc.getFields();

        Map<String, Method> getMethods = sourceDesc.getGetMethods();
        Map<String, Method> setMethods = targetDesc.getSetMethods();

        //始终遍历属性少的一方
        Set<String> fileNames=getMethods.size() > setMethods.size() ? setMethods.keySet():getMethods.keySet();

        try {
            Map<String, CopyIgnore> sourceDescIgnoreProperties = sourceDesc.getIgnoreProperties();
            Map<String, CopyIgnore> targetDescIgnoreProperties= targetDesc.getIgnoreProperties();


            for (String fieldName : fileNames) {

                CopyIgnore sourceIgnore= sourceDescIgnoreProperties.get(fieldName);
                CopyIgnore targetIgnore = targetDescIgnoreProperties.get(fieldName);

                if((sourceIgnore !=null && sourceIgnore.get())
                        || (targetIgnore !=null && targetIgnore.set()))
                    continue;

                Method method = getMethods.get(fieldName);
                Method setMethod = setMethods.get(fieldName);
                //获取值
                Object val = method == null? null : method.invoke(source, nullArgs);

                if(setMethod == null){
                    continue;
                }
                //如果源值为null 并且忽略null值时
                if(val ==null && ignoreNull){
                    continue;
                }

                setMethod.invoke(target,val);
            }
        }catch (Throwable e){
            if(e instanceof  ClassCastException){
                throw  new RuntimeException("源数据类型 和目标数据类型 不兼容 "+e.getMessage());
            }
            logger.error("fill  error msg {} ",fileNames);
        }
    }


    @Override
    public Map<String, Object> transformToMap(Object source) {

        Map result=new HashMap();

        if(source == null){
            return  result;
        }
        ClassDescription resolve = resolve(source.getClass());

        Map<String, Method> getMethods = resolve.getGetMethods();

            getMethods.forEach((k,v) ->{
                Object val = null;
                try {
                    val = v.invoke(source, nullArgs);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                result.put(k,val);
            });
        return  result;
    }

    public void fill(Object source, Object target) {
        fill(source,target,isIgnoreSourceNull);
    }



    protected  static  void assertNotNull(Object source,String message){
        if(source == null){
            throw  new RuntimeException(message);
        }
    }
}
