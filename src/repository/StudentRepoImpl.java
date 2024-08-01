package repository;

import model.Student;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentRepoImpl implements IStudentRepo {

    private final static List<Student> students = new ArrayList<>();
    static {
        students.add(new Student(1, "HV-001", "Hung", LocalDate.parse("2004-10-10"), "hungCGHN@gmail.com", "C0324M4"));
        students.add(new Student(2, "HV-002", "Trung", LocalDate.parse("2003-12-12"), "trungCGHN@gmail.com", "C0324M4"));
        students.add(new Student(3, "HV-003", "Khanh", LocalDate.parse("2002-05-14"), "hungCGHN@gmail.com", "C0324M4"));
    }

    @Override
    public List<Student> findAll() {
        return students;
    }

    public boolean addStudent(int id, String code, String name, LocalDate birthday, String email, String className) {
        return students.add(new Student(id, code, name, birthday, email, className));
    }

    @Override
    public boolean removeStudent(int id) {
        return students.removeIf(student -> student.getId() == id);
    }

    @Override
    public boolean updateStudent(int id, String name, LocalDate birthday, String email, String className) {
        boolean found = false;
        for (Student student : students) {
            if (student.getId() == id) {
                student.setName(name);
                student.setBirthday(birthday);
                student.setEmail(email);
                student.setClassName(className);
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        List<Student> studentList = new ArrayList<>();
        for(Student student : students) {
            if(student.getName().contains(name)){
                studentList.add(student);
            }
        }
        return studentList;
    }
}
