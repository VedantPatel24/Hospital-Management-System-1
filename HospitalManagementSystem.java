
import java.sql.*;
import java.util.*;

class DatabaseConnection {

    static String URL = "jdbc:mysql://localhost:3306/hospital_management";
    static String USER = "root";
    static String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class MyLinkedList<T> {

    Node<T> head;
    int size;

    public class Node<T> {

        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public int size() {
        return size;
    }

    public void printList() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data.toString());
            current = current.next;
        }
    }

}

class Doctor {

    int id;
    String name;
    String specialty;
    String phone;
    String city;
    String status;

    public Doctor(int id, String name, String specialty, String phone, String city, String status) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
        this.city = city;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "Doctor [id=" + id + ", name=" + name + ", specialty=" + specialty + ", phone=" + phone + ", city=" + city + ",status=" + status + "]";
    }
}

class Patient {

    int id;
    String name;
    int age;
    String gender;
    String disease;

    public Patient(int id, String name, int age, String gender, String disease) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getdisease() {
        return disease;
    }

    public void setdisease(String disease) {
        this.disease = disease;
    }

    public String toString() {
        return "Patient [id=" + id + ", name=" + name + ", age=" + age + ", gender=" + gender + ", disease=" + disease
                + "]";
    }
}

class Appointment {

    int id;
    int patientId;
    int doctorId;
    String appointmentDate;
    String appointmentTime;

    public Appointment(int id, int patientId, int doctorId, String appointmentDate, String appointmentTime) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String toString() {
        return "Appointment [id=" + id + ", patientId=" + patientId + ", doctorId=" + doctorId + ", appointmentDate="
                + appointmentDate + ", appointmentTime=" + appointmentTime + "]";
    }

}

class UserOperations {

    Scanner sc = new Scanner(System.in);

    public void registerUser() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            String query = "{CALL RegisterUser(?,?)}";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
            System.out.println("--------------------------------------");
            System.out.println("Registration successful! Please login.");
            System.out.println("--------------------------------------");
            System.out.println();
        }

    }

    public boolean loginUser() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            String query = "SELECT * FROM users WHERE user_username = ? AND user_password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public boolean loginAdmin() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            String query = "SELECT * FROM admin WHERE admin_username = ? AND admin_password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter admin username: ");
            String username = sc.nextLine();
            System.out.print("Enter admin password: ");
            String password = sc.nextLine();
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        return false;
    }
}

class DoctorOperations {

    Scanner sc = new Scanner(System.in);

