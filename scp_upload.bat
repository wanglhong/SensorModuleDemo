@echo off
setlocal enabledelayedexpansion

:: 设置变量
set "source_file=./SensorModuleDemo-Client/target/SensorModuleDemo-Client-1.0-SNAPSHOT.jar"
set "destination_file=//home/pi/SensorModuleDemo/"
set "destination_ip=pi.wlih.cn"
set "username=pi"
set "password=raspberry"

:: 使用scp命令上传文件
scp %source_file% %username%@%destination_ip%:%destination_file%

:: 自动输入密码
:: scpi -s %destination_ip% -l %username% -pw %password%

echo 测试成功！
pause