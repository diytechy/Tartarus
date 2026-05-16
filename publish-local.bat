@echo off
echo Publishing TARTARUS pack to local Maven repository...
call gradlew.bat publishToMavenLocal
if %ERRORLEVEL% == 0 (
    echo.
    echo TARTARUS pack published successfully to local Maven.
) else (
    echo.
    echo ERROR: Failed to publish TARTARUS pack.
    exit /b %ERRORLEVEL%
)
