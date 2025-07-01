# Personal Budget Tracker

A simple Spring Boot app to track personal expenses and income with a clean dark UI and PostgreSQL.

## Features
- Add, edit, and delete expenses and income
- Dashboard with summary cards and charts
- Category-wise spending breakdown
- Export transactions to CSV
- Dark, mobile-friendly UI

## Tech Stack
- Java 21, Spring Boot 3
- Thymeleaf, Tailwind CSS, Chart.js
- PostgreSQL

## Setup
1. Clone the repo and enter the project folder:
   ```bash
   git clone https://github.com/yourusername/personal-budget-tracker.git
   cd personal-budget-tracker/budgettracker
   ```
2. Edit `src/main/resources/application.properties` with your PostgreSQL credentials.
3. Run the app:
   ```bash
   mvn spring-boot:run
   ```

## Usage
Open [http://localhost:8080](http://localhost:8080) and start tracking your budget. 
