package project.hms.util;

import org.springframework.util.Assert;
import project.hms.model.Base;

import java.lang.reflect.Field;

/**
 * 模型工具类
 */
public class ModelTool {

    /**
     * 合并两个模型实例
     * 将to中的空属性赋值为from总的对应属性
     *
     * @param from 源变量
     * @param to   目标变量
     * @param <T>  模型类
     */
    public static <T extends Base> void merge(T from, T to) {
        Assert.notNull(from, "from is null");
        Assert.notNull(to, "to is null");
        Assert.isTrue(from.getClass() == to.getClass(), "from and to aren't same type");

        Class clazz = from.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            if (f.getName().equals("id"))
                continue;

            try {
                f.setAccessible(true);
                Object fromVal = f.get(from);
                if (fromVal != null) {
                    f.set(to, fromVal);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
