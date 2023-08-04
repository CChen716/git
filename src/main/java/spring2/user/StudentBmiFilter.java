package spring2.user;

import org.springframework.stereotype.Component;
import spring2.ContainerFilter;

/**
 * 筛选器
 */

@Component("bmiFilter")       //bmiFilter 对象
public class StudentBmiFilter implements ContainerFilter {

    @Override
    public boolean doFilter(Object obj) {
      if (obj==null){
          return false;
      }
      if (!(obj instanceof Student)){
          return false;
      }
      Student s= (Student) obj;
      if(s.getName()==null||"".equalsIgnoreCase(s.getName())){
          return false;
      }
      if (s.getHeight()<1||s.getHeight()>2.7){
          System.out.println("身高数据异常"+obj.toString());
          return false;
      }
      if (s.getWeight()<30||s.getWeight()>500){
          System.out.println("体重数据异常"+obj.toString());
          return false;
      }
      return true;
    }
}
