import pygame
from sys import exit

font = pygame.font.Font('holly.ttf', 32)
sky_surface = pygame.image.load('graphics/Sky.png').convert_alpha()
sky_surface = pygame.transform.scale(sky_surface, (1100, 500))
ground_surface = pygame.image.load('graphics/ground.png').convert_alpha()
ground_surface = pygame.transform.scale(ground_surface, (1100, 400))
plate_surface = pygame.image.load('graphics/plate.png').convert_alpha()
plate_surface = pygame.transform.scale(plate_surface, (200,50))

carrot_surface = pygame.image.load('graphics/carrot.png').convert_alpha()
carrot_surface = pygame.transform.scale(carrot_surface, (100, 100))
carrot_x_pos = -1100
carrot_speed = 2200

    
while True:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit( )
                exit()
                
        carrot_velocities[i] = 10

        screen.blit(sky_surface,(0,0))
        screen.blit(ground_surface,(0,350))
        screen.blit(plate_surface, (700,350))
        instruct_text = font.render('"V" to pause, "M" for menu', True, 'brown4')
        screen.blit(instruct_text, (100, 600))
        button_text = font.render('Press "E" for input', True, 'brown4')
        screen.blit(button_text, (700, 600))

        if carrot_velocities[i] != 0:
            carrot_x_pos = carrot_x_pos * carrot_velocities[i])
            if 0 <= carrot_x_pos <= 1100:
                screen.blit(carrot_surface, (carrot_x_pos, 280))

        pygame.display.update()
        clock.tick(60)














class Button():
    def __init__(self, x, y, image, scale):
        width = image.get_width()
        height = image.get_height()
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
            if pygame.mouse.get_pressed()[0] == 1 and self.clicked == False:
                self.clicked = True
                action = True 
        if pygame.mouse.get_pressed()[0] == 0:
            self.clicked = False
        
        screen.blit(self.image, (self.rect.x, self.rect.y))

        return action




def signup():
    #images/buttons
    pygame.init()
    clock = pygame.time.Clock()
    base_font = pygame.font.Font(None, 32)
    username = ''
    password = ''
    email = ''

    usernamef_surface = pygame.image.load('graphics/usernamef.png').convert_alpha()
    passwordf_surface = pygame.image.load('graphics/passwordf.png').convert_alpha()
    emailf_surface = pygame.image.load('graphics/emailf.png').convert_alpha()

    check_img = pygame.image.load('graphics/check.png').convert_alpha()
    check_button = Button(700, 500, check_img, 0.5)

    input_rect1 = pygame.Rect(200,200,140,32)
    input_rect2 = pygame.Rect(200,250,140,32)
    input_rect3 = pygame.Rect(200,300,140,32)
    
    color_active = pygame.Color('lightskyblue3')
    color_passive = pygame.Color('gray15')
    color1, color2, color3 = color_passive, color_passive, color_passive

    active1, active2, active3 = False, False, False

    screen.fill((119, 147, 60))

    active1, active2, active3 = False, False, False
    
    while True:
        mouse_pos = pygame.mouse.get_pos()
        for events in pygame.event.get():
            if events.type == pygame.QUIT:
                pygame.quit()
                sys.exit()
            if events.type == pygame.MOUSEBUTTONDOWN:
                if check_button.draw():
                    print('CHECK')
                    check_button.clicked = False
                if input_rect1.collidepoint(events.pos):
                    active1 = True
                    active2, active3 = False, False
                    color1 = color_active
                    color2, color3 = color_passive, color_passive
                elif input_rect2.collidepoint(events.pos):
                    active2 = True
                    active1, active3 = False, False
                    color2 = color_active
                    color1, color3 = color_passive, color_passive
                elif input_rect3.collidepoint(events.pos):
                    active3 = True
                    active1, active2 = False, False
                    color3 = color_active
                    color1, color2 = color_passive, color_passive
                else:
                    active1, active2, active3 = False, False, False
                    color1, color2, color3 = color_passive, color_passive, color_passive

            if events.type == pygame.KEYDOWN:
                if active1:
                    if events.key == pygame.K_BACKSPACE:
                        username = username[:-1]
                    else:
                        username += events.unicode
                elif active2:
                    if events.key == pygame.K_BACKSPACE:
                        password = password[:-1]
                    else:
                        password += events.unicode
                elif active3:
                    if events.key == pygame.K_BACKSPACE:
                        email = email[:-1]
                    else:
                        email += events.unicode


        #screen.fill((119, 147, 60))
        screen.blit(usernamef_surface,(200,190))
        screen.blit(passwordf_surface,(200,290))
        screen.blit(emailf_surface,(200,390))

        #screen.blit(check_img,(300,500))
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
        
        pygame.display.update()
        clock.tick(60)

