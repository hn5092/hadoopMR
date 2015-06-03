#coding=utf-8
import psutil
print psutil.cpu_count(logical=False)
b=psutil.pids()
for a in b:
    print a
c = psutil.process_iter()  #打印进程
for d in c:
    print d
print psutil.get_users()
disk=psutil.disk_partitions(all=True)
print disk
device=disk[0].device
print psutil.disk_usage("d:/")
import sys
print sys.path