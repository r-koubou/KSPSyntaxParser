#!/bin/bash

# Generate a .classpath, .project whitch will be used on vscode for editing codes.
./gradlew build cleanEclipseProject build eclipseClasspath