if __name__ == "__main__":
    screen = pygame.display.set_mode((1100, 700))
    menu()
    pygame.quit()



'''
pygame.init()

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))
pygame.display.set_caption('Rhythm Cook')
screen.fill('skyblue3')
clock = pygame.time.Clock()

timestamps_global = [
    1, 2, 2.2,
    3, 4, 4.2,
    5, 6, 6.13, 6.2,
    7, 7.43, 8.13, 8.2,
    
    9, 10, 10.2,
    11, 12, 12.2,
    13, 14, 14.13, 14.2,
    15, 15.43, 16.13, 16.2
]


def countdown():
    done = False
    secs = 3
    font = pygame.font.Font('holly.ttf', 72)
    clock = pygame.time.Clock()

    while not done:
        clock.tick(1)
        secs -= 1
        screen.fill(('skyblue3'))
        text = font.render('{}'.format(secs), True, (255,255,255),'skyblue3')
        textRect = text.get_rect()
        textRect.center = 1100//2, 600//2
        screen.blit(text, textRect)
        if secs == -1:
            minigame1()
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
        pygame.display.update()

def audio():
    pygame.init()
    file = 'music2.wav'
    pygame.mixer.music.load(file)
    pygame.mixer.music.play()
    

def minigame1():
    audio()
    pygame.time.delay(200)
    font = pygame.font.Font('holly.ttf', 32)
    sky_surface = pygame.image.load('graphics/Sky.png').convert_alpha()
    sky_surface = pygame.transform.scale(sky_surface, (1100, 500))
    ground_surface = pygame.image.load('graphics/ground.png').convert_alpha()
    ground_surface = pygame.transform.scale(ground_surface, (1100, 400))
    plate_surface = pygame.image.load('graphics/plate.png').convert_alpha()
    plate_surface = pygame.transform.scale(plate_surface, (200,50))

    carrot_surface = pygame.image.load('graphics/carrot.png').convert_alpha()
    carrot_surface = pygame.transform.scale(carrot_surface, (100, 100))
    carrot_x_pos = -1100
    carrot_speed = 2200

    timestamps_local = [
        0.62, 2.47, 2.92,
        4.20, 6, 6.47,
        7.77, 9.56, 9.79, 10,
        11.26, 12.90, 13.38, 13.58,

        14.79, 16.67, 17.13,
        18.36, 20.23, 20.66,
        21.95, 23.76, 23.98, 24.23,
        25.51, 27.21, 27.64, 27.83]

    pygame.mixer.music.play()
    carrot_velocities = [2200] * len(timestamps_local)
    start_time = pygame.time.get_ticks() / 1000
    visual_offset = -0.165
    score = 0

    while True:
        elapsed_time = (pygame.time.get_ticks() / 1000) - start_time

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_e:
                    for i, timestamp in enumerate(timestamps_local):
                        if 0 <= elapsed_time - timestamp <= 0.2:
                            carrot_velocities[i] = 0
                            score += 1
                            print('yes')
                    
        screen.blit(sky_surface,(0,0))
        screen.blit(ground_surface,(0,350))
        screen.blit(plate_surface, (800,350))
        score_text = font.render('Score: %s / 28 ' % str(score), True, 'brown4')
        screen.blit(score_text, (780, 150))

        for i, timestamp in enumerate(timestamps_local):
            if elapsed_time >= timestamp:
                if carrot_velocities[i] != 0:
                    carrot_x_pos = int((elapsed_time - timestamp - visual_offset) * carrot_velocities[i] - 1100)
                    if 0 <= carrot_x_pos <= 1100:
                        screen.blit(carrot_surface, (carrot_x_pos, 280))

        pygame.display.update()
        clock.tick(60)

    

countdown()

































        
