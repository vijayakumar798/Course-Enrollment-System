package Project;

import java.io.*;
import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CourseEnrollmentSystem {
    private static final String STUDENT_FILE = "Student.txt";
    private static final String COURSE_FILE = "Course.txt";
    private static final String ENROLLMENT_FILE = "Enrollment.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. View"
            		+ " (students, courses, enrolled courses)");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addMenu();
                    break;
                case 2:
                    removeMenu();
                    break;
                case 3:
                    viewMenu();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void addMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nAdd Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student in Course");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addCourse();
                    break;
                case 3:
                    enrollStudent();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return; // Return to main menu
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void addStudent() {
        try {
            Scanner scanner = new Scanner(System.in);
            BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_FILE, true));

            System.out.print("Enter student ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (isStudentExists(id)) {
                System.out.println("Error: Student ID already exists.");
                writer.close();
                return;
            }
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();

            writer.write(id + "," + name);
            writer.newLine();

            writer.close();
            System.out.println("Student added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void addCourse() {
        try {
            Scanner scanner = new Scanner(System.in);
            BufferedWriter writer = new BufferedWriter(new FileWriter(COURSE_FILE, true));

            System.out.print("Enter course ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (isCourseExists(id)) {
                System.out.println("Error: Course ID already exists.");
                writer.close();
                return;
            }
            System.out.print("Enter course name: ");
            String name = scanner.nextLine();

            writer.write(id + "," + name);
            writer.newLine();

            writer.close();
            System.out.println("Course added successfully.");
        } catch (IOException e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    private static void enrollStudent() {
        try {
            Scanner scanner = new Scanner(System.in);
            BufferedWriter writer = new BufferedWriter(new FileWriter(ENROLLMENT_FILE, true));

            System.out.print("Enter student ID: ");
            int studentId = scanner.nextInt();
            System.out.print("Enter course ID: ");
            int courseId = scanner.nextInt();

            if (!isStudentExists(studentId)) {
                System.out.println("Error: Student ID does not exist.");
                writer.close();
                return;
            }

            if (!isCourseExists(courseId)) {
                System.out.println("Error: Course ID does not exist.");
                writer.close();
                return;
            }

            if (isEnrollmentExists(studentId, courseId)) {
                System.out.println("Error: Student already enrolled in this course.");
                writer.close();
                return;
            }

            writer.write(studentId + "," + courseId);
            writer.newLine();

            writer.close();
            System.out.println("Student enrolled in course successfully.");
        } catch (IOException e) {
            System.out.println("Error enrolling student: " + e.getMessage());
        }
    }

    private static void removeMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nRemove Menu:");
            System.out.println("1. Remove Student");
            System.out.println("2. Remove Course");
            System.out.println("3. Remove Enrollment");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    removeStudent();
                    break;
                case 2:
                    removeCourse();
                    break;
                case 3:
                    removeEnrollment();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void removeStudent() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to remove: ");
            int idToRemove = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (!isStudentExists(idToRemove)) {
                System.out.println("Error: Student ID does not exist.");
                return;
            }
            
            System.out.print("Are you sure you want to remove this student? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();
            if (!confirmation.equals("yes")) {
                System.out.println("Student removal canceled.");
                return;
            }

            File inputFile = new File(STUDENT_FILE);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id != idToRemove) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Error: Unable to delete student data.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error: Unable to update student data.");
                return;
            }

            System.out.println("Student removed successfully.");
        } catch (IOException e) {
            System.out.println("Error removing student: " + e.getMessage());
        }
    }

    private static void removeCourse() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter course ID to remove: ");
            int idToRemove = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (!isCourseExists(idToRemove)) {
                System.out.println("Error: Course ID does not exist.");
                return;
            }
            
            System.out.print("Are you sure you want to remove this Course? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();
            if (!confirmation.equals("yes")) {
                System.out.println("Course removal canceled.");
                return;
            }

            File inputFile = new File(COURSE_FILE);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id != idToRemove) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Error: Unable to delete course data.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error: Unable to update course data.");
                return;
            }

            System.out.println("Course removed successfully.");
        } catch (IOException e) {
            System.out.println("Error removing course: " + e.getMessage());
        }
    }

    private static void removeEnrollment() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter student ID to remove enrollment: ");
            int studentIdToRemove = scanner.nextInt();
            System.out.print("Enter course ID to remove enrollment: ");
            int courseIdToRemove = scanner.nextInt();

            if (!isEnrollmentExists(studentIdToRemove, courseIdToRemove)) {
                System.out.println("Error: Enrollment does not exist.");
                return;
            }
            
            System.out.print("Are you sure you want to remove this Enrollment? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();
            if (!confirmation.equals("yes")) {
                System.out.println("Enrollment removal canceled.");
                return;
            }

            File inputFile = new File(ENROLLMENT_FILE);
            File tempFile = new File("temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                int idStudent = Integer.parseInt(parts[0]);
                int idCourse = Integer.parseInt(parts[1]);
                if (idStudent != studentIdToRemove || idCourse != courseIdToRemove) {
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Error: Unable to delete enrollment data.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Error: Unable to update enrollment data.");
                return;
            }

            System.out.println("Enrollment removed successfully.");
        } catch (IOException e) {
            System.out.println("Error removing enrollment: " + e.getMessage());
        }
    }

    private static void viewMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nView Menu:");
            System.out.println("1. View Students");
            System.out.println("2. View Courses");
            System.out.println("3. View Enrolled Courses");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewStudents();
                    break;
                case 2:
                    viewCourses();
                    break;
                case 3:
                    viewEnrolledCourses();
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void viewStudents() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE));
            String line;

            System.out.println("\nList of Students:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1]);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading student data: " + e.getMessage());
        }
    }

    private static void viewCourses() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE));
            String line;

            System.out.println("\nList of Courses:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("ID: " + parts[0] + ", Name: " + parts[1]);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading course data: " + e.getMessage());
        }
    }

    private static void viewEnrolledCourses() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENT_FILE));
            String line;

            System.out.println("\nEnrolled Courses:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int studentId = Integer.parseInt(parts[0]);
                int courseId = Integer.parseInt(parts[1]);
                String studentName = findStudentName(studentId);
                String courseName = findCourseName(courseId);
                System.out.println("Student: " + studentName + ", Course: " + courseName);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading enrollment data: " + e.getMessage());
        }
    }

    private static boolean isStudentExists(int studentId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            if (id == studentId) {
                reader.close();
                return true;
            }
        }

        reader.close();
        return false;
    }

    private static boolean isCourseExists(int courseId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            if (id == courseId) {
                reader.close();
                return true;
            }
        }

        reader.close();
        return false;
    }

    private static boolean isEnrollmentExists(int studentId, int courseId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENT_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int idStudent = Integer.parseInt(parts[0]);
            int idCourse = Integer.parseInt(parts[1]);
            if (idStudent == studentId && idCourse == courseId) {
                reader.close();
                return true;
            }
        }

        reader.close();
        return false;
    }

    private static String findStudentName(int studentId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(STUDENT_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            if (id == studentId) {
                reader.close();
                return parts[1];
            }
        }

        reader.close();
        return "Unknown Student";
    }

    private static String findCourseName(int courseId) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(COURSE_FILE));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            if (id == courseId) {
                reader.close();
                return parts[1];
            }
        }

        reader.close();
        return "Unknown Course";
    }
}







