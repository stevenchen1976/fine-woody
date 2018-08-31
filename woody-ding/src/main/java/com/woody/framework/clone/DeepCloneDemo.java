package com.woody.framework.clone;

import com.woody.framework.clone.demo.Student;
import com.woody.framework.clone.demo.Teacher;

public class DeepCloneDemo {

    public static void main(String[] args) {

        Teacher teacher = new Teacher("teacher_1", "English");
        Student student = new Student("张三", 14, teacher);

        Student st2 = student.deepClone();
        st2.getTeacher().setName("teacher_2");
        st2.getTeacher().setClassName("Math");

        System.out.println(st2 == student);  //false
        System.out.println(student.toString() + "............2");
        System.out.println(st2.toString() + "..............1");

    }
}