    public void addDoctor() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {

            String query = "INSERT INTO doctors (doctor_name, doctor_specialty, doctor_phone, doctor_city) VALUES (?, ?, ?,?)";
            PreparedStatement ps = con.prepareStatement(query);

            System.out.print("Enter doctor's name: ");
            String doctorName = sc.nextLine();
            System.out.print("Enter doctor's specialty: ");
            String specialty = sc.nextLine();
            String phone = "";

            boolean valid = false;
            while (!valid) {
                System.out.print("Enter doctor's phone: ");
                phone = sc.nextLine();
                if (phone.matches("\\d{10}")) {
                    valid = true;
                } else {
                    System.out.println("Invalid phone number. Please enter a 10-digit number.");
                }
            }

            System.out.print("Enter doctor's city: ");
            String city = sc.nextLine();
            System.out.println("--------------------------");
            System.out.println("Doctor added successfully!");
            System.out.println("--------------------------");
            System.out.println();
            ps.setString(1, doctorName);
            ps.setString(2, specialty);
            ps.setString(3, phone);
            ps.setString(4, city);
            ps.executeUpdate();
        }
    }

    public void updateDoctor() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String sql1 = "SELECT doctor_id from doctors Where doctor_status = 'Free'";
                PreparedStatement pst = con.prepareStatement(sql1);
                ResultSet rs = pst.executeQuery();
                MyLinkedList<Integer> ll = new MyLinkedList<>();

                while (rs.next()) {
                    int id = rs.getInt("doctor_id");
                    ll.add(id);
                }
                getAllDoctors();
                System.out.println();
                System.out.print("Enter doctor's ID to update: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.println();
                boolean check = false;
                for (int i = 0; i < ll.size(); i++) {

                    if (id == ll.get(i)) {
                        check = true;
                    }
                }
                if (!check) {
                    System.out.println("-----------------------");
                    System.out.println("Doctor Can't be Updated");
                    System.out.println("-----------------------");
                    System.out.println();
                } else {
                    String query = "UPDATE doctors SET doctor_name = ?, doctor_specialty = ?, doctor_phone = ?, doctor_city = ? WHERE doctor_id = ?";
                    PreparedStatement ps = con.prepareStatement(query);

                    System.out.print("Enter new doctor's name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter new doctor's specialty: ");
                    String expertise = sc.nextLine();
                    System.out.print("Enter new doctor's phone: ");
                    String no = sc.nextLine();
                    System.out.print("Enter new doctor's city: ");
                    String city = sc.nextLine();

                    ps.setString(1, name);
                    ps.setString(2, expertise);
                    ps.setString(3, no);
                    ps.setString(4, city);
                    ps.setInt(5, id);
                    ps.executeUpdate();
                    System.out.println("----------------------------");
                    System.out.println("Doctor updated successfully!");
                    System.out.println("----------------------------");
                    System.out.println();
                }

            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid Input");
                System.out.println("-------------");
                System.out.println();
            }

        }
    }

    public void deleteDoctor() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String sql1 = "SELECT doctor_id from doctors";
                PreparedStatement pst = con.prepareStatement(sql1);
                ResultSet rs = pst.executeQuery();
                MyLinkedList<Integer> ll = new MyLinkedList<Integer>();

                while (rs.next()) {
                    int id = rs.getInt("doctor_id");
                    ll.add(id);
                }
                System.out.println("----------------------------------");
                System.out.println("Available Doctors in the Hospital");
                System.out.println("----------------------------------");
                getAllDoctors();
                System.out.println();
                System.out.print("Enter doctor's ID to delete: ");
                int doctorIdToDelete = sc.nextInt();
                boolean check = false;
                for (int i = 0; i < ll.size(); i++) {
                    if (doctorIdToDelete == ll.get(i)) {
                        check = true;
                        break;
                    }
                }

                if (!check) {
                    System.out.println("--------------------");
                    System.out.println("No Such Doctor Found");
                    System.out.println("--------------------");
                    System.out.println();
                } else {
                    String query = "DELETE FROM doctors WHERE doctor_id = ? And status = 'Free'";

                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, doctorIdToDelete);
                    ps.executeUpdate();
                    System.out.println("----------------------------");
                    System.out.println("Doctor deleted successfully!");
                    System.out.println("----------------------------");
                    System.out.println();
                }

            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid Input");
                System.out.println("-------------");
            }
        }
    }

    public void getAllDoctors() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {

            MyLinkedList<Doctor> doctors = new MyLinkedList<>();
            String query = "SELECT * FROM doctors";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor doctor = new Doctor(rs.getInt("doctor_id"), rs.getString("doctor_name"), rs.getString("doctor_specialty"), rs.getString("doctor_phone"), rs.getString("doctor_city"), rs.getString("doctor_status"));
                doctors.add(doctor);
            }

            doctors.printList();
            System.out.println();
        }
    }
}

class PatientOperations {

    Scanner sc = new Scanner(System.in);

    public void addPatient() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String query = "INSERT INTO patients (patient_name, patient_age, patient_gender, patient_disease) VALUES (?, ?, ?,?)";
                PreparedStatement ps = con.prepareStatement(query);

