# 🎟️ JavaFX Event Management System

A complete desktop application built with **JavaFX**, **FXML**, and **MySQL**, offering a role-based event management system for users and admins. Users can register for events, submit feedback, and manage their account, while admins can manage events, users, and feedback through an admin dashboard.

---

## 🚀 Features

### 🧑‍🎓 Users
- Sign up and log in with full authentication
- Browse and register for available events
- View and cancel registered events
- Submit feedback with a star rating system
- Update profile information (name, email, password)
- Logout securely

### 🧑‍💼 Admins
- Create, update, and delete events (CRUD)
- View all registered users
- View feedback submitted by users
- View attendees for each event

---

## 🛠️ Technologies Used

- **JavaFX** – For the modern GUI  
- **FXML + SceneBuilder** – For UI layout design  
- **MySQL** – Backend database  
- **JDBC** – To connect to MySQL  
- **CSS** – For styling all scenes  
- **MVC Architecture** – For clean separation of logic and UI  

---SCREENSHOTS

![Image](https://github.com/user-attachments/assets/077adae1-6f08-4ea1-a56a-35ea20771bd9)

![Image](https://github.com/user-attachments/assets/4253eb78-949c-49b9-aed4-050bcdba8a2e)

![Image](https://github.com/user-attachments/assets/4caffa7d-bba1-4f76-bcab-61f9caf5423b)

![Image](https://github.com/user-attachments/assets/20a3d522-c07d-451a-9a42-d74a16d5e92a)

![Image](https://github.com/user-attachments/assets/3c2146c7-3b2a-48ff-89f6-9d0378c7a279)

![Image](https://github.com/user-attachments/assets/a8d6e7f6-af46-4f66-8471-1a7a4030f54a)

![Image](https://github.com/user-attachments/assets/620f4558-dcc1-4db7-a933-61c94a34c04b)

![Image](https://github.com/user-attachments/assets/33ad9f57-ae87-4612-bbc9-2039c4e390ca)

![Image](https://github.com/user-attachments/assets/220a49e7-c173-488d-878b-5dadf9877186)

## 📦 How to Run the App

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/javafx-event-management.git
cd javafx-event-management
```

2. Set Up the MySQL Database
Run the following SQL commands to create the database, tables, and insert dummy data:

CREATE DATABASE event_management_system;
USE event_management_system;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100) NOT NULL UNIQUE,
    role ENUM('admin', 'user') DEFAULT 'user'
);

CREATE TABLE events (
    event_id INT AUTO_INCREMENT PRIMARY KEY,
    event_name VARCHAR(100) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    event_date DATE,
    image_path VARCHAR(255),
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES users(user_id)
);

CREATE TABLE attendees (
    attendee_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    phone_number VARCHAR(20),
    email VARCHAR(100),
    gender ENUM('Male', 'Female') NOT NULL,
    institution VARCHAR(100),
    age INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE registrations (
    registration_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    event_id INT,
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(event_id) ON DELETE CASCADE,
    UNIQUE (user_id, event_id)
);

CREATE TABLE feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

INSERT INTO events (event_name, description, location, event_date, image_path, created_by) VALUES
('Music Festival', 'An exciting outdoor music event.', 'Addis Ababa Stadium', '2025-05-10', '/images/event1.png', 1),
('Book Fair', 'Annual gathering of publishers and authors.', 'National Library Hall', '2025-06-15', '/images/event2.png', 1),
('Semenar', 'Smart World: The Rise of Intelligent Systems.', 'Wisdom', '2025-08-05', '/images/event7.png', 1),
('Tech Conference', 'A technology conference with speakers from around the world.', 'Hilton Hotel', '2025-07-20', '/images/event3.png', 1),
('Creative Visions', 'A vibrant exhibition celebrating bold and experimental art from new voices.', 'Modern Art Gallery', '2025-08-05', '/images/event4.png', 1),
('Art Show', 'Showcasing emerging artists from the region.', 'Modern Art Gallery', '2025-08-05', '/images/event5.png', 1),
('Job Fair', 'Opportunities from top companies and networking events.', 'Expo Center', '2025-09-12', '/images/event6.png', 1);

3. Configure Database Connection
The connection class (DBUtils.java) is set up as follows:

public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/event_management_system";
        String user = "root";              // Change if needed
        String password = "password";      // Change to your MySQL password
        return DriverManager.getConnection(url, user, password);
    }

Important: Update user and password in DBUtils.java to match your MySQL credentials.

4. Running the Application
- Open your IDE (e.g., IntelliJ IDEA).

- Import the project.

- Add JavaFX SDK and MySQL Connector/J libraries to your project dependencies.

- Run your main application class (Application.java).

- Admin varification on signup is "ABC123"

Thanks for checking out my app :)
