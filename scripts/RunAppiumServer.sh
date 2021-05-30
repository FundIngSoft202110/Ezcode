#!/bin/bash
set -ex
sudo npm install -g appium --unsafe-perm=true --allow-root
appium -v
appium &>/dev/null &