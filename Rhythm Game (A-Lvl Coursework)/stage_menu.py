import pygame
import sys
import re
import button
import slider
from tutorial import Tutorial
from database_functions import create_user, check_credentials, checklogin_credentials, update_score, get_score
from minigame_1 import countdown, minigame1
from minigame_2 import countdown, minigame2
from sorting import insertion_sort, save_data, load_data
from stack import organise, ranking, hold
from extended_leaderboard import extended_leaderboard

pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))

def stage_menu(current_username):
    font1 = pygame.font.Font('holly.ttf', 52)
    font2 = pygame.font.Font('rocky.ttf', 72)
    font3 = pygame.font.Font('rocky.ttf', 30)
    font4 = pygame.font.Font('rocky.ttf', 36)
    score = get_score(current_username)
    display_score = get_score(current_username)
    organise()
    rank = ranking()
    first_name = str(rank[0][0])
    second_name = str(rank[1][0])
    third_name = str(rank[2][0])
    first_score = str(rank[0][1])
    second_score = str(rank[1][1])
    third_score = str(rank[2][1])

    scoreboard_surface = pygame.image.load('graphics/scoreboard.png').convert_alpha()
    board_surface = pygame.image.load('graphics/boardbg.png').convert_alpha()
    board_surface = pygame.transform.scale(board_surface, (235,190))
    creambg_surface = pygame.image.load('graphics/creambg.png').convert_alpha()
    creambg_surface = pygame.transform.scale(creambg_surface, (640,430))
    minigame1_img = pygame.image.load('graphics/minigame1.png').convert_alpha()
    minigame2_img = pygame.image.load('graphics/minigame2.png').convert_alpha()
    minigame3_img = pygame.image.load('graphics/minigame3.png').convert_alpha()
    finaldish1_img = pygame.image.load('graphics/finaldish1.png').convert_alpha()

    minigame1_button = button.Button(210, 230, minigame1_img, 0.35)
    minigame2_button = button.Button(210, 300, minigame2_img, 0.35)
    minigame3_button = button.Button(210, 370, minigame3_img, 0.35)
    finaldish1_button = button.Button(210, 440, finaldish1_img, 0.35)

    minigame4_img = pygame.image.load('graphics/minigame4.png').convert_alpha()
    minigame5_img = pygame.image.load('graphics/minigame5.png').convert_alpha()
    minigame6_img = pygame.image.load('graphics/minigame6.png').convert_alpha()
    finaldish2_img = pygame.image.load('graphics/finaldish2.png').convert_alpha()

    minigame4_button = button.Button(420, 230, minigame4_img, 0.35)
    minigame5_button = button.Button(420, 300, minigame5_img, 0.35)
    minigame6_button = button.Button(420, 370, minigame6_img, 0.35)
    finaldish2_button = button.Button(420, 440, finaldish2_img, 0.35)

    minigame7_img = pygame.image.load('graphics/minigame7.png').convert_alpha()
    minigame8_img = pygame.image.load('graphics/minigame8.png').convert_alpha()
    minigame9_img = pygame.image.load('graphics/minigame9.png').convert_alpha()
    finaldish3_img = pygame.image.load('graphics/finaldish3.png').convert_alpha()

    minigame7_button = button.Button(620, 230, minigame7_img, 0.35)
    minigame8_button = button.Button(620, 300, minigame8_img, 0.35)
    minigame9_button = button.Button(620, 370, minigame9_img, 0.35)
    finaldish3_button = button.Button(620, 440, finaldish3_img, 0.35)
    
    back2_img = pygame.image.load('graphics/back2.png').convert_alpha()
    back2_button = button.Button(870, 500, back2_img, 0.5)
    more_text = font1.render('~ more ~', True, 'skyblue2')
    more_button = button.Button(900, 452, more_text, 0.5)
    action = False

    sort_list = "sort_list.txt"
    stack = hold()

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
        screen.fill((255,239,213))
        screen.blit(creambg_surface, (150,120))
        screen.blit(scoreboard_surface, (800,150))
        screen.blit(board_surface, (815,290))
        stage1_text = font1.render('Stage 1', True, 'paleturquoise3')
        screen.blit(stage1_text, (200, 150))
        stage2_text = font1.render('Stage 2', True, 'paleturquoise3')
        screen.blit(stage2_text, (400, 150))
        stage3_text = font1.render('Stage 3', True, 'paleturquoise3')
        screen.blit(stage3_text, (600, 150))
        title_text = font2.render('Rhythm Cook', True, 'skyblue3')
        screen.blit(title_text, (280, 30))
        score_text = font2.render('%i' %display_score, True, 'skyblue3')
        screen.blit(score_text, (960, 180))
        first_place = font3.render('1st  '+first_name+'   -   '+first_score, True, 'skyblue3')
        screen.blit(first_place, (835, 335))
        second_place = font3.render('2nd  '+second_name+'   -   '+second_score, True, 'skyblue3')
        screen.blit(second_place, (835, 375))
        third_place = font3.render('3rd  '+third_name+'   -   '+third_score, True, 'skyblue3')
        screen.blit(third_place, (835, 415))
        leaderboard = font4.render('Leaderboard', True, 'grey')
        screen.blit(leaderboard, (850, 295))
        systems_text = font4.render('This minigame is not ready yet', True, 'grey')
        if action:
            screen.blit(systems_text, (200, 500))
        dish1_text = font3.render('(Soup)', True, 'grey')
        screen.blit(dish1_text, (230, 190))
        dish2_text = font3.render('(coming soon)', True, 'grey')
        screen.blit(dish2_text, (395, 190))
        dish3_text = font3.render('(coming soon)', True, 'grey')
        screen.blit(dish3_text, (595, 190))
        carrot_surface = pygame.image.load('graphics/carrot.png').convert_alpha()
        carrot_surface = pygame.transform.scale(carrot_surface, (25, 25))
        screen.blit(carrot_surface, (180, 245))
        onion_surface = pygame.image.load('graphics/onion.png').convert_alpha()
        onion_surface = pygame.transform.scale(onion_surface, (20,22))
        potato_surface = pygame.image.load('graphics/potato.png').convert_alpha()
        potato_surface = pygame.transform.scale(potato_surface, (20,24))
        screen.blit(onion_surface, (159, 312))
        screen.blit(potato_surface, (184,310))

        if minigame3_button.draw() or minigame4_button.draw() or minigame5_button.draw() or minigame6_button.draw() or minigame7_button.draw() or minigame8_button.draw() or minigame9_button.draw() or finaldish1_button.draw() or finaldish2_button.draw() or finaldish3_button.draw():
            action = True
         
        if minigame1_button.draw():
            carrot_surface = pygame.image.load('graphics/carrot.png').convert_alpha()
            carrot_surface = pygame.transform.scale(carrot_surface, (100, 100))
            tutorial = Tutorial()
            tutorial.instructions(carrot_surface)
            if tutorial is not None or skip == True:
                countdown_result = countdown()
                if countdown_result is not None:
                    final_score = minigame1()
                    print('username: ', current_username)
                    print('Score: ' + str(final_score))
                    if final_score is not None and final_score >= 26 and score == 0:
                        score += 1
                        update_score(current_username, score)
                        display_score = get_score(current_username)
                        my_list = load_data(sort_list)
                        my_list.append([current_username, display_score])
                        save_data(sort_list, my_list)
                        print(my_list)
        elif minigame2_button.draw():
            if get_score(current_username) >= 1:
                onion_surface = pygame.image.load('graphics/onion.png').convert_alpha()
                onion_surface = pygame.transform.scale(onion_surface, (55, 60))
                potato_surface = pygame.image.load('graphics/potato.png').convert_alpha()
                potato_surface = pygame.transform.scale(potato_surface, (58, 70))
                tutorial = Tutorial()
                tutorial.instructions2(onion_surface, potato_surface)
                if tutorial is not None or skip == True:
                    countdown_result = countdown()
                    if countdown_result is not None:
                        final_score = minigame2()
                        print('username: ', current_username)
                        print('Score: ' + str(final_score))
                        if final_score is not None and final_score >= 26 and score == 1:
                            score += 1
                            update_score(current_username, score)
                            display_score = get_score(current_username)
                            my_list = load_data(sort_list)
                            my_list.append([current_username, display_score])
                            save_data(sort_list, my_list)
                            print(my_list)
        elif back2_button.draw():
            return
        elif more_button.draw():
            stack = extended_leaderboard()
            
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

        pygame.display.update()
