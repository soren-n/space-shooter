rm -fr ./desktop/bin/classes/*

javac -classpath "./desktop/libs/*:./main/libs/*:./desktop/bin/classes" -sourcepath ./desktop/src/com/oaken/rockit:./main/src/com/oaken/rockit -d ./desktop/bin/classes ./desktop/src/com/oaken/rockit/*.java ./main/src/com/oaken/rockit/*.java 2> ./logs/desktop.txt

java -classpath "./desktop/libs/*:./main/libs/*:./desktop/bin/classes" com.oaken.rockit.DesktopLauncher 2>> ./logs/desktop.txt