                System.out.print("Enter patient's name: ");
                String patientName = sc.nextLine();
                System.out.print("Enter patient's age: ");
                int age = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter patient's gender: ");
                String gender = sc.nextLine();
                System.out.print("Enter Disease");
                String disease = sc.nextLine();
                System.out.println();
                ps.setString(1, patientName);
                ps.setInt(2, age);
                ps.setString(3, gender);
                ps.setString(4, disease);
                ps.executeUpdate();
                System.out.println("---------------------------");
                System.out.println("Patient added successfully!");
                System.out.println("---------------------------");
                System.out.println();
            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid Input");
                System.out.println("-------------");
            }
        }
    }

    public void updatePatient() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String sql1 = "SELECT patient_id from patients";
                PreparedStatement pst = con.prepareStatement(sql1);
                ResultSet rs = pst.executeQuery();
                LinkedList<Integer> ll = new LinkedList<>();

                while (rs.next()) {
                    int id = rs.getInt("patient_id");
                    ll.add(id);
                }
                getAllPatients();
                System.out.print("Enter patient's ID to update: ");
                int patientIdToUpdate = sc.nextInt();
                sc.nextLine();
                boolean check = false;
                for (int i = 0; i < ll.size(); i++) {

                    if (patientIdToUpdate == ll.get(i)) {
                        check = true;
                    }
                }
                if (!check) {
                    System.out.println("---------------------");
                    System.out.println("No Such Patient Found");
                    System.out.println("---------------------");
                    System.out.println();
                } else {
                    String query = "UPDATE patients SET patient_name = ?, patient_age = ?, patient_gender = ?, patient_disease = ? WHERE patient_id = ?";
                    PreparedStatement ps = con.prepareStatement(query);
                    System.out.print("Enter new patient's name: ");
                    String patientName = sc.nextLine();
                    System.out.print("Enter new patient's age: ");
                    int age = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new patient's gender: ");
                    String gender = sc.nextLine();
                    System.out.print("Enter new patient's disease: ");
                    String disease = sc.nextLine();

                    ps.setString(1, patientName);
                    ps.setInt(2, age);
                    ps.setString(3, gender);
                    ps.setString(4, disease);
                    ps.setInt(5, patientIdToUpdate);
                    ps.executeUpdate();
                    System.out.println("-----------------------------");
                    System.out.println("Patient updated successfully!");
                    System.out.println("-----------------------------");
                    System.out.println();
                }

            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid Input");
                System.out.println("-------------");
            }
        }
    }

    public void deletePatient() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String sql1 = "SELECT patient_id from patients";
                PreparedStatement pst = con.prepareStatement(sql1);
                ResultSet rs = pst.executeQuery();
                MyLinkedList<Integer> ll = new MyLinkedList<>();

                while (rs.next()) {
                    int id = rs.getInt("patient_id");
                    ll.add(id);
                }
                getAllPatients();
                System.out.print("Enter patient's ID to delete: ");
                int patientIdToDelete = sc.nextInt();
                for (int i = 0; i < ll.size(); i++) {

                    if (patientIdToDelete != ll.get(i)) {
                        System.out.println("---------------------");
                        System.out.println("No Such Patient Found");
                        System.out.println("---------------------");
                        System.out.println();
                    } else {
                        String query = "DELETE FROM patients WHERE patient_id = ?";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setInt(1, patientIdToDelete);
                        ps.executeUpdate();
                        System.out.println("-----------------------------");
                        System.out.println("Patient deleted successfully!");
                        System.out.println("-----------------------------");
                        System.out.println();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid Input");
                System.out.println("-------------");
            }
        }
    }

    public void getAllPatients() throws SQLException {
        MyLinkedList<Patient> patients = new MyLinkedList<>();
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            String query = "SELECT * FROM patients";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Patient patient = new Patient(rs.getInt("patient_id"), rs.getString("patient_name"), rs.getInt("patient_age"), rs.getString("patient_gender"), rs.getString("patient_disease"));
                patients.add(patient);
            }
            patients.printList();
            System.out.println();
        }
    }
}

class AppointmentOperations {

    Scanner sc = new Scanner(System.in);

