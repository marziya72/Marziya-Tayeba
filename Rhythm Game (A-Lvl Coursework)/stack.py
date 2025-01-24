import pygame
from sorting import insertion_sort, save_data, load_data

class stack():
    def __init__(self):
        self.stack = []

    def push(self, item):
        self.stack.append(item)

    def isEmpty(self):
        return len(self.stack) == 0
    
    def pop(self):
        if not self.isEmpty():
            return self.stack.pop()
        else:
            print('stack empty')

    def show(self):
        print(self.stack)

stack0 = stack()
stack1 = stack()
stack2 = stack()

def organise():
    global stack2, stack1, stack0
    sorted_list = insertion_sort()
    for i in reversed(range(len(sorted_list))):
        if sorted_list[i][1] == 2:
            stack2.push(sorted_list[i])
        elif sorted_list[i][1] == 1:
            stack1.push(sorted_list[i])
        else:
            stack0.push(sorted_list[i])

def ranking():
    first = stack2.pop()
    second = stack2.pop()
    third = stack2.pop()

    if first is None:
        first = stack1.pop()
        if first is None:
            first = stack0.pop()
    if second is None:
        second = stack1.pop()
        if second is None:
            second = stack0.pop()
    if third is None:
        third = stack1.pop()
        if third is None:
            third = stack0.pop()
        
    return first, second, third

def hold():
    stack = []
    while not stack2.isEmpty():
        second = stack2.pop()
        stack.append(second)
    while not stack1.isEmpty():
        second = stack1.pop()
        stack.append(second)
    while not stack0.isEmpty():
        second = stack0.pop()
        stack.append(second)

    return stack
            


