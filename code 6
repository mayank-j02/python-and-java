import math
def add(x,y):
    return x+y
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
    return mmath.tan(math.radians(a))
print('1-normal')
print('2-trigo')
e = input('choose:')
m = int(e)

if m == 1:
    print('select operation:')
    print('1-addition')
    print('2-subtraction')
    print('3-multiplication')
    print('4-division')
    print('5-exponent')

    choice = input('enter the choice:')
    try :
        op = int(choice)
    except:
        print(-1)

    x = input('Enter first number:')
    y = input('Enter second number:')
    try :
        num1 = float(x)
        num2 = float(y)
    except:
        print('invalid input')
    if op == 1:
        print(add(num1,num2))
    elif op == 2:
        print(subtraction(num1,num2))
    elif op == 3:
        print(multiplication(num1,num2))
    elif op == 4:
        print(division(num1,num2))
    elif op == 5:
        print(exp(num1,num2))
    else:
        print("invalid input")
elif m == 2:
    print('1-sin')
    print('2-cos')
    print('3-tan')
    operation = int(input('select the operation:'))
    angle1 = input('write the value of angle in degrees:')
    angle = float(angle1)
    if operation == 1:
        print(sin(angle))
    elif operation == 2:
        print(cos(angle))
    elif operation == 3:
        print(tan(angle))
    else:
        print('invalid')
else:
    print('invalid input')
