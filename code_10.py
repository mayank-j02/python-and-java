from datetime import datetime
timex = input('enter the first time in the format minute:seconds:milliseconds: ')
timey = input('enter the second time in the same format: ')
timez = input('enter the third time in the same format: ')
time1 = datetime.strptime(timex, '%M:%S:%f')
time2 = datetime.strptime(timey, '%M:%S:%f')
time3 = datetime.strptime(timez, '%M:%S:%f')
if time1<time2 and time1<time3:
    print('time1 is the shortest: ', time1)
elif time2<time1 and time2<time3:
    print('time2 is the shortest: ', time2)
elif time3<time1 and time3<time2:
    print('time3 is the shortest: ', time3)
else:
    print('Try Again')
