# MQTT-Launch
Java app for running Windows programs using MQTT

Instalation:
1. Download JAR file
1. Download nircmd: https://www.nirsoft.net/utils/nircmd.html
2. Put nircmd.exe in the folder containing JAR file
3. Configure application with application.properties
4. Configure OS to autorun JAR file on startup

Features:
- runs applications based on the messages received in the subscribeTopic
- publishes current time in millis if "isOn" message is received
