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
from stage_menu import stage_menu

pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))

def handle_keydown(event, user_text):
    if event.key == pygame.K_BACKSPACE:
        user_text = user_text[:-1]
    else:
        user_text += event.unicode
    return user_text

def menu():
    current_username = ''
    action = False
    font = pygame.font.Font('holly.ttf', 32)
    font2 = pygame.font.Font('holly.ttf', 82)
    signup_img = pygame.image.load('graphics/signup.png').convert_alpha()
    login_img = pygame.image.load('graphics/login.png').convert_alpha()
    settings_img = pygame.image.load('graphics/settings.png').convert_alpha()
    start_img = pygame.image.load('graphics/start.png').convert_alpha()
    menu_surface = pygame.image.load('graphics/menu.png').convert_alpha()
    menu_surface = pygame.transform.scale(menu_surface, (400, 80))
    paper_surface = pygame.image.load('graphics/paper.png').convert_alpha()
    paper_surface = pygame.transform.scale(paper_surface, (450, 600))

    signup_button = button.Button(450, 150, signup_img, 0.35)
    login_button = button.Button(450, 270, login_img, 0.35)
    settings_button = button.Button(450, 390, settings_img, 0.35)
    start_button = button.Button(450, 510, start_img, 0.35)

    while True:
        screen.fill((162,181,205))
        screen.blit(paper_surface, (310, 50))
        menu_text = font2.render('Main Menu', True, 'skyblue3')
        screen.blit(menu_text, (390, 70))
        user_text = font.render('User: ' + current_username, True, 'brown4')
        screen.blit(user_text, (780, 150))
        systems_text = font.render('Log in or sign up first', True, 'brown4')
        if action == True:
            screen.blit(systems_text, (410, 610))

        if signup_button.draw():
            current_username = signup()
        if login_button.draw():
            current_username = login()
        if settings_button.draw():
            settings()
        if start_button.draw():
            if len(current_username) > 0:
                stage_menu(current_username)
                action = False
            else:
                print('start')
                action = True

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

        pygame.display.update()

