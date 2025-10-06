import pygame
from pygame.locals import *
import sys
import re
import button
import slider
import tutorial
from database_functions import create_user, check_credentials, checklogin_credentials, update_score, get_score
from minigame_1 import countdown, minigame1
from minigame_2 import countdown, minigame2
from main_menu import handle_keydown, menu, signup, login, settings, stage_menu
from stack import organise, ranking

pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height), pygame.RESIZABLE)
pygame.display.set_caption('Rhythm Cook')


if __name__ == "__main__":
    menu()
    pygame.quit()

