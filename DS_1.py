  
import math
def add(x,y):
    return x + y
def subtraction(x,y):
    return x-y
def multiplication(x,y):
    return x*y
def division(x,y):
    return x/y
def exp(x,y):
    return x**y
def sin(a):
    return math.sin(math.radians(a))
def cos(a):
    return math.cos(math.radians(a))
def tan(a):
    return math.tan(math.radians(a))
optuple = (1,2,3,4,5,6,7,8)
(add, subtraction ,multiplication ,division , exp ,sin ,cos,tan)= optuple
name = input("enter the function.")
m = int(name)
if m in (1,2,3,4,5):
    x = input("First number: ")
    num1 = float(x)
    y = input("second number: ")
    num2 = float(y)
    if m == 1:
        print(add(num1,num2))
    elif m == 2:
        print(subtraction(num1,num2))
    elif m == 3:
        print(multiplication(num1,num2))
    elif m == 4:
        print(division(num1,num2))
    elif m == 5:
        print(exp(num1,num2))
elif m in (6, 7, 8):
    num3 = float(input("Enter the angle: "))
    if m == 6:
        print(sin(num3))
    elif m == 7:
        print(cos(num3))
    elif m == 8:
        print(tan(num3))
    else:
        print('invalid')
else:
    print('invalid')
