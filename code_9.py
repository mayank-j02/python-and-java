import time
in3 = 2
def time_convert(sec):
  mins = sec // 60
  sec = sec % 60
  hours = mins // 60
  mins = mins % 60
  print("Time Lapsed = {0}:{1}:{2}".format(int(hours),int(mins),sec))
input1 = int(input("Press 1 to start accelaration and 2 for lap time.\n"))
if input1 == 1:
  start1 = time.time()
  in2 = int(input("Press 1 again to get elapsed time."))
  end1 = time.time()
  time_elapsed = end1 - start1
  time_convert(time_elapsed)
elif input1 == 2:
  start2 = time.time()
  while in3 !=0:
    in3 = int(input("Enter 1 to end time or 0 to end time."))
    if in3 == 1:
      end2 = time.time()
      time_elapsed2 = end2 -start2
      time_convert(time_elapsed2)
      start2 = end2
else:
  print("not valid")
