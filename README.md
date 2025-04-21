# 🧊 FridgePal Backend

This is the backend service for **FridgePal**, a web application that helps users track items in their fridge using images. Built with **Spring Boot**, this RESTful API handles image uploads, fridge item management, expiration tracking, and recipe suggestion endpoints.

---

## 🛠 Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot (v3+)
- **Database:** PostgreSQL
- **Security:** Spring Security + JWT
- **Image Storage:** Cloudinary
- **API Docs:** Swagger / SpringDoc OpenAPI

---

## 🚀 Features

- 📦 **CRUD for Fridge Items**
- 🗓️ **Expiration Date Tracking**
- 📤 **Image Upload & Storage**
- 🔐 **User Authentication with JWT**

---

## 🚀 Running Locally

1. Navigate to the `local-dev-env` folder in the project directory.
2. Copy the `.env.example` file and rename it to `.env`. Make sure to fill in any required environment variables (e.g., database URL, Cloudinary credentials, JWT secret).
3. Run the following command to bring up the necessary services using Docker Compose:

   ```bash
   docker-compose up
   ```

   This will start the database and any other services required to run the backend locally.

---

## 📚 Swagger Documentation

You can access the Swagger API documentation for **FridgePal** at the following URL:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)

This will provide you with an interactive interface to explore and test all available endpoints.
