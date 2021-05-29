#!/bin/bash
set -ex
sudo chown -R $USER /usr/local/lib/node_modules
npm install -g appium
appium -v
appium &>/dev/null &