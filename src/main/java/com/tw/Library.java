package com.tw;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Library {
    private List<Student> students;

    public Library() {
        students = new ArrayList<>();
    }

    public boolean add(String string) {
        String pattern = "[\\u4e00-\\u9fa5]*\\，\\d*\\，([\\u4e00-\\u9fa5]*\\：\\d+(\\.\\d)?\\，)+([\\u4e00-\\u9fa5]*\\：\\d+(\\.\\d)?)";
        if (!Pattern.matches(pattern, string)) {
            System.out.print("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n");
            return false;
        }
        String[] stuInfo = string.split("，");
        List<Subject> subjects = new ArrayList<>();
        for (int i = 2; i < stuInfo.length; ++i) {
            String[] subInfo = stuInfo[i].split("：");
            subjects.add(new Subject(subInfo[0], Double.valueOf(subInfo[1])));
        }
        students.add(new Student(stuInfo[0], Integer.valueOf(stuInfo[1]), subjects));
        System.out.print("学生" + stuInfo[0] + "的成绩被添加\n");
        return true;
    }

    public boolean print(String string) {
        String pattern = "(\\d*(\\.\\d*)?\\，)+(\\d*(\\.\\d*)?)";
        if (!Pattern.matches(pattern, string)) {
            System.out.print("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：\n");
            return false;
        }
        String[] strings = string.split("，");
        List<Double> scores = new ArrayList<>();
        String res = "成绩单\n" + "姓名";
        for (Subject subject : students.get(0).getSubjects()) {
            res += "|" + subject.getName();
        }
        res = res + "|平均分|总分\n" + "========================\n";
        for (int i = 0; i < strings.length; ++i) {
            Student student = getStudent(Integer.valueOf(strings[i]));
            if (student != null) {
                res += student.getDisplay();
                scores.add(student.getTotalScore());
            }
        }
        Double avg = (scores.get(scores.size() / 2 - 1) + scores.get(scores.size() / 2)) / 2.0;
        String medimum = scores.size() % 2 == 0 ? Student.numberToString((scores.get(scores.size() / 2 - 1) + scores.get(scores.size() / 2)) / 2.0) : Student.numberToString(scores.get(scores.size() / 2));
        res = res + "========================\n" + "全班总分平均数：" + Student.numberToString(scores.stream().mapToDouble(value -> value).average().getAsDouble()) + "\n" + "全班总分中位数：" + medimum;
        System.out.print(res + "\n");
        return true;
    }

    public Student getStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public Student getStudent(int id, List<Student> students) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

}

