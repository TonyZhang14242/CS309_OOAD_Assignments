package dependency_injection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.*;

/**
 * TODO you should complete the class
 */
public class BeanFactoryImpl implements BeanFactory {
    Properties injectprops;
    Properties valueprops;
    Map<Class<?>, Class<?>> injectMap = new HashMap<>();
    Map<String, String> valueMap = new HashMap<>();

    @Override
    public void loadInjectProperties(File file) {
        injectprops = new Properties();
        try {
            InputStream stream = new FileInputStream(file);
            injectprops.load(stream);
            for(Object o:injectprops.keySet()){
                Class<?> abstractclass = Class.forName(o.toString());
                Class<?> implclass = Class.forName(injectprops.get(o).toString());
                injectMap.put(abstractclass, implclass);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void loadValueProperties(File file) {
        valueprops = new Properties();
        try {
            InputStream stream = new FileInputStream(file);
            valueprops.load(stream);
            for(Object o:valueprops.keySet()){
                String field = o.toString();
                String value = valueprops.get(o).toString();
                valueMap.put(field, value);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public <T> T createInstance(Class<T> clazz) {
        Class<?> implclass;
        if (injectMap.containsKey(clazz))
            implclass = injectMap.get(clazz);
        else
            implclass = clazz;
        Constructor<?>[] injectConstructors = implclass.getDeclaredConstructors();
        Constructor<?> selectedConstructor = null;
        boolean hasAnnotation = false;
        for (Constructor<?> constructor:injectConstructors){
            if (constructor.isAnnotationPresent(Inject.class)){
                selectedConstructor = constructor;
                hasAnnotation = true;
            }
        }
        if (!hasAnnotation) {
            try {
                selectedConstructor = implclass.getDeclaredConstructor();
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        Object[] objects;
        Parameter[] parameters = selectedConstructor.getParameters();
        //objects = new Object[parameters.length];
        objects = createObjects(parameters);
        T instance;
        try {
            instance = (T) selectedConstructor.newInstance(objects);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        injectionField(instance);
        return instance;
    }

    public Object[] createObjects(Parameter[] parameters){
        Object[] objs = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Class<?> objtype = parameters[i].getType();
            //System.out.println(parameters[i].getParameterizedType());
            if (!parameters[i].isAnnotationPresent(Value.class)){
                objs[i] = createInstance(objtype);
            }
            else {
                Value valueins = parameters[i].getAnnotation(Value.class);
                if (objtype == boolean.class || objtype == String.class || objtype == int.class || objtype == Integer.class || objtype == Boolean.class
                ||objtype == boolean[].class || objtype == int[].class || objtype == String[].class ||objtype == Integer[].class || objtype == Boolean[].class ||
                objtype == List.class || objtype == Set.class || objtype == Map.class){
                    String injectvalue;
                    if (valueMap.containsKey(valueins.value()))
                        injectvalue = valueMap.get(valueins.value());
                    else
                        injectvalue = valueins.value();
                    objs[i] = processing(injectvalue, valueins.delimiter(), objtype, parameters[i].getParameterizedType());
                }
            }
        }
        return objs;
    }

    public <T> void injectionField(T instance){
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field f: fields){
            Class<?> objtype = f.getType();
            //System.out.println(f.getGenericType());
            if (f.isAnnotationPresent(Inject.class)){
                Object o = createInstance(f.getType());
                try {
                    f.setAccessible(true);
                    f.set(instance, o);
                    f.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (objtype == boolean.class || objtype == String.class || objtype == int.class || objtype == Integer.class || objtype == Boolean.class
                    ||objtype == boolean[].class || objtype == int[].class || objtype == String[].class ||objtype == Integer[].class || objtype == Boolean[].class ||
                    objtype == List.class || objtype == Set.class || objtype == Map.class){
                if (f.isAnnotationPresent(Value.class)){
                    String injectvalue;
                    if (valueMap.containsKey(f.getAnnotation(Value.class).value()))
                        injectvalue = valueMap.get(f.getAnnotation(Value.class).value());
                    else
                        injectvalue = f.getAnnotation(Value.class).value();
                    Object o = processing(injectvalue, f.getAnnotation(Value.class).delimiter(), objtype, f.getGenericType());
                    f.setAccessible(true);
                    try {
                        f.set(instance, o);
                        f.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
    }

    public Object processing(String s, String spliter, Class<?> type, Type generictype){
        String[] splitted = s.split(spliter);
        Object defres=null;
        //System.out.println(generictype);
        if (type == int.class){
            int a = 0;
            for (int i = 0; i < splitted.length; i++) {
                try {
                    a = Integer.parseInt(splitted[i]);
                    return a;
                }
                catch (Exception e){
                    continue;
                }
            }
            return 0;
        }
        else if (type == boolean.class){
            boolean flag = false;
            for (int i = 0; i < splitted.length; i++) {
                try {
                    if (splitted[i].equalsIgnoreCase("true"))
                        flag = true;
                    if (splitted[i].equalsIgnoreCase("false"))
                        flag = false;
                    return flag;
                }
                catch (Exception e){
                    continue;
                }
            }
            return false;
        }
        else if (type == int[].class || type == Integer[].class) {
            splitted[0] = splitted[0].substring(1);
            splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
            int cnt =0;
            for (int i = 0; i < splitted.length; i++) {
                int a =0;
                try {
                    a = Integer.parseInt(splitted[i]);
                    cnt++;
                }
                catch (Exception e){
                    continue;
                }
            }
            int[] res = new int[cnt];
            cnt = 0;
            for (int i = 0; i < splitted.length; i++) {
                int a = 0;
                try {
                    a = Integer.parseInt(splitted[i]);
                    res[cnt] = a;
                    cnt++;
                }
                catch (Exception e){
                    continue;
                }
            }
            return res;
        }
        else if (type == boolean[].class || type== Boolean[].class){
            splitted[0] = splitted[0].substring(1);
            splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
            int cnt =0;
            for (int i = 0; i < splitted.length; i++) {
                boolean flag =false;
                try {
                    if (splitted[i].equalsIgnoreCase("true")||splitted[i].equalsIgnoreCase("false"))
                        cnt++;
                }
                catch (Exception e){
                    continue;
                }
            }
            boolean[] res = new boolean[cnt];
            cnt = 0;
            for (int i = 0; i < splitted.length; i++) {
                boolean flag = false;
                try {
                    if (splitted[i].equalsIgnoreCase("true")){
                        res[cnt] = true;
                        cnt++;
                    }

                    else if (splitted[i].equalsIgnoreCase("false")){
                        res[cnt] = false;
                        cnt++;
                    }
                }
                catch (Exception e){
                    continue;
                }
            }
            return res;
        }
        else if (type == String.class){
            for (int i=0;i<splitted.length;i++)
                    return splitted[i];
        }
        else if (type == String[].class) {
            splitted[0] = splitted[0].substring(1);
            splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
            if (s.equals("[]"))
                return new String[0];
            int cnt =0;
            for (int i=0;i<splitted.length;i++)
                    cnt++;
            String[] res = new String[cnt];
            cnt = 0;
            for (int i=0;i<splitted.length;i++) {
                    res[cnt] = splitted[i];
                    cnt++;
            }
            return res;
        }
        else if (type == List.class){
            //System.out.println(Arrays.toString(splitted));
            if (s.equals("[]"))
                return new ArrayList<>();
            if (generictype.getTypeName().equals("java.util.List<java.lang.Integer>")){
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                List<Integer> l = new ArrayList<>();
                for (int i = 0; i < splitted.length; i++) {
                    int a;
                    try {
                        a = Integer.parseInt(splitted[i]);
                        l.add(a);
                    }
                    catch (Exception e){
                        continue;
                    }
                }
                return l;
            }
            else if (generictype.getTypeName().equals("java.util.List<java.lang.String>")) {
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                List<String> l = new ArrayList<>();
                for (int i = 0; i < splitted.length; i++) {
                    if (!splitted[i].equals(""))
                        l.add(splitted[i]);
                }
                return l;
            }
            else if (generictype.getTypeName().equals("java.util.List<java.lang.Boolean>")) {
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                List<Boolean> l = new ArrayList<>();
                for (int i = 0; i < splitted.length; i++) {
                    if (splitted[i].equalsIgnoreCase("true"))
                        l.add(true);
                    if (splitted[i].equalsIgnoreCase("false"))
                        l.add(false);
                }
                return l;
            }


        }
        else if (type == Set.class){
            if (s.equals("{}"))
                return new HashSet<>();
            if (generictype.getTypeName().equals("java.util.Set<java.lang.Integer>")){
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                Set<Integer> l = new HashSet<>();
                for (int i = 0; i < splitted.length; i++) {
                    int a;

                    try {
                        a = Integer.parseInt(splitted[i]);
                        l.add(a);
                    }
                    catch (Exception e){
                        continue;
                    }
                }
                return l;
            }
            else if (generictype.getTypeName().equals("java.util.Set<java.lang.String>")) {
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                Set<String> l = new HashSet<>();
                for (int i = 0; i < splitted.length; i++) {
                    if (!splitted[i].equals(""))
                        l.add(splitted[i]);
                }
                return l;
            }
            else if (generictype.getTypeName().equals("java.util.Set<java.lang.Boolean>")) {
                splitted[0] = splitted[0].substring(1);
                splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                Set<Boolean> l = new HashSet<>();
                for (int i = 0; i < splitted.length; i++) {
                    if (splitted[i].equalsIgnoreCase("true"))
                        l.add(true);
                    if (splitted[i].equalsIgnoreCase("false"))
                        l.add(false);
                }
                return l;
            }
        }
        else if (type == Map.class){
            /*TODO: Map<String, String>, Map<String, Integer>, Map<String, Boolean>,
            *  Map<Integer, String>, Map<Integer, Integer>, Map<Integer, Boolean>,
            *  Map<Boolean, Integer>, Map<Boolean, String>, Map<Boolean, Boolean>
            * */
            int idx=0;
            //System.out.println(generictype);
            if (generictype.getTypeName().equals("java.util.Map<java.lang.String, java.lang.String>")){
                idx = 0;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.String, java.lang.Integer>")) {
                idx =1;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.String, java.lang.Boolean>")) {
                idx =2;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Integer, java.lang.String>")) {
                idx = 3;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Integer, java.lang.Integer>")) {
                idx =4;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Integer, java.lang.Boolean>")) {
                idx = 5;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Boolean, java.lang.String>")) {
                idx =6;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Boolean, java.lang.Integer>")) {
                idx =7;
            } else if (generictype.getTypeName().equals("java.util.Map<java.lang.Boolean, java.lang.Boolean>")) {
                idx = 8;
            }
            switch (idx){
                case 0://S S
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            map.put(key, value);
                        }

                    }
                    return map;
                case 1://S I
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<String, Integer> map1 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                int valueint = Integer.parseInt(value);
                                map1.put(key, valueint);
                            }
                            catch (Exception e){
                                continue;
                            }

                        }

                    }
                    return map1;
                case 2:// S B
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<String, Boolean> map2 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                if (value.equalsIgnoreCase("true"))
                                    map2.put(key, true);
                                if (value.equalsIgnoreCase("false"))
                                    map2.put(key, false);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map2;
                case 3:// I S
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Integer, String> map3 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                int keyint = Integer.parseInt(key);
                                map3.put(keyint, value);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map3;
                case 4:// I I
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Integer, Integer> map4 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                int keyint = Integer.parseInt(key);
                                int valueint = Integer.parseInt(value);
                                map4.put(keyint, valueint);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map4;
                case 5://I B
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Integer, Boolean> map5 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                int keyint = Integer.parseInt(key);
                                if (value.equalsIgnoreCase("true"))
                                    map5.put(keyint, true);
                                if (value.equalsIgnoreCase("false"))
                                    map5.put(keyint, false);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map5;
                case 6://B S
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Boolean, String> map6 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                if (key.equalsIgnoreCase("true"))
                                    map6.put(true, value);
                                if (key.equalsIgnoreCase("false"))
                                    map6.put(false, value);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map6;
                case 7://B I
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Boolean, Integer> map7 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                int valueint = Integer.parseInt(value);
                                if (key.equalsIgnoreCase("true"))
                                    map7.put(true, valueint);
                                if (key.equalsIgnoreCase("false"))
                                    map7.put(false, valueint);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map7;
                case 8://B B
                    splitted[0] = splitted[0].substring(1);
                    splitted[splitted.length-1] = splitted[splitted.length-1].substring(0,splitted[splitted.length-1].length()-1);
                    Map<Boolean, Boolean> map8 = new HashMap<>();
                    for (int i = 0; i < splitted.length; i++) {
                        String key, value;
                        if (!splitted[i].equals("")){
                            key = splitted[i].split(":")[0];
                            value = splitted[i].split(":")[1];
                            try {
                                if (key.equalsIgnoreCase("true") && value.equalsIgnoreCase("true"))
                                    map8.put(true, true);
                                if (key.equalsIgnoreCase("true") && value.equalsIgnoreCase("false"))
                                    map8.put(true, false);
                                if (key.equalsIgnoreCase("false") && value.equalsIgnoreCase("true"))
                                    map8.put(false, true);
                                if (key.equalsIgnoreCase("false") && value.equalsIgnoreCase("false"))
                                    map8.put(false, false);
                            }
                            catch (Exception e){
                                continue;
                            }
                        }
                    }
                    return map8;

            }
            return new HashMap<>();
        }


        return defres;
    }
}
