package spring2.user;


import org.springframework.stereotype.Component;
import spring2.Measure;
/*
学生bmi测量接口
 */
@Component("bmiMeasure")
public class StudentBmiMeasure implements Measure {
    @Override
    public double doMeasure(Object obj) {
        if (obj==null){
            throw new RuntimeException("待数据异常");
        }
        if (!(obj instanceof Student)){
            throw new RuntimeException("待数据异常");
        }
        Student s=(Student) obj;
        return s.getWeight()/(s.getHeight()*s.getHeight());
    }
}
