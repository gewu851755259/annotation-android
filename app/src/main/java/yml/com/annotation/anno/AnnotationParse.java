package yml.com.annotation.anno;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Annotation解析 2017/4/14 09:00
 */
public class AnnotationParse {

    /**
     * 解析 2017/4/14 09:01
     *
     * @param target 解析目标
     */
    public static void parse(final Activity target) {
        try {
            Class<?> clazz = target.getClass();
            Field[] fields = clazz.getDeclaredFields(); // 获取所有的字段 2017/4/14 09:34
            FindViewById byId;
            SetOnClickListener clkListener;
            View view;
            String name;
//            String methodName;
            int id;
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof FindViewById) {
                        byId = field.getAnnotation(FindViewById.class); // 获取FindViewById对象 2017/4/14 09:10
                        field.setAccessible(true); // 反射访问私有成员，必须进行此操作 2017/4/14 09:13

                        name = field.getName(); // 字段名 2017/4/14 09:18
                        id = byId.value();

                        // 查找对象 2017/4/14 09:15
                        view = target.findViewById(id);
                        if (view != null)
                            field.set(target, view);
                        else
                            throw new Exception("Cannot find.View name is " + name + ".");

                    } else if (annotation instanceof SetOnClickListener) { // 设置点击方法 2017/4/14 09:59
                        clkListener = field.getAnnotation(SetOnClickListener.class);
                        field.setAccessible(true);

                        // 获取变量 2017/4/14 10:00
                        id = clkListener.id();
                        final String methodName = clkListener.methodName();
                        name = field.getName();

                        view = (View) field.get(target);
                        if (view == null) { // 如果对象为空，则重新查找对象 2017/4/14 09:45
                            view = target.findViewById(id);
                            if (view != null)
                                field.set(target, view);
                            else
                                throw new Exception("Cannot find.View name is " + name + ".");
                        }

                        // 设置执行方法 2017/4/14 09:55
                        Method[] methods = clazz.getDeclaredMethods();
                        boolean isFind = false;
                        for (final Method method : methods) {
                            if (method.getName().equals(methodName)) {
                                isFind = true;
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            method.setAccessible(true); // 抑制Java的访问控制检查
                                            method.invoke(target);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                break;
                            }
                        }

                        // 没有找到 2017/4/14 09:59
                        if (!isFind) {
                            throw new Exception("Cannot find.Method name is " + methodName + ".");
                        }

                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
