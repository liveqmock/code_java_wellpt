rem @echo off
echo alias %1
echo keypass %2
echo keystoreName %3
echo keystorePass %4
echo keyName %5

rem ����RSA��Կ��
keytool -genkey -alias %1 -keypass %2 -keystore %3 -storepass %4 -dname "cn=%1" -keyalg RSA
rem ʹ��˽Կ������ǩ��
keytool -selfcert -alias %1 -keystore %3 -storepass %4 -keypass %2
rem ��������֤��
keytool -export -alias %1 -file %5 -keystore %3 -storepass %4