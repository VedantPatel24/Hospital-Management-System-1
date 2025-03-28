-- Create Database Hospital_Mangement
CREATE DATABASE hospital_management


-- Inside Database Run the below code is SQL
-- Create a table for users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_username VARCHAR(50) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL
);

-- Create a table for admin
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    admin_username VARCHAR(50) NOT NULL UNIQUE,
    admin_password VARCHAR(255) NOT NULL
);

-- Create a table for doctors
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    doctor_name VARCHAR(100) NOT NULL,
    doctor_specialty VARCHAR(100) NOT NULL,
    doctor_phone VARCHAR(15) NOT NULL,
    doctor_city VARCHAR(100) NOT NULL,
    doctor_status ENUM('Free', 'Occupied') DEFAULT 'Free'
);

-- Create a table for patients
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_name VARCHAR(100) NOT NULL,
    patient_age INT NOT NULL,
    patient_gender ENUM('Male', 'Female', 'Other') NOT NULL,
    patient_disease VARCHAR(255) NOT NULL
);

-- Create a table for appointments
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
);

-- Procedure

DELIMITER //

CREATE PROCEDURE RegisterUser  (IN username VARCHAR(50), IN password VARCHAR(255))
BEGIN
    DECLARE userExists INT;

    -- Check if the username already exists
    SELECT COUNT(*) INTO userExists FROM users WHERE user_username = username;

    IF userExists > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Username already exists';
    ELSE
        -- Insert the new user into the users table
        INSERT INTO users (user_username, user_password) VALUES (username, password);
    END IF;
END //

DELIMITER ;