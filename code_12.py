from datetime import datetime
timex = input('enter the first time in the format minute:seconds:milliseconds: ')
timey = input('enter the second time in the same format: ')
timez = input('enter the third time in the same format: ')
time1 = datetime.strptime(timex, '%M:%S:%f')
time2 = datetime.strptime(timey, '%M:%S:%f')
time3 = datetime.strptime(timez, '%M:%S:%f')

x = min(time1, time2, time3)
print(x)
