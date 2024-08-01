package controller;

import service.IStudentService;
import service.StudentServiceImpl;
import model.Student;


import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class StudentController {
    private final IStudentService studentService = new StudentServiceImpl();
    private final Scanner sc = new Scanner(System.in);
    // Biến nhập input từ màn hình
    public String inputCode,inputName, inputEmail, inputClassName, inputDOB;

    // In toàn bộ danh sách học viên
    public void displayAllStudents() {
        List<Student> students = studentService.findAll();
        for(Student student : students) {
            System.out.println(student);
        }
    }
    // Mã học viên có dạng HV-XXXX
    public final static String CODE_REGEX = "^HV-[0-9]{4}$";
    // Tên học viên không chứa ký tự đặc biệt
    public final static String NAME_REGEX = "^[a-zA-Z\\s]+$";
    // Ngày sinh có dạng yyyy-mm-dd
    public final static String DOB_REGEX = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$";
    // Email có dạng aB0@aB0.aB
    public final static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    // Lớp học không chứa ký tự đặc biệt
    public final static String CLASSNAME_REGEX = "^[a-zA-Z0-9\\s]+$";

    // Nhập liệu từ màn hình console

    public void inputFromConsole() {
        // Kiểm tra mã học viên
        do {
            System.out.print("Nhập mã học viên (HV-XXXX): ");
            inputCode = sc.nextLine();
            if (!inputCode.matches(CODE_REGEX)) {
                System.out.println("Mã học viên không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!inputCode.matches(CODE_REGEX));

        // Kiểm tra tên học viên
        do {
            System.out.print("Nhập tên học viên: ");
            inputName = sc.nextLine();
            if (!inputName.matches(NAME_REGEX)) {
                System.out.println("Tên học viên không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!inputName.matches(NAME_REGEX));

        // Kiểm tra ngày sinh
        do {
            System.out.print("Nhập ngày sinh HV (yyyy-mm-dd): ");
            inputDOB = sc.nextLine();
            if (!inputDOB.matches(DOB_REGEX)) {
                System.out.println("Ngày sinh không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!inputDOB.matches(DOB_REGEX));

        // Kiểm tra email
        do {
            System.out.print("Nhập email của HV: ");
            inputEmail = sc.nextLine();
            if (!inputEmail.matches(EMAIL_REGEX)) {
                System.out.println("Email không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!inputEmail.matches(EMAIL_REGEX));

        // Kiểm tra tên lớp học
        do {
            System.out.print("Nhập lớp học: ");
            inputClassName = sc.nextLine();
            if (!inputClassName.matches(CLASSNAME_REGEX)) {
                System.out.println("Tên lớp học không hợp lệ. Vui lòng nhập lại.");
            }
        } while (!inputClassName.matches(CLASSNAME_REGEX));
    }

    // Thêm học viên vào danh sách
    public void addStudent() {
        inputFromConsole();
        boolean isAdded = studentService.addStudent(0, inputCode, inputName, LocalDate.parse(inputDOB), inputEmail, inputClassName);
        if(isAdded){
            System.out.println("Danh sách sinh viên mới là: ");
            displayAllStudents();
        }
        else{
            System.out.println("Không thể thêm sinh viên!");
        }
    }

    // Xóa học viên khỏi danh sách
    public void removeStudent() {
        System.out.print("Nhập id của sinh viên cần xóa: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean isRemoved = studentService.removeStudent(id);
        if(isRemoved) {
            System.out.println("Xóa thành công!");
        }
        else{
            System.out.println("Id không tồn tại!");
        }
    }

    public void updateStudent() {
        System.out.println("Nhập id của sinh viên cần sửa: ");
        int id = Integer.parseInt(sc.nextLine());
        inputFromConsole();
        if(studentService.updateStudent(id, inputName, LocalDate.parse(inputDOB), inputEmail, inputClassName)) {
            System.out.println("Cập nhật thành công!");
        }
        else{
            System.out.println("Id không tồn tại!");
        }
    }

    public void getStudentsByName() {
        String name;
        do {
            System.out.print("Nhập tên sinh viên cần tìm kiếm: ");
            name = sc.nextLine();
            if(name.isEmpty())
                System.out.println("Bạn chưa nhập tên để tìm kiếm!\n");
        }while (name.isEmpty());
        List<Student> studentList = studentService.getStudentsByName(name);
        for(Student student : studentList){
            System.out.println(student);
        }
    }

    public void exportToCSV() {
        try ( FileWriter fileWriter = new FileWriter("src/view/students.csv", false);
              BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) )
        {
            bufferedWriter.write("Class\tID\tCode\tName\tBirthday\tEmail\n");
            List<Student> students = studentService.findAll();
            for(Student student : students) {
                bufferedWriter.write(student.getClassName() + "\t"
                        + student.getId() + "\t"
                        + student.getCode() + "\t"
                        + student.getName() + "\t"
                        + student.getBirthday() + "\t"
                        + student.getEmail());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     CRUD using file CSV
     Create: Thêm học viên vào file CSV
     Read: Đọc toàn bộ học viên từ file CSV
     Update: Cập nhật thông tin học viên trong file CSV
     Delete: Xóa học viên khỏi file CSV
     */
    File file = new File("src/view/students_db.csv");

    //chuyển dữ liệu từ file CSV thành List<Student>
    public List<Student> convertCSVToList(File file) {
        List<Student> list = new ArrayList<>();
        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                Student student = new Student(Integer.parseInt(data[1]), data[2], data[3], LocalDate.parse(data[4]), data[5], data[0]);
                list.add(student);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());

        }
        return list;
    }

    public void displayAllStudentsFromCSV() {
        try (FileReader fileReader = new FileReader("src/view/students_db.csv");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Student {" +
                        "id=" + data[1] +
                        ", code='" + data[2] + '\'' +
                        ", name='" + data[3] + '\'' +
                        ", birthday=" + data[4] +
                        ", email='" + data[5] + '\'' +
                        ", className='" + data[0] + '\'' +
                        '}');
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void addStudentToCSV() {
        System.out.print("Nhập Id của học viên: ");
        int inputID = Integer.parseInt(sc.nextLine());
        inputFromConsole();
        try ( FileWriter fileWriter = new FileWriter("src/view/students_db.csv", true);
              BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) )
        {
            bufferedWriter.write(inputClassName + ","
                    + inputID + ","
                    + inputCode + ","
                    + inputName + ","
                    + LocalDate.parse(inputDOB) + ","
                    + inputEmail);
            bufferedWriter.newLine();
            System.out.println("Thêm sinh viên thành công!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateStudentInCSV() {
        System.out.println("Nhập ID của sinh viên cần sửa: ");
        int inputID = Integer.parseInt(sc.nextLine());
        inputFromConsole();
        List<Student> students = convertCSVToList(file);
        boolean isExisted = false; // kiểm tra xem id co tồn tại không
        for(Student student : students) {
            if(student.getId() == inputID) {
                student.setName(inputName);
                student.setBirthday(LocalDate.parse(inputDOB));
                student.setEmail(inputEmail);
                student.setClassName(inputClassName);
                isExisted = true;
                break;
            }
        }
        if(isExisted) {
            try ( FileWriter fileWriter = new FileWriter("src/view/students_db.csv", false);
                  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) )
            {
                for(Student student : students) {
                    bufferedWriter.write(student.getClassName() + ","
                            + student.getId() + ","
                            + student.getCode() + ","
                            + student.getName() + ","
                            + student.getBirthday() + ","
                            + student.getEmail());
                    bufferedWriter.newLine();
                }
                System.out.println("Cập nhật thành công!");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("ID không tồn tại!");
        }
    }

    public void deleteStudentInCSV() {
        System.out.print("Nhập ID của sinh viên cần xóa: ");
        int inputID = Integer.parseInt(sc.nextLine());
        List<Student> students = convertCSVToList(file);
        boolean isExisted = false; // kiểm tra xem id có tồn tại không
        for(Student student : students) {
            if(student.getId() == inputID) {
                students.remove(student);
                isExisted = true;
                break;
            }
        }
        if(isExisted) {
            try ( FileWriter fileWriter = new FileWriter("src/view/students_db.csv", false);
                  BufferedWriter bufferedWriter = new BufferedWriter(fileWriter) )
            {
                for(Student student : students) {
                    bufferedWriter.write(student.getClassName() + ","
                            + student.getId() + ","
                            + student.getCode() + ","
                            + student.getName() + ","
                            + student.getBirthday() + ","
                            + student.getEmail());
                    bufferedWriter.newLine();
                }
                System.out.println("Xóa thành công!");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        else{
            System.out.println("ID không tồn tại!");
        }
    }

}
