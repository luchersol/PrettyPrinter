package com.prettyprinter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ObjectUtils {

    /**
     * Convierte un objeto en un Map<String, ?> donde
     * la clave es el nombre del campo y el valor es el valor del campo.
     */
    public static Map<String, Object> objectToMap(Object obj, boolean withMethod) {
        Map<String, Object> map = new HashMap<>();

        if (obj == null) return null;

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                map.put(field.getName(), null);
            }
        }

        if (withMethod) {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.getParameterCount() != 0) continue;
                try {
                    map.put(method.getName(), method.invoke(obj));
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put(method.getName(), null);
                }
            }
        }
        return map;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        return objectToMap(obj, false);
    }

    private static final Set<Class<?>> WRAPPERS = Set.of(
        Integer.class, Double.class, Character.class, Boolean.class,
        Long.class, Float.class, Short.class, Byte.class
    );

    public static boolean isWrapper(Object obj) {
        return obj != null && WRAPPERS.contains(obj.getClass());
    }

    // Ejemplo de uso
    public static void main(String[] args) {
        class Product {
            private int id = 1;
            private String name = "Laptop";
            private double price = 1200.50;
        }

        Product product = new Product();
        Map<String, ?> map = objectToMap(product);

        map.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}
