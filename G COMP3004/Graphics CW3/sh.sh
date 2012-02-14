#!/bin/bash
for f in `find . -name "*.temp" ! -type d`; do 
	hg mv -v `echo $f | sed 's/^..\(.*\)\.temp$/\1\.temp/'` `echo $f | sed 's/^\(..\)\(..*\)\.temp$/\2/'`;
done
