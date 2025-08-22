# 🛒 Console-Based E-Commerce Platform (Programação orientada a objetos / Object-oriented programming)

## **Grade: 16/20** 🎯

This project was developed as a 2nd-year university assignment, a fully functional, object-oriented e-commerce simulation built in Java. This console application demonstrates core programming principles by modeling a real-world online store with separate functionalities for customers, administrators, and logistics partners.

---

## ✨ Features

### 👥 Multi-Role User System
- **Role-Based Access:** Secure login/logout system differentiating between `Customer`, `Admin`, and `PacketTransporter` users.
- **Account Management:** Users can register new accounts (with specific roles) and delete existing ones.

### 🛍️ Customer Experience
- **Browse Products:** View all available items listed by admins, including their associated shipping method.
- **Purchase Items:** Add products to a cart and complete purchases. The chosen shipping method is attached to the order.
- **Stock Validation:** The system checks product availability before completing a transaction.

### ⚙️ Admin Dashboard
- **Full Product CRUD:** Create, Read, Update, and Delete products from the store's catalog. **Must assign a `PacketTransporter` and shipping method to each product.**
- **Inventory Management:** Set and modify product stock levels.

### 📦 Logistics & Shipping System
- **Transporter Accounts:** `PacketTransporter` users can log in to view a list of products/orders that need to be shipped using their service.
- **Product-Shipping Link:** Every product in the store is linked to a specific `PacketTransporter` and a shipping method (e.g., "Standard", "Express").
- **Order Fulfillment:** The system models the logistics chain, from purchase to dispatch.

### 💾 Data Persistence
- **State Serialization:** All application data (user accounts, product catalog, inventory, transporter links) is automatically saved to and loaded from files using Java Serialization, ensuring data persists between runs.

---

## 🏗️ Project Structure & OOP Design

This project was built with a strong emphasis on Object-Oriented Programming (OOP) principles. The architecture was planned using UML diagrams before any code was written.

---

## 🚀 How to Run

1.  **Prerequisites:** Ensure you have the Java JDK (version 8 or higher) installed on your machine.
2.  **Clone the Repository:**
    ```bash
    git clone https://github.com/desantesway/POO.git
    cd POO
    ```
3.  **Compile the Code:** Navigate to the `src` directory and compile all Java files.
    ```bash
    javac *.java
    ```
4.  **Run the Application:**
    ```bash
    java Main
    ```

---
