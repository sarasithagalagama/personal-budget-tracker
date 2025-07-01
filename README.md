# Personal Budget Tracker

A modern, mobile-friendly Spring Boot application for tracking personal finances with a beautiful gradient UI and PostgreSQL persistence.

## Features
- Add, view, edit, and delete expenses and income
- Edit and delete via modern modal popups for both transactions and income
- Dashboard with summary cards, analytics, and charts
- Category-wise spending breakdown with colorful gradients
- Income table with full CRUD support
- Responsive, dark-themed, gradient-based UI (Tailwind CSS)
- Export transactions to CSV
- Persistent storage with PostgreSQL

## Tech Stack
- **Backend:** Java 21, Spring Boot 3
- **Frontend:** Thymeleaf, Tailwind CSS, Chart.js
- **Database:** PostgreSQL

## Screenshots
> _Add your dashboard screenshot here!_

## Getting Started

### Prerequisites
- Java 21+
- Maven
- PostgreSQL (running and accessible)

### Setup
1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/personal-budget-tracker.git
   cd personal-budget-tracker/budgettracker
   ```
2. **Configure PostgreSQL:**
   - Edit `src/main/resources/application.properties` with your DB credentials.
3. **Build and run:**
   ```bash
   mvn spring-boot:run
   ```
4. **Access the app:**
   - Open [http://localhost:8080](http://localhost:8080) in your browser.

## Usage
- Add transactions and income via the dashboard forms.
- View analytics, category breakdowns, and net cashflow.
- Export your data as CSV for backup or analysis.

## Credits
- UI powered by [Tailwind CSS](https://tailwindcss.com/) and [Chart.js](https://www.chartjs.org/)
- Built with [Spring Boot](https://spring.io/projects/spring-boot)

---

_Enjoy tracking your budget with style!_ 