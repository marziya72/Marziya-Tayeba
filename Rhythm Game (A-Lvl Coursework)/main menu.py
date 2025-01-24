import pygame
import sys
import re

import mysql.connector
from mysql.connector import Error

db_config = {
    'host': 'DESKTOP-K3LTK42',
    'database': 'GameData',
    'user': 'username',
    'password': 'password'
}
def create_connection():
    try:
        connection = mysql.connector.connect(**db_config)
        if connection.is_connected():
            return connection
    except Error as e:
        print(f"Error: {e}")
        return None

def create_user(username, password):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "INSERT INTO users (username, password) VALUES (%s, %s)"
            cursor.execute(query, (username, password))
            connection.commit()
            print("User created successfully!")
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()

def check_credentials(username, password):
    connection = create_connection()
    if connection:
        try:
            cursor = connection.cursor()
            query = "SELECT * FROM users WHERE username = %s AND password = %s"
            cursor.execute(query, (username, password))
            result = cursor.fetchone()
            return result is not None
        except Error as e:
            print(f"Error: {e}")
        finally:
            cursor.close()
            connection.close()
    return False

pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption('Main Menu')


class Button():
    def __init__(self, x, y, image, scale):
        width = image.get_width()
        height = image.get_height()
        self.image = pygame.transform.scale(image, (int(width*scale), int(height*scale)))
        self.rect = self.image.get_rect()
        self.rect.topleft = (x, y)
        self.clicked = False

    def draw(self):
        action = False

        pos = pygame.mouse.get_pos()
        if self.rect.collidepoint(pos):
            if pygame.mouse.get_pressed()[0] == 1 and not self.clicked:
                self.clicked = True
                action = True
        elif pygame.mouse.get_pressed()[0] == 0:
            self.clicked = False

        screen.blit(self.image, (self.rect.x, self.rect.y))

        return action


def handle_keydown(event, user_text):
    if event.key == pygame.K_BACKSPACE:
        user_text = user_text[:-1]
    else:
        user_text += event.unicode
    return user_text


def menu():
    username = ''
    font = pygame.font.Font('holly.ttf', 32)
    signup_img = pygame.image.load('graphics/signup.png').convert_alpha()
    login_img = pygame.image.load('graphics/login.png').convert_alpha()
    settings_img = pygame.image.load('graphics/settings.png').convert_alpha()
    start_img = pygame.image.load('graphics/start.png').convert_alpha()
    menu_surface = pygame.image.load('graphics/menu.png').convert_alpha()
    menu_surface = pygame.transform.scale(menu_surface, (400, 80))
    paper_surface = pygame.image.load('graphics/paper.png').convert_alpha()
    paper_surface = pygame.transform.scale(paper_surface, (450, 600))

    signup_button = Button(450, 150, signup_img, 0.35)
    login_button = Button(450, 270, login_img, 0.35)
    settings_button = Button(450, 390, settings_img, 0.35)
    start_button = Button(450, 510, start_img, 0.35)

    run = True
    while run:
        screen.fill((119, 147, 60))
        screen.blit(paper_surface, (310, 50))
        screen.blit(menu_surface, (340, 60))
        user_text = font.render('User: ' + username, True, 'brown4')
        screen.blit(user_text, (780, 150))
                              

        if signup_button.draw():
            username = signup()
        if login_button.draw():
            print('LOG IN')
        if settings_button.draw():
            settings()
        if start_button.draw():
            if len(username) > 0:
                start()

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False

        pygame.display.update()


def signup():
    clock = pygame.time.Clock()
    base_font = pygame.font.Font(None, 32)
    temp_username = ''
    temp_password = ''
    temp_email = ''

    usernamef_surface = pygame.image.load('graphics/usernamef.png').convert_alpha()
    passwordf_surface = pygame.image.load('graphics/passwordf.png').convert_alpha()
    emailf_surface = pygame.image.load('graphics/emailf.png').convert_alpha()

    check_img = pygame.image.load('graphics/check.png').convert_alpha()
    check_button = Button(700, 500, check_img, 0.5)
    back_img = pygame.image.load('graphics/back.png').convert_alpha()
    back_button = Button(900, 500, back_img, 0.5)

    input_rect1 = pygame.Rect(200, 200, 140, 32)
    input_rect2 = pygame.Rect(200, 250, 140, 32)
    input_rect3 = pygame.Rect(200, 300, 140, 32)

    color_active = pygame.Color('lightskyblue3')
    color_passive = pygame.Color('gray15')
    color1, color2, color3 = color_passive, color_passive, color_passive

    active1, active2, active3 = False, False, False

    while True:
        screen.fill((119, 147, 60))

        screen.blit(usernamef_surface, (200, 190))
        screen.blit(passwordf_surface, (200, 290))
        screen.blit(emailf_surface, (200, 390))

        input_rect1.topleft = (500, 200)
        pygame.draw.rect(screen, color1, input_rect1, 2)
        text_surface1 = base_font.render(temp_username, True, (255, 255, 255))
        screen.blit(text_surface1, (input_rect1.x + 5, input_rect1.y + 5))
        input_rect1.w = max(140, text_surface1.get_width() + 10)

        input_rect2.topleft = (500, 300)
        pygame.draw.rect(screen, color2, input_rect2, 2)
        text_surface2 = base_font.render(temp_password, True, (255, 255, 255))
        screen.blit(text_surface2, (input_rect2.x + 5, input_rect2.y + 5))
        input_rect2.w = max(140, text_surface2.get_width() + 10)

        input_rect3.topleft = (500, 400)
        pygame.draw.rect(screen, color3, input_rect3, 2)
        text_surface3 = base_font.render(temp_email, True, (255, 255, 255))
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
                    print('CHECK')
                    checki = True
                    pat = "^[a-zA-Z0-9-_]+@[a-zA-Z0-9]+\.[a-z]{1,3}$"
                    if len(temp_username) >= 3 and len(temp_password) >= 6 and re.match(pat,temp_email):
                        #create_user(temp_username, temp_password)
                        print('valid!')
                        return temp_username 
                    else:
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
                    temp_username = handle_keydown(event, temp_username)
                elif active2:
                    temp_password = handle_keydown(event, temp_password)
                elif active3:
                    temp_email = handle_keydown(event, temp_email)
                    
        pygame.display.update()
        clock.tick(60)
        
def login(username, password):
    if check_credentials(username, password):
        print('Login successful!')
    else:
        print('Invalid credentials.')
    
def settings():
    clock = pygame.time.Clock()
    base_font = pygame.font.Font(None, 32)
    back_img = pygame.image.load('graphics/back.png').convert_alpha()
    back_button = Button(900, 500, back_img, 0.5)
    font = pygame.font.Font('holly.ttf', 70)
    while True:
        screen.fill((119, 147, 60))
        back_button.draw()
        settings_text = font.render('Settings Page', True, 'brown4')
        screen.blit(settings_text, (380, 250))
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if event.type == pygame.MOUSEBUTTONDOWN:
                if back_button.rect.collidepoint(event.pos):
                    menu()
        pygame.display.update()
        clock.tick(60)



if __name__ == "__main__":
    screen = pygame.display.set_mode((1100, 700))
    menu()
    pygame.quit()
