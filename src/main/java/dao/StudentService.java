package dao;

import jakarta.persistence.Query;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtilService;
import java.util.List;
import java.util.Scanner;


public class StudentService {


    //Insert New Student
    public void saveNewStudent(Student student) throws Exception {
        Transaction transaction = null;

        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            org.hibernate.query.Query<Student> studentQuery=session.createQuery("FROM Student ", Student.class);

            List<Student> students=studentQuery.list();

            for (Student exsisitEmail:students){
                if(exsisitEmail.getEmail().equals(student.getEmail())){

                    System.out.println("The Enter Email have Already Exsist....");
                    transaction.commit();
                    return;

                }
            }

            String insert = "INSERT INTO Student (firstName, lastName, email) SELECT :firstName, :lastName, :email";

            Query query = session.createQuery(insert);
            query.setParameter("firstName", student.getFirstName());
            query.setParameter("lastName", student.getLastName());
            query.setParameter("email", student.getEmail());
            int result = query.executeUpdate();
            System.out.println("Insert Successful "+result);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception(e.getMessage());
        }
    }


    //Update Student Details By ID
    public void updateStudentDetails(int id){


        Transaction transaction=null;

        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            org.hibernate.query.Query<Student> studentQuery = session.createQuery("FROM Student ", Student.class);

            List<Student> students = studentQuery.list();

            int count=0;

            for (Student invaildID : students) {

                if(invaildID.getId()==id){
                    count++;
                }
            }

            if(count ==0){
                System.out.println("Invalid Student ID");
                transaction.commit();
                return;
            }
            else {
                while (true){

                    Scanner scanner=new Scanner(System.in);

                    System.out.println("***Select Update Column***\n");
                    System.out.print("[1]Update First Name\n");
                    System.out.print("[2]Update Last Name\n");
                    System.out.print("[3]Update Email\n");
                    System.out.print("[0]Goto Main Menu\n");

                    int option;

                    System.out.print("Select Option :");
                    option=scanner.nextInt();

                    if(option==0){
                        return;
                    }

                    Scanner scanner2=new Scanner(System.in);
                    System.out.print("Enter Updated Details :");
                    String details=scanner2.nextLine();

                    switch (option){

                        case 1, 3, 2:
                            updateToStudent(option,details,id);
                            break;
                        default:
                            System.out.println("Invalid Input");
                            break;
                    }
                }
            }


        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }


    }

    public void updateToStudent(int option,String value,int id){

        Transaction transaction=null;

        try(Session session=HibernateUtilService.getSessionFactory().openSession()){

            transaction=session.beginTransaction();

            Query query;

            int r;

            if(option==1){String updatefirstname="UPDATE Student SET firstName = :value WHERE id = :id";

                query=session.createQuery(updatefirstname);
                query.setParameter("value",value);
                query.setParameter("id",id);

                r=query.executeUpdate();

                System.out.println("Update Successfully "+r);

                transaction.commit();

            }else if(option==2){
                String updatelastname="UPDATE Student SET lastName = :value WHERE id = :id";

                query=session.createQuery(updatelastname);
                query.setParameter("value",value);
                query.setParameter("id",id);

                r=query.executeUpdate();

                System.out.println("Update Successfully "+r);

                transaction.commit();

            } else if (option == 3) {
                String updateEmail="UPDATE Student SET email = :value WHERE id = :id";

                query=session.createQuery(updateEmail);
                query.setParameter("value",value);
                query.setParameter("id",id);

                r=query.executeUpdate();

                System.out.println("Update Successfully "+r);

                transaction.commit();

            }

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    // View Student Details By ID
    public Student getStudent(int id){

        Transaction transaction = null;

        List<Student> students=null;

        Student student = null;
        try(Session session=HibernateUtilService.getSessionFactory().openSession()){

            transaction=session.beginTransaction();

            String getStudentByID="FROM Student WHERE id=:id";

            org.hibernate.query.Query<Student> query=session.createQuery(getStudentByID, Student.class);
            query.setParameter("id",id);

            students=query.list();

            if (students.isEmpty()){
                return student;
            }else {
                student=students.get(0);
            }
            transaction.commit();

        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
        return student;
    }

    //View All Students
    public List<Student> getAllStudent(){

        Transaction transaction=null;

        List<Student> students=null;

        try(Session session=HibernateUtilService.getSessionFactory().openSession()){

            transaction=session.beginTransaction();

            org.hibernate.query.Query<Student> studentQuery=session.createQuery("FROM Student ", Student.class);

            students=studentQuery.list();


        }catch (Exception e){

            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return students;
    }

    //Delete Student By ID
    public void deleteStudentDetails(int id) {

        Transaction transaction = null;

        try (Session session = HibernateUtilService.getSessionFactory().openSession()) {

            transaction = session.beginTransaction();

            org.hibernate.query.Query<Student> studentQuery = session.createQuery("FROM Student ", Student.class);

            List<Student> students = studentQuery.list();

            int count=0;

            for (Student invaildID : students) {

                if(invaildID.getId()==id){
                    count++;
                }
            }

            if(count ==0){
                System.out.println("Invalid Student ID");
                transaction.commit();
                return;
            }
            else {
                String delete="DELETE Student WHERE id=:id";
                Query query=session.createQuery(delete);
                query.setParameter("id",id);

                int r=query.executeUpdate();

                System.out.println("Deleted Successfully "+r);

                transaction.commit();
            }


        }catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
