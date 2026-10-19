@echo off
setlocal
echo ====================================================================
echo   ShopSphere - Database Setup Script (MySQL)
echo ====================================================================

set MYSQL_BIN="C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe"
if not exist %MYSQL_BIN% (
    set MYSQL_BIN=mysql
)

echo Importing database\shopsphere.sql into MySQL...
echo (Default credentials from setup: root / root)
echo.

%MYSQL_BIN% -u root -proot < "%~dp0database\shopsphere.sql"

if %ERRORLEVEL% equ 0 (
    echo.
    echo [SUCCESS] ShopSphere database initialized with 12 tables and seed data!
) else (
    echo.
    echo [ERROR] Failed to connect with default password 'root'.
    echo Please enter your MySQL root password manually:
    set /p DB_PASS=Password: 
    %MYSQL_BIN% -u root -p%DB_PASS% < "%~dp0database\shopsphere.sql"
    if %ERRORLEVEL% equ 0 (
        echo [SUCCESS] Database initialized successfully!
    ) else (
        echo [ERROR] Database setup failed. Please check your credentials.
    )
)

pause
