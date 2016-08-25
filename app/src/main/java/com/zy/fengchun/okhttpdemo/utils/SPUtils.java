package com.zy.fengchun.okhttpdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author: on 2016/8/3 10:38
 * @emial : @517na.com
 * @description
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        }
        else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        }
        else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        }
        else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        }
        else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        }
        else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }
        else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        }
        else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        }
        else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        }
        else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method SApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (SApplyMethod != null) {
                    SApplyMethod.invoke(editor);
                    return;
                }
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }

    private Context mContext;

    private SharedPreferences mSp = null;

    private SharedPreferences.Editor mEdit = null;

    /**
     * Create DefaultSharedPreferences
     *
     * @param mContext
     */
    public SPUtils(Context mContext) {
        this(mContext, PreferenceManager.getDefaultSharedPreferences(mContext));
    }

    /**
     * Create SharedPreferences by filename
     *
     * @param mContext
     * @param filename
     */
    public SPUtils(Context mContext, String filename) {
        this(mContext, mContext.getSharedPreferences(filename, Context.MODE_MULTI_PROCESS));
    }

    /**
     * Create SharedPreferences by SharedPreferences
     *
     * @param mContext
     * @param sp
     */
    public SPUtils(Context context, SharedPreferences sp) {
        this.mContext = context;
        this.mSp = sp;
        mEdit = sp.edit();
    }

    /**
     * Boolean
     *
     * @param key
     * @param value
     */
    public void setValue(String key, boolean value) {
        mEdit.putBoolean(key, value);
        mEdit.commit();
    }

    /**
     * @param resKey
     * @param value
     */
    public void setValue(int resKey, boolean value) {
        setValue(this.mContext.getString(resKey), value);
    }

    /**
     * Float
     *
     * @param key
     * @param value
     */
    public void setValue(String key, float value) {
        mEdit.putFloat(key, value);
        mEdit.commit();
    }

    /**
     * @param resKey
     * @param value
     */
    public void setValue(int resKey, float value) {
        setValue(this.mContext.getString(resKey), value);
    }

    /**
     * Integer
     *
     * @param key
     * @param value
     */
    public void setValue(String key, int value) {
        mEdit.putInt(key, value);
        mEdit.commit();
    }

    /**
     * @param resKey
     * @param value
     */
    public void setValue(int resKey, int value) {
        setValue(this.mContext.getString(resKey), value);
    }

    /**
     * Long
     *
     * @param key
     * @param value
     */
    public void setValue(String key, long value) {
        mEdit.putLong(key, value);
        mEdit.commit();
    }

    /**
     * @param resKey
     * @param value
     */
    public void setValue(int resKey, long value) {
        setValue(this.mContext.getString(resKey), value);
    }

    /**
     * String
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        mEdit.putString(key, value);
        mEdit.commit();
    }

    /**
     * @param resKey
     * @param value
     */
    public void setValue(int resKey, String value) {
        setValue(this.mContext.getString(resKey), value);
    }

    /**
     * Get Boolean
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getValue(String key, boolean defaultValue) {
        return mSp.getBoolean(key, defaultValue);
    }

    /**
     * @param resKey
     * @param defaultValue
     * @return
     */
    public boolean getValue(int resKey, boolean defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    /**
     * Get Float
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public float getValue(String key, float defaultValue) {
        return mSp.getFloat(key, defaultValue);
    }

    /**
     * @param resKey
     * @param defaultValue
     * @return
     */
    public float getValue(int resKey, float defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    /**
     * Get Integer
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public int getValue(String key, int defaultValue) {
        return mSp.getInt(key, defaultValue);
    }

    /**
     * @param resKey
     * @param defaultValue
     * @return
     */
    public int getValue(int resKey, int defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    /**
     * Get Long
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public long getValue(String key, long defaultValue) {
        return mSp.getLong(key, defaultValue);
    }

    /**
     * @param resKey
     * @param defaultValue
     * @return
     */
    public long getValue(int resKey, long defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    /**
     * Get String
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getValue(String key, String defaultValue) {
        return mSp.getString(key, defaultValue);
    }

    /**
     * @param resKey
     * @param defaultValue
     * @return
     */
    public String getValue(int resKey, String defaultValue) {
        return getValue(this.mContext.getString(resKey), defaultValue);
    }

    /**
     * Delete
     *
     * @param key
     */
    public void remove(String key) {
        mEdit.remove(key);
        mEdit.commit();
    }

    /**
     * Clear
     */
    public void clear() {
        mEdit.clear();
        mEdit.commit();
    }

}
