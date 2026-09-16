@echo off
:: Navigate into the Maven project directory (already done tho)
echo Copying assets..
:: I dont know how to make it work properly.....
robocopy "assets" "target/assets" /E 
:: Compile the code and build the executable JAR file
echo Compiling project with Maven...
call mvn package

:: Check if the build was successful
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed!
    pause
    exit /b %ERRORLEVEL%
)

:: Run the compiled program
echo.
echo Starting application...
echo --------------------------------------------------
java -jar target/funkinjav-1.0-SNAPSHOT-jar-with-dependencies.jar

:: Prevents the console window from instantly closing when the program finishes
echo --------------------------------------------------
echo Application finished.
