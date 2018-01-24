package db;

import dto.Deadline;
import dto.LoginInfo;
import dto.StudentInfo;
import dto.TeacherInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

    private static Connection con;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lyceum", "root", "");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static LoginInfo login(String login, String password) {
        try {
            String query = "SELECT id, role_id FROM lyceum.user WHERE login = ? AND password = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int id = rs.getInt("id");
            int roleId = rs.getInt("role_id");

            switch (roleId) {
                case 0:
                    return getTeacherInfo(id);
                case 1:
                    return getStudentInfo(id);
                default:
                    return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static TeacherInfo getTeacherInfo(int id) {
        try {
            String query = "SELECT user_id, first_name, last_name, email, phone_number, address FROM lyceum.teacher WHERE user_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int userId = rs.getInt("user_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");

            TeacherInfo info = new TeacherInfo(userId, firstName, lastName, email, phoneNumber, address);
            info.setRoleId(0);
            return info;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static StudentInfo getStudentInfo(int id) {
        try {
            String query = "SELECT user_id, first_name, last_name, email, phone_number, address, gender FROM lyceum.student WHERE user_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            int userId = rs.getInt("user_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            int gender = rs.getInt("gender");

            StudentInfo info = new StudentInfo(userId, firstName, lastName, email, phoneNumber, address, gender == 0 ? StudentInfo.GENDER.MALE : StudentInfo.GENDER.FEMALE);
            info.setRoleId(1);

            return info;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<StudentInfo> getStudentList() {
        try {
            String query = "SELECT user_id, first_name, last_name, email, phone_number, address, gender FROM lyceum.student";
            PreparedStatement statement = con.prepareStatement(query);

            ResultSet rs = statement.executeQuery();

            List<StudentInfo> studentList = new ArrayList<>();
            populateStudentList(rs, studentList);

            return studentList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Deadline> getDeadlines(int studentId) {
        try {
            String query = "SELECT due_date, homework, internal, midterm, spec_obj, subject FROM lyceum.deadline WHERE student_id = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, studentId);


            ResultSet rs = statement.executeQuery();

            List<Deadline> deadlines = new ArrayList<>();

            while (rs.next()) {
                String dueDate = rs.getString("due_date");
                String homework = rs.getString("homework");
                String internal = rs.getString("internal");
                String midterm = rs.getString("midterm");
                String specObj = rs.getString("spec_obj");
                String subject = rs.getString("subject");

                deadlines.add(new Deadline(studentId, dueDate, homework, internal, midterm, specObj, subject));
            }

            return deadlines;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean addDeadline(int studentId, Deadline deadline) {
        try {
            String query = "INSERT INTO deadline ( student_id, due_date, homework, internal, midterm, spec_obj, subject) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, studentId);
            statement.setString(2, deadline.getDueDate());
            statement.setString(3, deadline.getHomework());
            statement.setString(4, deadline.getInternal());
            statement.setString(5, deadline.getMidterm());
            statement.setString(6, deadline.getSpecObj());
            statement.setString(7, deadline.getSubject());

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean assignStudentToTeacher(int teacherId, int studentId) {
        try {
            String query = "INSERT INTO teacher_student (teacher_id, student_id) VALUES (?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            statement.setInt(1, teacherId);
            statement.setInt(2, studentId);

            return statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<StudentInfo> getAssignedStudents(int teacherId) {
        try {
            String query = "SELECT * FROM student WHERE user_id IN (SELECT student_id FROM teacher_student WHERE teacher_id = ?)";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, teacherId);

            ResultSet rs = statement.executeQuery();

            List<StudentInfo> studentList = new ArrayList<>();
            populateStudentList(rs, studentList);

            return studentList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void populateStudentList(ResultSet rs, List<StudentInfo> studentList) throws SQLException {
        while (rs.next()) {
            int userId = rs.getInt("user_id");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("email");
            String phoneNumber = rs.getString("phone_number");
            String address = rs.getString("address");
            int gender = rs.getInt("gender");

            studentList.add(new StudentInfo(userId, firstName, lastName, email, phoneNumber, address, gender == 0 ? StudentInfo.GENDER.MALE : StudentInfo.GENDER.FEMALE));
        }
    }
}
