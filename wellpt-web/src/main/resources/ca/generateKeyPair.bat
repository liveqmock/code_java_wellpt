rem @echo off
echo alias %1
echo keypass %2
echo keystoreName %3
echo keystorePass %4
echo keyName %5

rem 创建RSA密钥对
keytool -genkey -alias %1 -keypass %2 -keystore %3 -storepass %4 -dname "cn=%1" -keyalg RSA
rem 使用私钥进行自签名
keytool -selfcert -alias %1 -keystore %3 -storepass %4 -keypass %2
rem 导出数字证书
keytool -export -alias %1 -file %5 -keystore %3 -storepass %4