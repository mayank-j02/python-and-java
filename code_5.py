def bubble_sort(list1):
    for i in range(0,len(list1)-1):
        for j in range(len(list1)-1):
            if(list1[j]>list1[j+1]):
                list1[j],list1[j+1] = list1[j+1], list1[j]
    return list1

list1 = []
n = int(input('Enter the number of elements: '))
for i in range(0,n):
    x = int(input())
    list1.append(x) 
print("The unsorted list is: ", list1)
print("The sorted list is: ", bubble_sort(list1))
