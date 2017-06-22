#!/bin/sh

function uninstall {
    adb uninstall com.kasparpeterson.minibux
}

function testUnit {
    ./gradlew testDebugUnitTest
}