import pygame
import sys
import re
import button
from database_functions import create_user, check_credentials, checklogin_credentials, update_score, get_score
from sorting import insertion_sort, save_data, load_data
from stack import organise, ranking, hold

pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))

def extended_leaderboard():
    font1 = pygame.font.Font('holly.ttf', 72)
    font3 = pygame.font.Font('rocky.ttf', 30)
    border_surface = pygame.image.load('graphics/border.png').convert_alpha()
    border_surface = pygame.transform.scale(border_surface, (580,600))
    back3_img = pygame.image.load('graphics/back2.png').convert_alpha()
    back3_button = button.Button(920, 610, back3_img, 0.5)
    organise()
    stack = hold() 

    while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
        screen.fill((255,239,213))
        distance = 0
        for i, (item, score) in enumerate(stack):
            if i == 0:
                rank_text = '1st'
            elif i == 1:
                rank_text = '2nd'
            elif i == 2:
                rank_text = '3rd'
            else:
                rank_text = str(i+1) + 'th'

            place_text = font3.render(rank_text + '  ' + item + '   -   '+str(score) , True, 'skyblue3')
            screen.blit(place_text, (450, 135 + distance))
            distance += 40

        leaderboard = font1.render('Leaderboard', True, 'grey')
        screen.blit(leaderboard, (400, 60))
        screen.blit(border_surface, (280, 30))

        if back3_button.draw():
            return

        pygame.display.update()
    return stack
