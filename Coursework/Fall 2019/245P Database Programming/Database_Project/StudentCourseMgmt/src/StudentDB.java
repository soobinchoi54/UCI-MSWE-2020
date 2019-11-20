import java.sql.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class StudentDB {
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        StudentDB connection = new StudentDB();
        connection.createConnection();
        // add method to manage and query tables connection.manageDatabase()
        connection.printOptions();
        Scanner sc = new Scanner(System.in);
        String option = sc.nextLine().toLowerCase();
        switch (option) {
            case "1":
                System.out.println("Adding new course...");
                connection.newCourse();
                System.out.println();
                System.out.println("Adding new session...");
                connection.courseSchedule();
                break;
            case "2":
                System.out.println("Adding new student...");
                connection.newStudent();
                break;
            case "3":
                System.out.println("Enrolling student in course...");
                connection.enrollStudentInCourse();
                break;
            case "4":
                System.out.println("Querying to see which students are in the course...");
                connection.queryStudentList();
                break;
            case "5":
                System.out.println("Querying to see which courses the student is in...");
                connection.queryCourseLoad();
                break;
            case "6":
                System.out.println("Querying to see course schedule...");
                connection.querySchedule();
                break;
            case "0":
                System.out.println("Exiting Program...");
                connection.exitProgram();
                break;
        }
    }

    // Create a method to connect to database
    private void createConnection() {
        Statement stmt;
        try {
            // Connect to Database
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connecting to database...");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            if (connect != null)
                System.out.println("Connected to Database");
            else
                System.out.println("Not Connected");

            System.out.println("Creating Database and Tables...");
            assert connect != null;
            stmt = connect.createStatement();

//            // To drop database
//            String dropDB = "DROP DATABASE Student_Database";
//            stmt.executeUpdate(dropDB);

            // Create a Database
            String createDB = "CREATE DATABASE IF NOT EXISTS Student_Database";
            stmt.executeUpdate(createDB);

            // Use Database
            String useDB = "USE Student_Database";
            stmt.executeUpdate(useDB);

            // Create a Table: Course Info
            String createCourseInfoTB = "CREATE TABLE IF NOT EXISTS course_info " +
                    "(course_name VARCHAR(50) NOT NULL, " + // course_name doesn't have to be unique as one course may have multiple sessions
                    "course_id INT PRIMARY KEY AUTO_INCREMENT, " + // there may be more than one course_id per course as each course_id is one session
                    "course_instructor VARCHAR(50) NOT NULL)";
            stmt.executeUpdate(createCourseInfoTB);

            // Create a Table: Student Info
            String createStudentInfoTB = "CREATE TABLE IF NOT EXISTS student_info " +
                    "(first_name VARCHAR(45) NOT NULL, " +
                    "last_name VARCHAR(45) NOT NULL, " +
                    "student_id INT PRIMARY KEY AUTO_INCREMENT)"; // student_id PK
            stmt.executeUpdate(createStudentInfoTB);

            // Create a Table: Course Schedule
            String createCourseSchedule = "CREATE TABLE IF NOT EXISTS course_schedule " +
                    "(course_id INT NOT NULL, " +
                    "session_id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "day VARCHAR(50) NOT NULL, " +
                    "start_time TIME NOT NULL, " +
                    "end_time TIME NOT NULL)";
            stmt.executeUpdate(createCourseSchedule);

            // Create a Table: Course to Student (linking table)
            String createCourseStudentTB = "CREATE TABLE IF NOT EXISTS course_student " +
                    "(student_id INT NOT NULL, " +
                    "course_id INT NOT NULL, " +
                    "CONSTRAINT course_student_fk_course FOREIGN KEY (course_id) REFERENCES course_info(course_id), " +
                    "CONSTRAINT course_student_fk_student FOREIGN KEY (student_id) REFERENCES student_info(student_id))";
            stmt.executeUpdate(createCourseStudentTB);

            System.out.println("Execution complete.");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex); // Handling errors for Class.forName
        } catch (SQLException ex) {
            Logger.getLogger(StudentDB.class.getName()).log(Level.SEVERE, null, ex); // Handling errors for JDBC
            ex.printStackTrace();
        }
    }

    private void printOptions() {
        System.out.println("+---------------------------------------------------+");
        System.out.println("| Select from options below:                        |");
        System.out.println("| 1. Add a new course                               |");
        System.out.println("| 2. Add a new student                              |");
        System.out.println("| 3. Enroll student in a course                     |");
        System.out.println("| 4. Query student list for a given course          |");
        System.out.println("| 5. Query course list for a given student          |");
        System.out.println("| 6. Query student schedule on a given day          |");
        System.out.println("| 0. Exit program                                   |");
        System.out.println("+---------------------------------------------------+");
    }

    private void newCourse() {
        Statement stmt;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter course name:");
            String course_name = scan.nextLine().toLowerCase();
            System.out.println("Enter course number:");
            String course_id = scan.nextLine();
            System.out.println("Enter course instructor:");
            String course_instructor = scan.nextLine().toLowerCase();
//            System.out.println("This course is taught on Mon/Tue/Wed/Thu/Fri:");
//            String day = scan.nextLine().toLowerCase();
//            System.out.println("This course starts at (ex. 13:00):");
//            String start_time = scan.nextLine();
//            System.out.println("This course ends at (ex. 13:00):");
//            String end_time = scan.nextLine();
            // Select Database and Insert Values
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            String addCourse = "INSERT INTO course_info " +
                    "VALUES ('" + course_name + "', " + course_id + ", '" + course_instructor + "')";
            // Execute Update
            stmt.executeUpdate(addCourse);
            // Display Course Info
            System.out.println("Course Info: " + course_name + " | " + course_id + " | " + course_instructor);
            System.out.println("COURSE ADDED TO CATALOG");
        } catch (Exception e) {
            System.out.println("Error: This course already exists in the catalog");
            // e.printStackTrace();
        }
    }

    private void courseSchedule() {
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter course number:");
            String course_id = scan.nextLine();
            System.out.println("This course is taught on Mon/Tue/Wed/Thu/Fri:");
            String day = scan.nextLine().toLowerCase();
            System.out.println("This course starts at (ex. 13:00):");
            String start_time = scan.nextLine();
            System.out.println("This course ends at (ex. 13:00):");
            String end_time = scan.nextLine();
            // Select Database and Insert Values
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            String addCourseSchedule = "INSERT INTO course_schedule " +
                    "VALUES (" + course_id + ", DEFAULT, '" + day + "', '" + start_time + "', '" + end_time + "')";
            // Execute Update
            stmt.executeUpdate(addCourseSchedule);
            // Query Session ID for Display
            String querySessionID = "SELECT session_id FROM course_schedule WHERE course_id = '" + course_id + "'";
            rs = stmt.executeQuery(querySessionID);
            int session_id = 0;
            if (rs.next()) {
                session_id = rs.getInt("session_id");
            }
            System.out.println("New Session: " + course_id + " | " + session_id + " | " + day + " | " + start_time + " | " + end_time + " ");
            System.out.println("SESSION ADDED FOR COURSE");
            rs.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void newStudent() {
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter your first name:");
            String first_name = scan.nextLine().toLowerCase();
            System.out.println("Enter your last name:");
            String last_name = scan.nextLine().toLowerCase();
            // Select Database and Insert Values
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            String addStudent = "INSERT INTO student_info " +
                    "VALUES ('" + first_name + "', '" + last_name + "', " + "DEFAULT)";
            // Execute Update
            stmt.executeUpdate(addStudent);
            // Query Student ID for Display
            String queryStudentID = "SELECT student_id FROM student_info WHERE first_name = '" + first_name + "'";
            rs = stmt.executeQuery(queryStudentID);
            int student_id = 0;
            if (rs.next()) {
                student_id = rs.getInt("student_id");
            }
            System.out.println("Student Name: " + first_name + " " + last_name + " | ID: " + student_id);
            System.out.println("STUDENT ADDED TO PROGRAM");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enrollStudentInCourse() {
        Statement stmt;
//        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter student ID:");
            String student_id = scan.nextLine();
            System.out.println("Enter course ID:");
            String course_id = scan.nextLine();
            // Select Database and Insert Values
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            String enrollStudent = "INSERT INTO course_student " +
                    "VALUES (" + student_id + ", " + course_id + ")";
            // Execute Update
            stmt.executeUpdate(enrollStudent);
            System.out.println("STUDENT ENROLLED TO COURSE");
//            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    private void queryStudentList() {
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter course ID:");
            String course_id = scan.nextLine();
            // Select Database
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            // Query Student List in Course
            String queryStudentList =
                    "SELECT student_info.first_name, student_info.last_name " +
                            "FROM student_info " +
                            "JOIN course_student ON student_info.student_id = course_student.student_id " +
                            "WHERE course_id = " + course_id;
            rs = stmt.executeQuery(queryStudentList);
            while (rs.next()) {
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                System.out.println(first_name + " " + last_name);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    private void queryCourseLoad() {
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter student ID:");
            String student_id = scan.nextLine();
            // Select Database
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            // Query Student Course Load
            String queryCourseLoad =
                    "SELECT course_info.course_name " +
                            "FROM course_info " +
                            "JOIN course_student ON course_info.course_id = course_student.course_id " +
                            "WHERE course_student.student_id = " + student_id;
            rs = stmt.executeQuery(queryCourseLoad);
            System.out.println("Student is enrolled in: ");
            while (rs.next()) {
                String course_name = rs.getString("course_name");
                System.out.println(course_name);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    private void querySchedule() {
        Statement stmt;
        ResultSet rs;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
            System.out.println("Enter student ID:");
            String student_id = scan.nextLine();
            System.out.println("Enter day (Mon/Tue/Wed/Thu/Fri):");
            String day = scan.nextLine();
            // Select Database
            String useDB = "USE Student_Database;";
            stmt.executeUpdate(useDB);
            // Query Student Schedule
            String querySchedule =
                    "SELECT course_schedule.course_id, course_schedule.day, course_schedule.start_time, course_schedule.end_time " +
                            "FROM course_schedule " +
                            "JOIN course_student ON course_schedule.course_id = course_student.course_id " +
                            "WHERE course_student.student_id =" + student_id +
                            " AND course_schedule.day = '" + day + "'";
            rs = stmt.executeQuery(querySchedule);
            System.out.println("Student schedule for " + day + ":");
            while (rs.next()) {
                String course_id = rs.getString("course_id");
                String start_time = rs.getString("start_time");
                String end_time = rs.getString("end_time");
                System.out.println("Course Info: " + course_id + " [from " + start_time + " to " + end_time + "]");
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }
    }

    private void exitProgram() {
        Statement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1?serverTimezone=GMT", "root", "Tnqls)530");
            stmt = connect.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        System.out.println("EXIT COMPLETE");
    }
}



