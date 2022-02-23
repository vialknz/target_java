cd ..
cd src
#sh compile_code.sh

cd Target
javac -cp ../../netcdfAll-4.6.11.jar:../../slf4j-jdk14-1.7.14.jar:. *.java HTC/*.java

cd ..
java -cp ../netcdfAll-4.6.11.jar:. Target.RunToolkit ../example/controlfiles/Mawson/MawsonExample.txt


