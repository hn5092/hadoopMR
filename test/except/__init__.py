# coding=utf-8
from fileinput import filename
import logging
import Crypto
#时间 文件名 错误的行数 消息
logging.basicConfig(filename='c:/log', format='%(asctime)s-%(filename)s %(lineno)s %(message)s', LEVEL=logging.DEBUG, filemode='a')  # a是追加的模式 w是重置
logging.info("it is info log level")
logging.error("it is error log level")
logging.debug("it is debug log level")
