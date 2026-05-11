# Hospital Patient Management System

A clean, console-based Java application designed to manage core hospital records. This project demonstrates strong foundational knowledge of Object-Oriented Programming (OOP) concepts by efficiently handling doctor and patient profiles.

## 🚀 Key Features

* **Doctor Profiles:** Manages and displays doctor information, including unique IDs, specialized departments (e.g., Cardiology, Orthopedics), and contact details.
* **Patient Records:** Keeps track of patient admissions, storing crucial medical data like Blood Group and current Disease.
* **Doctor-Patient Association:** Seamlessly assigns a specific doctor to each patient record.
* **Unified Database:** Uses a consolidated record system to output comprehensive hospital data at a glance.

## 🧠 OOP Concepts Implemented

This project is an excellent showcase of the four pillars of OOP:

* **Abstraction:** The `Person` class is defined as `abstract`, establishing a common blueprint with an abstract `displayDetails()` method that child classes must implement.
* **Inheritance:** Both `Doctor` and `Patient` classes extend the abstract `Person` class, effectively inheriting common attributes like Name, Age, and Contact Number while adding their own specific properties.
* **Encapsulation:** Class variables are kept `private` to restrict direct access, using public getter and setter methods to interact with the data safely (including age validation logic).
* **Polymorphism:** The main execution utilizes a `Person[]` array to store `Patient` objects. When iterating through the array, the overridden `displayDetails()` method resolves dynamically at runtime to show patient-specific formatting.

## 👥 Task Breakdown & Contributions

* **Task 1: Core Class Hierarchy & OOP Implementation**
   * **Completed by:** Nur-amin
   * **Description:** Design and implement the core class hierarchy of the chosen management system, clearly demonstrating all four Object-Oriented Programming (OOP) principles, Encapsulation, Abstraction, Inheritance, and Polymorphism through the structure and behaviour of the classes.

## 📁 Project Structure

* `Main.java` - The entry point containing sample data and the hospital database array logic.
* `Person.java` - Abstract parent class defining the core human attributes.
* `Doctor.java` - Child class representing a hospital doctor.
* `Patient.java` - Child class representing a patient.

## 💻 How to Run Locally

1. Ensure you have **Java** installed on your system.
2. Clone this repository to your local machine:
   ```bash
   git clone [https://github.com/nuraminislam/Hospital-Patient-Management.git](https://github.com/nuraminislam/Hospital-Patient-Management.git)