package view;

import controller.StudentController;

import java.util.Scanner;

public class CodeGymManagement {
    private final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManagement();
    }

    public static void StudentManagement() {
        StudentController studentController = new StudentController();
        do{
            System.out.print(
                    "-------------Quản lí học viên-------------\n"
                            +"1. Hiển thị danh sách HV\n"
                            +"2. Thêm học viên\n"
                            +"3. Xóa học viên\n"
                            +"4. Chỉnh sửa thông tin HV\n"
                            +"5. Tìm kiếm học viên theo tên\n"
                            +"6. Xuất file CSV từ List\n"
                            +"7. Thêm học viên vào file CSV\n"
                            +"8. Hiển thị danh sách học viên từ file CSV\n"
                            +"9. Chỉnh sửa thông tin HV trong file CSV\n"
                            +"10. Xóa học viên trong file CSV\n"
                            +"0. Thoát!\n"
                            +"Nhập lựa chọn : ");
            int opt = Integer.parseInt(sc.nextLine());
            switch (opt){
                case 0 : return;
                case 1 :
                    studentController.displayAllStudents(); break;
                case 2 :
                    studentController.addStudent();
                    break;
                case 3 :
                    studentController.removeStudent();
                    break;
                case 4 :
                    studentController.updateStudent();
                    break;
                case 5:
                    studentController.getStudentsByName();
                    break;
                case 6:
                    studentController.exportToCSV();
                    break;
                case 7:
                    studentController.addStudentToCSV();
                    break;
                case 8:
                    studentController.displayAllStudentsFromCSV();
                    break;
                case 9:
                    studentController.updateStudentInCSV();
                    break;
                case 10:
                    studentController.deleteStudentInCSV();
                    break;
                default:
                    System.out.println("Yêu cầu bạn nhập đúng lựa chọn!");
            }
        }while(true);

    }
}
