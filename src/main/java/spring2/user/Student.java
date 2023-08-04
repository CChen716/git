package spring2.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   //参数的set get方法
@AllArgsConstructor  //带所有参数的构造方法
@NoArgsConstructor   //无参构造方法
public class Student {
    private String name;
    private double height;
    private double weight;

}
