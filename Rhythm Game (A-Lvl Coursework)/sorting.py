from database_functions import create_user, check_credentials, checklogin_credentials, update_score, get_score
import os

def insertion_sort():
    for i in range(1, len(new_list)):
        elem = new_list[i]
        score = int(elem[1])
        j = i - 1
        while j >= 0 and int(new_list[j][1]) > score:
            new_list[j + 1] = new_list[j]
            j -= 1
        new_list[j + 1] = elem
    return new_list

def save_data(sort_list, data):
    with open(sort_list, 'w') as file:
        file.write('\n'.join([' : '.join(map(str, item)) for item in data]))

sort_list = "sort_list.txt"

def load_data(sort_list):
    if os.path.exists(sort_list):
        with open(sort_list, 'r') as file:
            data = file.read().splitlines()
            return [tuple(item.split(': ')) for item in data]
    else:
        return []

def clean_username(username):
    return ' '.join(username.split())

def duplicates(sort_list):
    my_list = load_data(sort_list)
    if my_list:
        new_users = {}
        for item in my_list:
            username, score = item
            username = clean_username(username)
            score = int(score)
            if username in new_users:
                if score > new_users[username][1]:
                    new_users[username] = (username, score)
            else:
                new_users[username] = (username, score)
        new_list = [entry for _, entry in new_users.items()]
        return new_list
    else:
        return [] 


my_list = load_data(sort_list)

new_list = duplicates(sort_list)

save_data(sort_list, new_list)

sorted_list = insertion_sort()





