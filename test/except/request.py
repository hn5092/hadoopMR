
# coding=utf-8
import requests
import logging
from logging import handlers

def logger(logger_name, message):
    logfile = 'c:/log'
    logger = logging.getLogger(logger_name)
    logger.setLevel(logging.INFO)
    if logger.handlers:
        logger.handlers = []
    fmt = '%(asctime)s - %(filename)s:%(lineno)s - %(message)s'
    formatter = logging.Formatter(fmt)
    handler = handlers.RotatingFileHandler(logfile, maxBytes = 104857600, backupCount = 30)
    handler.setFormatter(formatter)
    logger.addHandler(handler)

    logger.info(message)
    
def get_baidu():
    resp = requests.get('http://www.baidu.com')
    print resp.headers
    logger('baidu', resp.headers)
get_baidu()