    public void addAppointment() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            try {
                String sql1 = "SELECT patient_id from patients ";
                String sql2 = "SELECT doctor_id from doctors";
                PreparedStatement pstpatient = con.prepareStatement(sql1);
                PreparedStatement pstdoctor = con.prepareStatement(sql2);
                ResultSet rspatient = pstpatient.executeQuery();
                ResultSet rsdoctor = pstdoctor.executeQuery();
                MyLinkedList<Integer> llpatient = new MyLinkedList<>();
                MyLinkedList<Integer> lldoctor = new MyLinkedList<>();

                while (rspatient.next()) {
                    int id = rspatient.getInt("patient_id");
                    llpatient.add(id);
                }
                while (rsdoctor.next()) {
                    int id = rsdoctor.getInt("doctor_id");
                    lldoctor.add(id);
                }
                boolean flag = false;

                MyLinkedList<Patient> patients = new MyLinkedList<>();
                String query2 = "SELECT patients.patient_id,patients.patient_name,patients.patient_age,patients.patient_gender,patients.patient_disease from patients Left Join appointments ON patients.patient_id = appointments.patient_id Where appointments.patient_id IS Null";

                PreparedStatement ps2 = con.prepareStatement(query2);
                ResultSet rs2 = ps2.executeQuery();
                System.out.println("--------------------------------------------");
                System.out.println("Patients not allocated to specific doctors are");
                System.out.println("--------------------------------------------");
                while (rs2.next()) {
                    Patient patient = new Patient(
                            rs2.getInt("patient_id"),
                            rs2.getString("patient_name"),
                            rs2.getInt("patient_age"),
                            rs2.getString("patient_gender"),
                            rs2.getString("patient_disease")
                    );
                    patients.add(patient);
                }

                patients.printList();
                System.out.println();
                System.out.print("Enter patient's ID for appointment: ");
                int appointmentPatientId = sc.nextInt();
                System.out.println();
                MyLinkedList<Doctor> doctors = new MyLinkedList<>();
                String query1 = "SELECT * FROM doctors where doctor_status = 'free'";
                PreparedStatement ps1 = con.prepareStatement(query1);
                ResultSet rs1 = ps1.executeQuery();
                System.out.println("--------------------------------------------");
                System.out.println("Doctors not alloted to specific patients are");
                System.out.println("--------------------------------------------");
                while (rs1.next()) {
                    Doctor doctor = new Doctor(rs1.getInt("doctor_id"), rs1.getString("doctor_name"), rs1.getString("doctor_specialty"), rs1.getString("doctor_phone"), rs1.getString("doctor_city"), rs1.getString("doctor_status"));
                    doctors.add(doctor);
                }

                doctors.printList();
                System.out.println();

                System.out.print("Enter doctor's ID for appointment: ");
                int appointmentDoctorId = sc.nextInt();
                sc.nextLine();

                for (int i = 0; i < llpatient.size(); i++) {

                    if (appointmentPatientId == llpatient.get(i)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                for (int j = 0; j < lldoctor.size(); j++) {

                    if (appointmentDoctorId == lldoctor.get(j)) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
                if (flag == false) {
                    System.out.println("Doctor ID or Patient ID Not availabe in Database");
                } else {
                    String query = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time) VALUES (?, ?, ?, ?,)";
                    PreparedStatement ps = con.prepareStatement(query);

                    System.out.print("Enter appointment date (YYYY-MM-DD): ");
                    String appointmentDate = sc.nextLine();
                    System.out.print("Enter appointment time (HH:MM:SS): ");
                    String appointmentTime = sc.nextLine();

                    ps.setInt(1, appointmentPatientId);
                    ps.setInt(2, appointmentDoctorId);
                    ps.setDate(3, java.sql.Date.valueOf(appointmentDate));
                    ps.setTime(4, java.sql.Time.valueOf(appointmentTime));
                    ps.executeUpdate();

                    String sqlupdate = "UPDATE doctors set status = 'Occupied' WHERE doctor_id = " + appointmentDoctorId;
                    PreparedStatement pst2 = con.prepareStatement(sqlupdate);
                    pst2.executeUpdate();
                    System.out.println("-------------------------------");
                    System.out.println("Appointment added successfully!");
                    System.out.println("-------------------------------");
                    System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println("-------------");
                System.out.println("Invalid input");
                System.out.println("-------------");
            }
        }
    }

    public void deleteAppointment() throws SQLException {
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            String sql = "SELECT appointment_id FROM appointments WHERE appointment_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            System.out.print("Enter Appointment ID: ");
            int appointmentId = sc.nextInt();
            pst.setInt(1, appointmentId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String query = "DELETE FROM appointments WHERE appointment_id = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setInt(1, appointmentId);
                ps.executeUpdate();
                System.out.println("---------------------------------");
                System.out.println("Appointment deleted successfully!");
                System.out.println("---------------------------------");
                System.out.println();

            } else {
                System.out.println("----------------------------------------");
                System.out.println("Appointment ID Not available in Database");
                System.out.println("----------------------------------------");
            }
        }
    }

    public void getAllAppointments() throws SQLException {
        MyLinkedList<Appointment> appointments = new MyLinkedList<>();
        String query = "SELECT * FROM appointments";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) {
            System.out.println("Connection is Not Established");
        } else {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Appointment appointment = new Appointment(rs.getInt("appointment_id"), rs.getInt("patient_id"), rs.getInt("doctor_id"), rs.getString("appointment_date"), rs.getString("appointment_time"));
                appointments.add(appointment);
            }
            appointments.printList();
            System.out.println();
        }
    }
}

