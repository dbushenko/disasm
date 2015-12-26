#!/bin/sh
find $1 -type f -name "*.json" -exec cp '{}' $2 \;
