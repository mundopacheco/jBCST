# Berg's Card Sorting Test in JAVA #

This is an implementation of the Berg's Card Sorting Test in java. Following PEBL,

> We refer to our implementation of the Wisconsin Card Sorting Test as
> "Berg's Card Sorting Test" because the more common name, the
> "Wisconsin Card Sorting Test" is a registered trademark, and it was
> first developed by Esta Berg at the University of Wisconsin.

Repository: https://github.com/mundopacheco/jBCST/

# Authors #

Edmundo Pacheco Blas, Facultad de Ciencias, UNAM.

Roberto Velasco Segura, CCADET, UNAM.

# Registration #

Indautor: ...number...

## The Jar File ##

To run the jar file you'll need a Linux, Mac or Windows operating
system on which  you can install the
[Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),
[here's a tutorial to install it](https://www.youtube.com/watch?v=evoLlsLFn10).
The requirements can be found
[here](http://www.oracle.com/technetwork/java/javase/certconfig-2095354.html),
though most computers should have no problems complying.

## Editing ##

Netbeans was used to create this version of the WCST, but it's not a requirement
for running the jar file. As always you only need a text editor to modify
and edit the code.

# Usage #

This version of the WCST is free software and intended for academic purposes.

This code was written in Linux and Mac OS and therefore we recommend that
the test be run on a unix-based system.

After installing the java developer tools and unzipping the project,
you only need to double click on the Jar file to run the program. A menu
will open in which you can choose the WCST option to run the test.
Then you must discharge the subject to whom the test will be applied.
The other two options are a list of subjects and a random answers generator

The missing files will be added to the directory in which the application is
running, however the "images" folder containing the images with which it works
must be manually added by the user, if the program is not in the same folder
It will not run correctly.

## Compile and run on Linux ##

Move cwd to the repo root directory
```bash
cd jBCST
```
create a directory for the binary files
```bash
mkdir -p bin
```
compile
```bash
javac -d bin -s src -classpath src/ src/jBCST/*.java
```
run
```bash
java -classpath bin/ jBCST.jBCST
```

## Output ##

The generated files are in a folder that bears the name of the subject, said
folder contains the following documents:

- queries.csv, list of parameter values of each query
- reference.csv, list of parameter values of the four main option cards
- results-date-time.txt, summary of results
- Selected Cards-date-time.csv, list of the chosen cards and their evaluation

For more details regarding the output files clic [here](Output.md)

# Customization #

We have seen different studies use some specific rules to present
trials and evaluate results.

One of the aims of this code is allow such customization, some effort
has been made to place (hopefully) all that could need customization
in a single file named `Manual.java`. See the coments inside this file
for details.
