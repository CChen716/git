package spring2;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
@Component
public class Container<T> {
    private List<T> objs=new ArrayList<>();
    @Resource(name = "bmiMeasure") //此注解spring 5以上版本被弃用  需要添加javax.annotation依赖
    private Measure measure;
    @Resource(name = "bmiFilter")
    private ContainerFilter filter;

    private T max;
    private T min;
    private double avg;
    private double sum;

    public void add(T t){
            //判断t是否合格  调用筛选的实现
        if (filter!=null){
            if (filter.doFilter(t)==false){
                return;
            }
        }
        //添加到objs
        objs.add(t);
//        判断大小记录max min 计算avg 调用 measure的实现
        if (objs.size()==1){
            max=t;
            min=t;
        }else {
            //测出值 判断大小
            double val=this.measure.doMeasure(t);
            double maxval=this.measure.doMeasure(max);
            double minval=this.measure.doMeasure(min);
            if (val>maxval){
                max=t;
            }
            if (val<minval){
                min=t;
            }
        }
        sum+=measure.doMeasure(t);
        avg=sum/objs.size();

    }

    public T getMax() {
        return max;
    }

    public T getMin() {
        return min;
    }

    public double getAvg() {
        return avg;
    }

    public int size(){
        return objs.size();
    }
    /**
     * 系统复位
     */
    public void clearAll(){
        objs=new ArrayList<T>();
        measure=null;
        filter=null;
        max=null;
        min=null;
        avg=0;

    }
}
