import re
import codecs
import fileinput
print re.compile(r"[a-z]").match("Madasd")

print re.match(r'[a-z]', 'aAd',flags=0).group()
print re.search(r'[a-z]', 'Asd',flags=0).pos#endpos
f= codecs.open('e://t .txt','r+','gbk')
a=f.read()
f.write("xym")
print a

#f1=fileinput.input(files, inplace, backup, bufsize, mode, openhook)