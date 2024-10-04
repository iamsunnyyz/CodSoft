import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Course class to store course information
class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    // Getter methods
    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledStudents() {
        return enrolledStudents;
    }

    public boolean isAvailable() {
        return enrolledStudents < capacity;
    }

    public void enrollStudent() {
        if (isAvailable()) {
            enrolledStudents++;
        }
    }

    public void removeStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
        }
    }

    // Display course details
    public void displayCourseDetails() {
        System.out.println("Course Code: " + code);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Capacity: " + capacity);
        System.out.println("Schedule: " + schedule);
        System.out.println("Available Slots: " + (capacity - enrolledStudents));
        System.out.println("----------------------------------");
    }
}

// Student class to store student information
class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        if (!registeredCourses.contains(course) && course.isAvailable()) {
            registeredCourses.add(course);
            course.enrollStudent();
            System.out.println("Successfully registered for course: " + course.getTitle());
        } else {
            System.out.println("Cannot register for course: " + course.getTitle() + " (Capacity Full or Already Registered)");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.removeStudent();
            System.out.println("Successfully dropped course: " + course.getTitle());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("Registered Courses for " + name + ":");
        for (Course course : registeredCourses) {
            System.out.println(course.getTitle() + " (" + course.getCode() + ")");
        }
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
        }
    }
}

// Main class for Course Registration System
class CourseRegistrationSystem {
    private static HashMap<String, Course> courseDatabase = new HashMap<>();
    private static HashMap<String, Student> studentDatabase = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Predefined courses for testing
        courseDatabase.put("CS101", new Course("CS101", "Introduction to Computer Science", "Basics of computing", 2, "Mon-Wed 10am-11am"));
        courseDatabase.put("MA101", new Course("MA101", "Calculus I", "Differential calculus", 2, "Tue-Thu 2pm-3pm"));
        courseDatabase.put("PH101", new Course("PH101", "Physics I", "Mechanics and waves", 2, "Fri 11am-1pm"));

        // Main menu
        while (true) {
            System.out.println("\n=== Student Course Registration System ===");
            System.out.println("1. Register a New Student");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Available Courses");
            System.out.println("5. View Registered Courses");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear newline

            switch (choice) {
                case 1:
                    registerStudent();
                    break;
                case 2:
                    registerForCourse();
                    break;
                case 3:
                    dropCourse();
                    break;
                case 4:
                    viewAvailableCourses();
                    break;
                case 5:
                    viewRegisteredCourses();
                    break;
                case 6:
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Register a new student
    private static void registerStudent() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();
        studentDatabase.put(studentID, new Student(studentID, studentName));
        System.out.println("Student registered successfully.");
    }

    // Register a student for a course
    private static void registerForCourse() {
        Student student = findStudent();
        if (student != null) {
            Course course = findCourse();
            if (course != null) {
                student.registerCourse(course);
            }
        }
    }

    // Drop a registered course
    private static void dropCourse() {
        Student student = findStudent();
        if (student != null) {
            Course course = findCourse();
            if (course != null) {
                student.dropCourse(course);
            }
        }
    }

    // View all available courses
    private static void viewAvailableCourses() {
        System.out.println("\n=== Available Courses ===");
        for (Course course : courseDatabase.values()) {
            course.displayCourseDetails();
        }
    }

    // View a student's registered courses
    private static void viewRegisteredCourses() {
        Student student = findStudent();
        if (student != null) {
            student.displayRegisteredCourses();
        }
    }

    // Helper method to find a student by ID
    private static Student findStudent() {
        System.out.print("Enter Student ID: ");
        String studentID = scanner.nextLine();
        Student student = studentDatabase.get(studentID);
        if (student == null) {
            System.out.println("Student not found.");
        }
        return student;
    }

    // Helper method to find a course by code
    private static Course findCourse() {
        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();
        Course course = courseDatabase.get(courseCode);
        if (course == null) {
            System.out.println("Course not found.");
        }
        return course;
    }
}
