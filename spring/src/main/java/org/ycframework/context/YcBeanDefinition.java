package org.ycframework.context;

/**
 * 对一个Bean的特征的包装类
 * 特征 scope
 *      lcay (false|true) 懒加载
 *      primary  主实例|优先实例
 *
 */
public class YcBeanDefinition {
    private boolean isLazy;
    private String scope="singleton";
    private  boolean isPrimary;
    //...
    private String classInfo;

    public boolean isLazy() {
        return isLazy;
    }

    public void setLazy(boolean lazy) {
        isLazy = lazy;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public String getClassInfo() {
        return classInfo;
    }

    public void setClassInfo(String classInfo) {
        this.classInfo = classInfo;
    }
}
