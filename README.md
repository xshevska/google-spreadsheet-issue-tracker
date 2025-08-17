# Google Spreadsheet Issue Tracker

## Introduction

This is a **Java 17** application built with **Spring Boot** and **Spring Shell**.  
It provides a simple command-line interface (CLI) to manage issues stored in a **Google Spreadsheet**:  
you can create issues, update their statuses, and list them by status — all directly from the terminal.


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
## CLI Commands

Once inside the interactive shell, you can use the following commands:

### Create a new issue

```bash
issue create --description "<your description>" [--parent-id <issueId>]
# Alias:
create --description "<your description>" [--parent-id <issueId>]
```

- `--description` *(required)*: Text describing the issue.
- `--parent-id` *(optional)*: ID of the parent issue.

**Example:**

```bash
issue create --description "Customer 360 job not ingesting" --parent-id AD-1
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