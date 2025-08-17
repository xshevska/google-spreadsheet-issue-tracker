# Google Spreadsheet Issue Tracker

## Configuration

Before running the application, you need to set up the following:

1. Copy `.env.example` to `.env` and update the values:
   ```bash
   cp .env.example .env
   ```

2. Update the `.env` file with your Google Spreadsheet ID and other configuration

3. Place your Google API credentials in the `.secrets` directory:
   ```bash
   mkdir -p .secrets
   # Copy your credentials.json file to .secrets/credentials.json
   ```

## Running with Docker

1. Make sure you have Docker and Docker Compose installed

2. Build and run the application:
   ```bash
   docker-compose run --rm issue-tracker
   ```

## Security Notes

- Never commit the `.env` file or `.secrets` directory to version control
- The `.env.example` file serves as a template and should not contain real credentials
- Make sure to properly secure your Google API credentials

## Assignment Description

The goal of this assignment is to build a **Java CLI application** that tracks issues in a **Google Spreadsheet**.

### Supported Actions
The application allows the user to:

1. **Create a new issue** with parameters:
    - `description` *(required)*
    - `parent issue id` *(optional)*

2. **Update the status** of an existing issue with parameters:
    - `issue id` *(required)*
    - `status` *(OPEN, IN_PROGRESS, CLOSED) [required]*

3. **List all issues** by:
    - `status` *(OPEN, IN_PROGRESS, CLOSED) [required]*

Example spreadsheet format:

| ID   | Description                     | Parent ID | Status       | Created at       | Updated at       |
|------|---------------------------------|-----------|--------------|------------------|------------------|
| AD-1 | Customer 360 Job not ingesting  |           | CLOSED       | 2024-10-01T10:02 | 2024-10-02T14:35 |
| AD-2 | Databricks Job failing on DoB   | 1         | OPEN         | 2024-10-03T16:12 |                  |

---