def signup():
    clock = pygame.time.Clock()
    base_font = pygame.font.Font(None, 32)
    font = pygame.font.Font('holly.ttf', 22)
    font2 = pygame.font.Font('holly.ttf', 42)
    font3 = pygame.font.Font('holly.ttf', 72)
    username = ''
    password = ''
    email = ''
    info = ''
    info2 = ''
    action = 0

    usernamef_surface = pygame.image.load('graphics/usernamef.png').convert_alpha()
    passwordf_surface = pygame.image.load('graphics/passwordf.png').convert_alpha()
    emailf_surface = pygame.image.load('graphics/emailf.png').convert_alpha()

    print_surface = pygame.image.load('graphics/print.png').convert_alpha()
    print_surface = pygame.transform.scale(print_surface, (1300, 800))
    check_img = pygame.image.load('graphics/check.png').convert_alpha()
    check_button = button.Button(700, 500, check_img, 0.5)
    back_img = pygame.image.load('graphics/back.png').convert_alpha()
    back_button = button.Button(900, 500, back_img, 0.5)

    input_rect1 = pygame.Rect(200, 200, 140, 32)
    input_rect2 = pygame.Rect(200, 250, 140, 32)
    input_rect3 = pygame.Rect(200, 300, 140, 32)

    color_active = pygame.Color('lightcyan3')
    color_passive = pygame.Color('gray15')
    color1, color2, color3 = color_passive, color_passive, color_passive

    active1, active2, active3 = False, False, False

    while True:
        screen.fill((119, 147, 60))
        screen.blit(print_surface, (0,0))
        screen.blit(usernamef_surface, (200, 190))
        screen.blit(passwordf_surface, (200, 290))
        screen.blit(emailf_surface, (200, 390))
        username_text = font.render('Must be three or more characters:' , True, 'lightcyan3')
        screen.blit(username_text, (200, 150))
        password_text = font.render('Must be 6 or more characters:' , True, 'lightcyan3')
        screen.blit(password_text, (200, 250))
        sign_up_text = font3.render('SIGN UP' , True, 'brown4')
        screen.blit(sign_up_text, (480, 60))
        invalid_text = font2.render('inavlid' , True, 'brown4')

        input_rect1.topleft = (500, 200)
        pygame.draw.rect(screen, color1, input_rect1, 2)
        text_surface1 = base_font.render(username, True, (255, 255, 255))
        screen.blit(text_surface1, (input_rect1.x + 5, input_rect1.y + 5))
        input_rect1.w = max(140, text_surface1.get_width() + 10)

        input_rect2.topleft = (500, 300)
        pygame.draw.rect(screen, color2, input_rect2, 2)
        text_surface2 = base_font.render(password, True, (255, 255, 255))
        screen.blit(text_surface2, (input_rect2.x + 5, input_rect2.y + 5))
        input_rect2.w = max(140, text_surface2.get_width() + 10)

        input_rect3.topleft = (500, 400)
        pygame.draw.rect(screen, color3, input_rect3, 2)
        text_surface3 = base_font.render(email, True, (255, 255, 255))
        screen.blit(text_surface3, (input_rect3.x + 5, input_rect3.y + 5))
        input_rect3.w = max(140, text_surface3.get_width() + 10)

        check_button.draw()
        back_button.draw()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.MOUSEBUTTONDOWN:
                if check_button.rect.collidepoint(event.pos):
                    pat = "^[a-zA-Z0-9-_]+@[a-zA-Z0-9]+\.[a-z]{1,3}$"
                    if len(username) >= 3 and len(password) >= 6 and re.match(pat,email):
                        result = create_user(str(username), str(password), str(email))
                        if result == 'valid':
                            print('valid!')
                            return username
                        else:
                            info2 = 'User with the same username or email already exists.'
                            action = 2
                    else:
                        info = 'invalid'
                        action = 1
                        print('invalid')
                elif back_button.rect.collidepoint(event.pos):
                    menu()
                elif input_rect1.collidepoint(event.pos):
                    active1 = True
                    active2, active3 = False, False
                    color1 = color_active
                    color2, color3 = color_passive, color_passive
                elif input_rect2.collidepoint(event.pos):
                    active2 = True
                    active1, active3 = False, False
                    color2 = color_active
                    color1, color3 = color_passive, color_passive
                elif input_rect3.collidepoint(event.pos):
                    active3 = True
                    active1, active2 = False, False
                    color3 = color_active
                    color1, color2 = color_passive, color_passive

            elif event.type == pygame.KEYDOWN:
                if active1:
                    username = handle_keydown(event, username)
                elif active2:
                    password = handle_keydown(event, password)
                elif active3:
                    email = handle_keydown(event, email)

        systems_text = font2.render(' ' + info, True, 'brown4')
        systems_text2 = font.render(' ' + info2, True, 'brown4')

        if action == 1:
            screen.blit(systems_text, (550, 510))
        elif action == 2:
            screen.blit(systems_text2, (250, 520))
        
        pygame.display.update()
        clock.tick(60)
        
