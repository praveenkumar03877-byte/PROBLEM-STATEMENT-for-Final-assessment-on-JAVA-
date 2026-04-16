import java.util.*;

// Custom Exception
class InvalidFeeException extends Exception {
    InvalidFeeException(String message) {
        super(message);
    }
}

// Student Class
class Student {
    int studentId;
    String name;
    String email;

    Student(int studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return studentId + " - " + name + " - " + email;
    }
}

// Course Class
class Course {
    int courseId;
    String courseName;
    double fee;

    Course(int courseId, String courseName, double fee) throws InvalidFeeException {
        if (fee < 0) {
            throw new InvalidFeeException("Fee cannot be negative!");
        }
        this.courseId = courseId;
        this.courseName = courseName;
        this.fee = fee;
    }

    public String toString() {
        return courseId + " - " + courseName + " - Fee: " + fee;
    }
}

// Thread Class
class EnrollmentThread extends Thread {
    String studentName;
    String courseName;

    EnrollmentThread(String studentName, String courseName) {
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public void run() {
        System.out.println("Processing enrollment for " + studentName + "...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println(studentName + " enrolled in " + courseName);
    }
}

// Main Class
public class SmartCampusSingleFile {
    static Scanner sc = new Scanner(System.in);

    static HashMap<Integer, Student> students = new HashMap<>();
    static HashMap<Integer, Course> courses = new HashMap<>();
    static HashMap<Integer, ArrayList<Course>> enrollments = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Smart Campus Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. View Students");
            System.out.println("5. View Enrollments");
            System.out.println("6. Exit");

            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: addCourse(); break;
                case 3: enrollStudent(); break;
                case 4: viewStudents(); break;
                case 5: viewEnrollments(); break;
                case 6: System.exit(0);
                default: System.out.println("Invalid choice!");
            }
        }
    }

    static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = sc.nextInt(); sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            students.put(id, new Student(id, name, email));
            System.out.println("Student added!");
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    static void addCourse() {
        try {
            System.out.print("Enter Course ID: ");
            int id = sc.nextInt(); sc.nextLine();

            System.out.print("Enter Course Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Fee: ");
            double fee = sc.nextDouble();

            courses.put(id, new Course(id, name, fee));
            System.out.println("Course added!");
        } catch (InvalidFeeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    static void enrollStudent() {
        try {
            System.out.print("Enter Student ID: ");
            int sid = sc.nextInt();

            System.out.print("Enter Course ID: ");
            int cid = sc.nextInt();

            Student s = students.get(sid);
            Course c = courses.get(cid);

            if (s == null || c == null) {
                System.out.println("Invalid Student or Course!");
                return;
            }

            enrollments.putIfAbsent(sid, new ArrayList<>());

            if (enrollments.get(sid).contains(c)) {
                System.out.println("Already enrolled!");
                return;
            }

            enrollments.get(sid).add(c);

            new EnrollmentThread(s.name, c.courseName).start();

        } catch (Exception e) {
            System.out.println("Invalid Input!");
        }
    }

    static void viewStudents() {
        for (Student s : students.values()) {
            System.out.println(s);
        }
    }

    static void viewEnrollments() {
        for (int sid : enrollments.keySet()) {
            System.out.println("Student ID: " + sid);
            for (Course c : enrollments.get(sid)) {
                System.out.println("   " + c);
            }
        }
    }
}
