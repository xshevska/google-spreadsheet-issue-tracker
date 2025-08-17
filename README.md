# Google Spreadsheet Issue Tracker

A **Spring Boot** CLI application for managing issues using Google Sheets as storage. Built with **Java 17** and **Spring Shell**, 
it provides commands to create, update, and list issues directly from your terminal.

## Setup Guide

### 1. Google Cloud Configuration
1. Navigate to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a project or select an existing one
3. Enable the Google Sheets API
4. Create a Service Account:
   - Go to "Credentials" → "Create Credentials" → "Service Account"
   - Download the JSON credentials file
   - Copy the service account email address
   - Store the JSON file as `.secrets/credentials.json`

### 2. Google Sheets Configuration
1. Create a new spreadsheet in Google Sheets
2. Grant Editor access to the service account email
3. Create a sheet named `Issues` (case-sensitive)
4. Copy your spreadsheet ID from the URL:
   ```
   https://docs.google.com/spreadsheets/d/{SPREADSHEET_ID}/edit
   ```

## Application Setup

### Local Development
1. Set up credentials:
   ```bash
   # Create credentials directory
   mkdir -p .secrets
   
   # Copy previously downloaded Google API credentials
   cp path/to/downloaded/credentials.json .secrets/credentials.json
   ```

2. Configure environment:
   
Before running the application, you need to configure the environment and set the required variables.

   ```bash
   # Set the Google Spreadsheet ID in your terminal
   export GSHEETS_SPREADSHEET_ID=your_spreadsheet_id
   ```

3. Start the application:
   ```bash
   mvn spring-boot:run
   ```

> Note: The `export` command must be run in the terminal session where you'll start the application. 
> If you open a new terminal, you'll need to set the environment variable again.

### Configuration Reference
The application uses the following settings (`application.yml`):
```yaml
gsheets:
  spreadsheet-id: ${GSHEETS_SPREADSHEET_ID}
  sheet-name: ${GSHEETS_SHEET_NAME:Issues}
  credentials-path: ${GSHEETS_CREDENTIALS_PATH:.secrets/credentials.json}
```

## Docker Deployment

1. Configure environment variables in `.env`:
   ```properties
   GSHEETS_SPREADSHEET_ID=your_spreadsheet_id
   GSHEETS_SHEET_NAME=Issues
   GSHEETS_CREDENTIALS_PATH=/app/.secrets/credentials.json
   ```
   #### Environment file (.env)

    The runtime environment file is expected at:
    
    ```bash
    ./docker/env/.env
    ```

2. Run the application:
   ```bash
   # Start the application
   docker-compose run --rm issue-tracker
   ```

## Security Notes

- Protect sensitive files:
  - Never commit `.env` files or `.secrets` directory
  - Use `.env.example` as a configuration template

## Project Structure
```
.
├── docker/
│   ├── Dockerfile
│   └── env/
│       ├── .env        # Docker environment (do not commit)
│       └── .env.example
├── .secrets/
│   └── credentials.json # Google API credentials (do not commit)
├── src/
├── docker-compose.yml
└── pom.xml
```


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
## CLI Commands

Once inside the interactive shell, you can use the following commands:

### Create a new issue

```bash
issue create --description "<your description>" [--parentId <issueId>]
# Alias:
create --description "<your description>" [--parentId <issueId>]
```

- `--description` *(required)*: Text describing the issue.
- `--parentId` *(optional)*: ID of the parent issue.

**Example:**

```bash
issue create --description "Customer 360 job not ingesting" --parentId AD-1
```

---

### Update the status of an issue

```bash
issue update-status --id <issueId> --status <STATUS>
# Alias:
update-status --id <issueId> --status <STATUS>
```

- `--id` *(required)*: Existing issue ID (e.g., `AD-2`).
- `--status` *(required)*: One of `OPEN`, `IN_PROGRESS`, `CLOSED`.

**Rule:** Status values must always be written in **UPPERCASE** exactly as shown.

**Example:**

```bash
issue update-status --id AD-2 --status CLOSED
```

---

### List issues by status

```bash
issue list --status <STATUS>
# Alias:
list --status <STATUS>
```

- `--status` *(required)*: Status to filter by. Accepted values: `OPEN`, `IN_PROGRESS`, `CLOSED`.
- Must also be in **UPPERCASE**.

**Examples:**

```bash
issue list --status IN_PROGRESS
issue list --status OPEN
```

**Output format:**

```
Issue AD-5 [OPEN]
  Desc: Customer 360 job failing
  Parent: AD-1
  Created: 2024-10-03T16:12
  Updated: null
```

If no issues are found:

```
No issues with status <STATUS>
```

---