call generateKeyPair.bat server serverpass serverStore.jks storepass serverKey.rsa
rem call generateKeyPair.bat client7 client7pass client7Store.jks storepass client7Key.rsa

rem 将服务器的数字证书导入客户端的密钥库
rem keytool -import -alias server -file serverKey.rsa -keystore client7Store.jks -storepass storepass -noprompt
rem keytool -import -alias client7 -file client7Key.rsa -keystore serverStore.jks -storepass storepass -noprompt

keytool -list -keystore serverStore.jks -storepass storepass
keytool -list -keystore client7Store.jks -storepass storepass