public class HospitalManagementSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DoctorOperations doctor_obj = new DoctorOperations();
        PatientOperations patient_obj = new PatientOperations();
        AppointmentOperations appointment_obj = new AppointmentOperations();
        UserOperations user_obj = new UserOperations();
        System.out.println("-----------------------------------------");
        System.out.println("Welcome to the Hospital Management System");
        System.out.println("-------------------------------------------");
        boolean b = true;
        do {
            try {
                System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _ _");
                System.out.println("| 1. Admin Login          |");
                System.out.println("| 2. User Registration    |");
                System.out.println("| 3. User Login           |");
                System.out.println("| 4. Exit                 |");
                System.out.println("|_ _ _ _ _ _ _ _ _ _ _ _ _|");
                System.out.print("Choose an option:");
                int choice = sc.nextInt();
                sc.nextLine();
                System.out.println();
                switch (choice) {
                    case 1:
                        if (user_obj.loginAdmin()) {
                            System.out.println("-----------------------------");
                            System.out.println("ADMIN LOGGED IN SUCCESSFULLY!");
                            System.out.println("-----------------------------");
                            while (true) {
                                System.out.println(" _ _ _ _ _ _ _ _ _ ");
                                System.out.println("|1. Add Doctor    |");
                                System.out.println("|2. Update Doctor |");
                                System.out.println("|3. Delete Doctor |");
                                System.out.println("|4. View Doctor   |");
                                System.out.println("|5. Logout        |");
                                System.out.println("|_ _ _ _ _ _ _ _ _|");
                                System.out.print("Choose an option: ");
                                int adminChoice = sc.nextInt();
                                sc.nextLine();
                                System.out.println();
                                switch (adminChoice) {
                                    case 1:
                                        try {
                                            doctor_obj.addDoctor();
                                        } catch (SQLException e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 2:
                                        try {
                                            doctor_obj.updateDoctor();
                                        } catch (SQLException e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 3:
                                        try {
                                            doctor_obj.deleteDoctor();
                                        } catch (SQLException e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                        break;
                                    case 4:
                                        try {
                                            doctor_obj.getAllDoctors();
                                        } catch (SQLException e) {
                                            System.out.println("Error: " + e.getMessage());
                                        }
                                    case 5:
                                        System.out.println("-----------------");
                                        System.out.println("ADMIN LOGGED OUT.");
                                        System.out.println("-----------------");
                                        break;
                                    default:
                                        System.out.println("Invalid choice. Try again.");
                                        break;
                                }

                                if (adminChoice == 4) {
                                    break;
                                }
                            }
                        } else {
                            System.out.println("------------------");
                            System.out.println("ADMIN LOGIN FAILED");
                            System.out.println("------------------");
                            System.out.println();
                        }
                        break;
                    case 2:
                        try {
                            user_obj.registerUser();
                        } catch (SQLException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 3:
                        if (user_obj.loginUser()) {
                            System.out.println("----------------------------");
                            System.out.println("USER LOGGED IN SUCCESSFULLY");
                            System.out.println("----------------------------");
                            int choice2 = 0;
                            int i = 0;
                            do {
                                if (i == 8) {
                                    break;
                                }
                                try {
                                    System.out.println(" _ _ _ _ _ _ _ _ _ _ _ _ _");
                                    System.out.println("|1. Add Patient           |");
                                    System.out.println("|2. View All Patients     |");
                                    System.out.println("|3. Update Patient        |");
                                    System.out.println("|4. Delete Patient        |");
                                    System.out.println("|5. Add Appointment       |");
                                    System.out.println("|6. Delete Appointment    |");
                                    System.out.println("|7. View All Appointments |");
                                    System.out.println("|8. Logout                |");
                                    System.out.println("|_ _ _ _ _ _ _ _ _ _ _ _ _|");
                                    System.out.print("Choose an option: ");

                                    choice2 = sc.nextInt();
                                    System.out.println();
                                    try {
                                        switch (choice2) {
                                            case 1:
                                                sc.nextLine();
                                                patient_obj.addPatient();

                                                break;
                                            case 2:

                                                patient_obj.getAllPatients();

                                                break;
                                            case 3:

                                                patient_obj.updatePatient();

                                                break;
                                            case 4:

                                                patient_obj.deletePatient();

                                                break;
                                            case 5:

                                                appointment_obj.addAppointment();

                                                break;
                                            case 6:

                                                appointment_obj.deleteAppointment();

                                                break;
                                            case 7:
                                                appointment_obj.getAllAppointments();
                                                break;
                                            case 8:
                                                System.out.println("---------------");
                                                System.out.println("USER LOGGED OUT");
                                                System.out.println("---------------");
                                                System.out.println();
                                                i = 8;
                                                break;
                                            default:
                                                System.out.println("Invalid choice. Please try again.");
                                                System.out.println();
                                        }
                                    } catch (SQLException e) {
                                        System.err.println("An error occurred: " + e.getMessage());
                                        System.out.println();
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a number.");
                                    sc.next();
                                    System.out.println();
                                }
                            } while (choice2 != 8);
                        } else {
                            System.out.println("-----------------");
                            System.out.println("USER LOGIN FAILED");
                            System.out.println("-----------------");
                            System.out.println();
                        }
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("An error occured: Wrong Input Inserted");
                sc.next();
                b = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage() + "An error occurred: SQL Error");
                b = true;
            }

        } while (b == true);
    }
}
