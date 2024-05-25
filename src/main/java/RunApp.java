import dao.StudentService;
import model.Student;
import java.util.List;
import java.util.Scanner;

public class RunApp {

    private static Scanner input;

    public static void main(String[] args) {

        while (true){

            System.out.println("\n ****MENU**** \n");
            System.out.print("[1].Insert New Student \n");
            System.out.print("[2].Update Student Details By ID \n");
            System.out.print("[3].View Student Details By ID \n");
            System.out.print("[4].View All Students \n");
            System.out.print("[5].Delete Student By ID \n");
            System.out.print("[6].Exit \n\n");

            int choice;

            StudentService studentService=new StudentService();

            input=new Scanner(System.in);
            System.out.print("Enter Your Operation :");
            choice=input.nextInt();

            switch (choice){

                case 1:
                    Student student=new Student();

                    input=new Scanner(System.in);
                    System.out.print("Enter First Name :");
                    student.setFirstName(input.nextLine());

                    input=new Scanner(System.in);
                    System.out.print("Enter Last  Name :");
                    student.setLastName(input.nextLine());

                    input=new Scanner(System.in);
                    System.out.print("Enter Email :");
                    student.setEmail(input.nextLine());

                    try {
                        studentService.saveNewStudent(student);
                    } catch (Exception e) {
                        throw new RuntimeException(e);

                    }

                    break;
                case 2:
                    input=new Scanner(System.in);
                    System.out.print("Enter Student ID :");
                    studentService.updateStudentDetails(input.nextInt());

                    break;
                case 3:

                    input=new Scanner(System.in);
                    System.out.print("Enter Student ID :");

                    Student student2=studentService.getStudent(input.nextInt());

                    if(student2 == null){
                        System.out.print("Invalid Student ID");
                    }
                    else {
                        System.out.println(student2.toString());

                    }
                    break;
                case 4:
                    List<Student> studentList=studentService.getAllStudent();

                    System.out.println("***All Students***");
                    for (Student student1:studentList){
                        System.out.println(student1.toString());
                    }
                    break;
                case 5:

                    input=new Scanner(System.in);
                    System.out.print("Enter Student ID :");
                    studentService.deleteStudentDetails(input.nextInt());
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        }
    }
}
