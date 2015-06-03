#coding=utf-8
import pexpect
child = pexpect.spawn('ssh -p 22 root@xym03')
pexpect_log = open('/root/桌面/log','w')
child.logfile=pexpect_log
child.expect("#")
child.sendline('ls -l /')
child.expect("#")
pexpect_log.close()