#!/usr/bin/env bash

keytool -genkey -keyalg RSA -alias selfsigned -dname "cn=Test Cert, ou=MOSERP, o=ORG" -keystore keystore.jks -storepass password -validity 360 -keysize 2048 -keypass password

keytool -export -keystore keystore.jks -storepass password -keypass password -alias selfsigned -file selfsigned.cer


keytool -import -noprompt -file selfsigned.cer -alias selfsigned -keystore truststore.jks -storepass password -keypass password
