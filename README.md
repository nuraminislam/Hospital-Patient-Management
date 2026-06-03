# Hospital Patient Management System

A robust, desktop-based graphical Java application designed to manage core hospital records. This project demonstrates strong foundational knowledge of Object-Oriented Programming (OOP) concepts, File I/O data persistence, and Java Swing GUI by efficiently handling doctor assignments, patient profiles, and automated billing operations.

## 🚀 Key Features

* **Modern GUI Dashboard:** A clean, user-friendly interface built with Java Swing, featuring dynamic panel swapping for seamless navigation without opening multiple windows.
* **Doctor & Patient Records:** Manages detailed profiles including unique IDs, specialized departments, medical history, and contact details.
* **Dynamic Billing System:** Automatically generates detailed cash memos and invoices by fetching patient data via their unique ID.
* **Persistent Storage:** Utilizes File I/O (e.g., `patientInfo.txt`, `doctorInfo.txt`) to securely save and load hospital records across sessions.
* **Robust Error Handling:** Integrated pop-up alerts (`JOptionPane`) and custom exceptions to prevent program crashes during invalid inputs.

## 🧠 OOP Concepts Implemented

This project is an excellent showcase of the four pillars of OOP:

* **Abstraction:** The `Person` class is defined as `abstract`, establishing a common blueprint with an abstract `displayDetails()` method that child classes must implement.
* **Inheritance:** Both `Doctor` and `Patient` classes extend the abstract `Person` class, effectively inheriting common attributes like Name, Age, and Contact Number while adding their own specific properties.
* **Encapsulation:** Class variables are kept `private` to restrict direct access, using public getter and setter methods to interact with the data safely (including age validation logic).
* **Polymorphism:** Method overriding resolves dynamically at runtime. For example, the `displayDetails()` method is overridden in child classes to show entity-specific formatting.

## 👥 Task Breakdown & Contributions

* **Task 1: Core Class Hierarchy & OOP Implementation**
    * **Completed by:** Marium Pramanik, Nur-amin, mahim-x446
    * **Description:** Designed and implemented the core class hierarchy of the management system, clearly demonstrating all four Object-Oriented Programming (OOP) principles through the structure and behavior of the classes.

* **Task 2: Backend Service Layer & Exception Handling**
    * **Completed by:** MD Nur- Amin Islam, Marium Pramanik, mahim-x446
    * **Description:** Applied object-oriented principles and exception handling to develop a fully functional backend service layer that manages data storage, validates inputs, and enforces business rules through custom exceptions.

* **Task 3: GUI Integration & Dynamic Navigation**
    * **Completed by:** Nur-amin, mahim-x446
    * **Description:** Designed and integrated a complete Java Swing GUI with dynamic panel swapping (Single-Page Application approach), a modern interactive dashboard, and form validation with exception-handling pop-ups.

* **Additional Module: Billing & Invoice Generation System**
    * **Completed by:** Marium Pramanik
    * **Description:** Developed a dynamic billing module that retrieves patient records by ID and generates a detailed, formatted text-based invoice including consultation fees, room charges, and hospital taxes.

## 📁 Project Structure

* `Main.java` - The entry point initializing the application and launching the Login module.
* `Dashboard.java` - The core GUI controller handling sidebar navigation and UI rendering.
* `BillingPage.java` - The graphical module responsible for searching patients and rendering the billing invoice.
* `HospitalService.java` - Backend service layer managing object arrays and file reading/writing operations.
* `Person.java` / `Doctor.java` / `Patient.java` - Core OOP entity classes representing hospital individuals.
* `InvalidAgeException.java` / `PatientNotFoundException.java` - Custom exceptions for structured error handling.
* `*.txt` Files - Text-based database files storing persistent system data.

## 💻 How to Run Locally

1. Ensure you have **Java (JDK 8 or higher)** installed on your system.
2. Clone this repository to your local machine:
```bash
   git clone [https://github.com/nuraminislam/Hospital-Patient-Management.git](https://github.com/nuraminislam/Hospital-Patient-Management.git)