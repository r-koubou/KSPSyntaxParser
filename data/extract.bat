@echo off

setlocal

set MANUAL=KSP Reference Manual(6.4.0).txt

python ExtractCallbackFromManual.py "%MANUAL%" > extract_callback.txt
python ExtractCommandFromManual.py "%MANUAL%" > extract_command.txt
python ExtractVariableFromManual.py "%MANUAL%" > extract_variables.txt

endlocal
