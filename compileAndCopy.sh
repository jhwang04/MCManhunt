#!/bin/bash
./gradlew clean build

scp  ~/IdeaProjects/MCManhunt/build/libs/MCManhunt-1.0.jar justin@server.goliath.org:/opt/minecraft/homestead/plugins
