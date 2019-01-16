@echo off
echo starting prerun
echo Windows Registry Editor Version 5.00 > mediaCapture.reg
echo   >> mediaCapture.reg
echo [HKEY_CURRENT_USER\Software\Classes\Local Settings\Software\Microsoft\Windows\CurrentVersion\AppContainer\Storage\microsoft.microsoftedge_8wekyb3d8bbwe\MicrosoftEdge\MediaCapture\AllowDomains] "https://www.onlinemictest.com"=dword:00000002 > mediaCapture.reg
echo content of registry file to import:
print mediaCapture.reg
echo importing registry file
reg import mediaCapture.reg
echo prerun complete
