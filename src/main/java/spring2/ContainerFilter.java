package spring2;

/**
 * 容器过滤接口
 */
public interface ContainerFilter {
    /*
    判断此对象是否为有效对象
     */
    public boolean doFilter(Object obj);
}