def login():
    clock = pygame.time.Clock()
    font2 = pygame.font.Font('holly.ttf', 42)
    font3 = pygame.font.Font('holly.ttf', 72)
    base_font = pygame.font.Font(None, 32)
    font = pygame.font.Font('holly.ttf', 22)
    username = ''
    password = ''
    info = ''

    print_surface = pygame.image.load('graphics/print.png').convert_alpha()
    print_surface = pygame.transform.scale(print_surface, (1300, 800))

    usernamef_surface = pygame.image.load('graphics/usernamef.png').convert_alpha()
    passwordf_surface = pygame.image.load('graphics/passwordf.png').convert_alpha()
    log_in_text = font3.render('LOG IN' , True, 'brown4')
        
    check_img = pygame.image.load('graphics/check.png').convert_alpha()
    check_button = button.Button(700, 500, check_img, 0.5)
    back_img = pygame.image.load('graphics/back.png').convert_alpha()
    back_button = button.Button(900, 500, back_img, 0.5)

    input_rect1 = pygame.Rect(200, 200, 140, 32)
    input_rect2 = pygame.Rect(200, 250, 140, 32)

    color_active = pygame.Color('lightcyan3')
    color_passive = pygame.Color('gray15')
    color1, color2 = color_passive, color_passive

    active1, active2 = False, False

    while True:
        screen.fill((119, 147, 60))
        screen.blit(print_surface, (0,0))
        screen.blit(usernamef_surface, (200, 240))
        screen.blit(passwordf_surface, (200, 340))
        username_text = font.render('Must be three or more characters:' , True, 'lightcyan3')
        screen.blit(username_text, (200, 200))
        password_text = font.render('Must be 6 or more characters:' , True, 'lightcyan3')
        screen.blit(password_text, (200, 300))
        screen.blit(log_in_text, (480, 60))

        input_rect1.topleft = (500, 250)
        pygame.draw.rect(screen, color1, input_rect1, 2)
        text_surface1 = base_font.render(username, True, (255, 255, 255))
        screen.blit(text_surface1, (input_rect1.x + 5, input_rect1.y + 5))
        input_rect1.w = max(140, text_surface1.get_width() + 10)

        input_rect2.topleft = (500, 350)
        pygame.draw.rect(screen, color2, input_rect2, 2)
        text_surface2 = base_font.render(password, True, (255, 255, 255))
        screen.blit(text_surface2, (input_rect2.x + 5, input_rect2.y + 5))
        input_rect2.w = max(140, text_surface2.get_width() + 10)

        check_button.draw()
        back_button.draw()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()

            if event.type == pygame.MOUSEBUTTONDOWN:
                if check_button.rect.collidepoint(event.pos):
                    if checklogin_credentials(username, password):
                        print('Login successful!')
                        return username
                    else:
                        info = 'invalid'
                        print('Invalid credentials.')
                elif back_button.rect.collidepoint(event.pos):
                    menu()
                elif input_rect1.collidepoint(event.pos):
                    active1 = True
                    active2 = False
                    color1 = color_active
                    color2 = color_passive
                elif input_rect2.collidepoint(event.pos):
                    active2 = True
                    active1 = False
                    color2 = color_active
                    color1 = color_passive

            elif event.type == pygame.KEYDOWN:
                if active1:
                    username = handle_keydown(event, username)
                elif active2:
                    password = handle_keydown(event, password)

        systems_text = font2.render(' ' + info, True, 'brown4')
        screen.blit(systems_text, (550, 510))
                    
        pygame.display.update()
        clock.tick(60)
    
def settings():
    clock = pygame.time.Clock()
    base_font = pygame.font.Font(None, 32)
    back_img = pygame.image.load('graphics/back.png').convert_alpha()
    back_button = button.Button(900, 500, back_img, 0.5)
    font = pygame.font.Font('holly.ttf', 90)
    font2 = pygame.font.Font('holly.ttf', 50)

    print_surface = pygame.image.load('graphics/print.png').convert_alpha()
    print_surface = pygame.transform.scale(print_surface, (1300, 800))

    music_slider = slider.Slider((550, 270), (200, 20), initial_val=0.5, min_val=0, max_val=1)
    sound_slider = slider.Slider((550, 400), (200, 20), initial_val=0.5, min_val=0, max_val=1)
    
    while True:
        screen.fill((119, 147, 60))
        screen.blit(print_surface, (0,0))
        back_button.draw()
        settings_text = font.render('Settings', True, 'brown4')
        screen.blit(settings_text, (400, 80))
        music_text = font2.render('Music', True, 'lightcyan3')
        screen.blit(music_text, (200, 260))
        sounds_text = font2.render('Game Sounds', True, 'lightcyan3')
        screen.blit(sounds_text, (200, 390))

        music_slider.render(screen)
        sound_slider.render(screen)
        
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            elif event.type == pygame.MOUSEBUTTONDOWN:
                if back_button.rect.collidepoint(event.pos):
                    menu()
                elif music_slider.button_rect.collidepoint(event.pos):
                    active_slider = music_slider
                elif sound_slider.button_rect.collidepoint(event.pos):
                    active_slider = sound_slider
            elif event.type == pygame.MOUSEMOTION:
                if event.buttons[0] == 1 and 'active_slider' in locals():
                    active_slider.update_value((event.pos[0] - active_slider.pos[0]) / active_slider.size[0])

        pygame.display.update()
        clock.tick(60)
