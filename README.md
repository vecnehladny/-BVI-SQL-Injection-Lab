# Running Project on Linux and Windows

## Requirements

- Java JDK 1.8 or later
- Maven 3.6.0 or later
- Git (optional, for cloning the repository)
- PostgreSQL database server

## Setup Instructions

### Linux

1. Install Java JDK:
   ```bash
   sudo apt update
   sudo apt install openjdk-11-jdk
   ```

2. Install Maven:
   ```bash
   sudo apt update
   sudo apt install maven
   ```
   
3. Install PostgreSQL
   ```bash
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   ```

4. Clone the repository (if Git is installed):
   ```bash
   git clone https://github.com/vecnehladny/-BVI-SQL-Injection-Lab.git
   cd -BVI-SQL-Injection-Lab
   ```

5. Run Maven:
   ```bash
   mvn clean install
   ```

### Windows

1. Download and install Java JDK from [Oracle's Website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. Download and install Maven from [Apache Maven Project](https://maven.apache.org/download.cgi).
3. Download and install PostgreSQL from [PostgreSQL Official Page](https://www.postgresql.org/download/windows/).

4. Optionally, clone the repository using Git:
   - Download and install Git from [Git SCM](https://git-scm.com/).
   - Open Command Prompt and run:
     ```cmd
     git clone https://github.com/vecnehladny/-BVI-SQL-Injection-Lab.git
     cd -BVI-SQL-Injection-Lab
     ```

5. Run Maven:
   ```cmd
   mvn clean install
   ```
