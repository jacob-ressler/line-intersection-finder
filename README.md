# Line Intersection Finder

You know, for when you lose your line intersection.

![lineintersectionfinder](https://user-images.githubusercontent.com/37934912/54873765-316a1a80-4db4-11e9-9758-f757f6791b78.png)

This is a simple Java Swing application to calculate and display the intersection of 2 lines. As it currently stands, the program can handles and differentiates between the following scenarios:

- Intersection of the 2 selected line segments
- Intersection of the 2 lines outside of the selected segments
- Parallel line segments

The application supports the ability to display results in both logical (continuous, y-value increases as you go up from the bottom of the window) coordinates and device (discrete, y-value increases as you go down from the top of the window) coordinates.

The application also supports the ability to clear the canvas, allowing new lines to be selected.

## Getting started

### **Download and Usage from JAR**

Executable JAR files for all versions are available under the `_releases` directory. To always get the most recent version, click [here](<https://github.com/jacob-ressler/line-intersection-finder/raw/master/_releases/LineIntersectionFinder(v1.0).jar>).

To launch the application, simply double-click the file. The following command can also be used to launch the application from a terminal:

`java -jar "LineIntersectionFinder(v1.0).jar"`

_**Note** that the version number in the command should be identical to version number of the JAR file you have downloaded for it to work properly._

### **Building from Source**

After downloading the `src` folder, use the following command to generate the class files:

`javac IntersectionCanvas.java IntersectionFrame.java`

To launch the application, use the command:

`java IntersectionFrame`

### **How to Use**

All you have to do after launching the application is select 4 points on the canvas. The first 2 points will form a line segment and the last 2 points will form another line segment. The program will then analyze the lines to calculate if and where they intersect.

## Project Information

- Developed by Jacob Ressler
- CIS 457 Computer Graphics
- Cleveland State University
- Spring 2019

