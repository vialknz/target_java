#this is how to run if the project was built in Eclipse. 
cd /home/kerryn/git/Target_Java/bin
java -cp ../slf4j-jdk14-1.7.14.jar:../netcdfAll-4.6.11.jar:. Target.RunToolkit /home/kerryn/Documents/Work/Toolkit2-Runs/Target-java-validation/controlfiles/Target-java-validation/Target-java-validation.txt

# if you just have a directory of .java files in Target_Java/src/Target/ and netcdfAll-4.6.11.jar is in Target_Java

# cd Target
# javac *.java
# cd ..
# java -cp ../netcdfAll-4.6.11.jar:. Target.RunToolkit

