@echo off

REM  
REM Copyright (C) 2016-2022 phantombot.github.io/PhantomBot
REM  
REM This program is free software: you can redistribute it and/or modify
REM it under the terms of the GNU General Public License as published by
REM the Free Software Foundation, either version 3 of the License, or
REM (at your option) any later version.
REM 
REM This program is distributed in the hope that it will be useful,
REM but WITHOUT ANY WARRANTY; without even the implied warranty of
REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
REM GNU General Public License for more details.
REM  
REM You should have received a copy of the GNU General Public License
REM along with this program.  If not, see <http://www.gnu.org/licenses/>.
REM

REM
REM PhantomBot Launcher - Windows
REM

IF /I "%2" == "--df" GOTO :DELTRACK
:CHECKONE
IF /I "%1" == "--nowt" GOTO :LAUNCH

WHERE powershell >nul 2>nul
IF %ERRORLEVEL% NEQ 0 GOTO :LAUNCH

WHERE wt >nul 2>nul
IF %ERRORLEVEL% EQU 0 GOTO :SWITCHTOWT

GOTO :LAUNCH

:DELTRACK
del /q /f .\config\wtcheck.txt > NUL
GOTO :CHECKONE

:LAUNCH
setlocal enableextensions enabledelayedexpansion
cd %~dp0
".\java-runtime\bin\java" --add-exports java.base/sun.security.x509=ALL-UNNAMED --add-opens java.base/java.lang=ALL-UNNAMED -Duser.language=en -Djava.security.policy=config/security -Dinteractive -Xms1m -Dfile.encoding=UTF-8 -jar "PhantomBot.jar" %*
endlocal
pause

GOTO :EOF

:SWITCHTOWT
setlocal enableextensions enabledelayedexpansion
copy /y NUL .\config\wtcheck.txt > NUL
wt nt --profile "Command Prompt" --startingDirectory "%~dp0\" --title PhantomBot launch.bat --nowt --df %*
timeout /t 5 /nobreak > NUL
IF EXIST .\config\wtcheck.txt GOTO :LAUNCH
endlocal
