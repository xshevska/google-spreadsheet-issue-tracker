# Google Spreadsheet Issue Tracker

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
