#!/bin/bash

if [ -z "$1" ]; 
then 
    echo "usage with param! $1";
    exit 1;
fi
    

RESOLUTIONS="mdpi hdpi xhdpi xxhdpi xxxhdpi"
FILENAME=$1

for RES in $RESOLUTIONS
do
    cp /opt/tools/android/material-design-icons/*/*-$RES/$FILENAME app/src/main/res/drawable-$RES/
done