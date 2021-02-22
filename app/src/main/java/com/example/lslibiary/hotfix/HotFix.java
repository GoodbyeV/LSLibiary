package com.example.lslibiary.hotfix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author  : Liushuai
 * time    : 2021/2/22 20:39
 * desc    :动态类加载实现热修复简单过程
 */
class HotFix {
    /**
     *
     * @param context
     * @param patchDexFile dex文件路径 （如何将class文件打包成dex文件？）
     *                     dx --dex --no-strict --output=patch.dex class文件路径
     */
    public static void fix(Context context, File patchDexFile) {
        ClassLoader classLoader=context.getClassLoader();
        Field pathListField=findFiled(classLoader,"pathList");
        try {
            //BaseDexClassLoader pathList对象
            Object pathList = pathListField.get(classLoader);

            List<File> files = Arrays.asList(patchDexFile);
            Object[] patchDexElements = makeDexElements(pathList, files, classLoader);

            //和原有的数组进行合并

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void combineDexElements(Object pathList, Object[] patchDexElements) throws IllegalAccessException {
        Field dexElementsField = findFiled(pathList, "dexElement");
        //原有的数组
        Object[] original = (Object[]) dexElementsField.get(pathList);
        //新的数组容器
        Object[] combined= (Object[]) Array.newInstance(original.getClass().getComponentType(),patchDexElements.length+original.length);

        //将patchDexElements数组插入到新容器头部
        System.arraycopy(patchDexElements,0,combined,0,patchDexElements.length);

        System.arraycopy(original,0,combined,patchDexElements.length,original.length);

        //重新给pathList赋值
        dexElementsField.set(pathList,combined);
    }




    //创建新的数组
    private static Object[] makeDexElements(Object pathList, List<File> files,ClassLoader classLoader) throws InvocationTargetException, IllegalAccessException {
        Method method = findMethod(pathList, "makeDexElement", List.class, File.class, List.class, ClassLoader.class);
        if(method==null) return null;
        ArrayList<IOException> exceptions = new ArrayList<>();
        return (Object[]) method.invoke(pathList, files, null, exceptions, classLoader);
    }

    //获取makeDexElement方法
    public static Method findMethod(Object instance,String methodName,Class<?>...paramterTypes){
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(methodName,paramterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    private static Field findFiled(Object instance, String filedName) {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(filedName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;

            } catch (NoSuchFieldException e) {
              e.printStackTrace();
            }
        }
        return null;
    }
}
