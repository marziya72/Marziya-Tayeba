import pygame
import button
from audios import audio, load_sound
from sys import exit

pygame.init()
screen = pygame.display.set_mode((1100,700))
pygame.display.set_caption('Rhythm Cook')
clock = pygame.time.Clock()
test_font = pygame.font.Font(None, 50)
screen.fill('skyblue3')

timestamps_global = [
    0, 2.08, 2.48,
    3.78, 5.4, 5.8,
    7.2, 9.2, 9.4, 9.6,
    10.8, 12.43, 12.86, 13.08,

    14.2, 16.2, 16.6,
    17.9, 19.6, 20,
    21.4, 23.4, 23.6, 23.8,
    25, 26.63, 27.06, 27.28,
]

def countdown():
    done = False
    secs = 3
    font = pygame.font.Font('holly.ttf', 72)
    text = font.render('{}'.format(secs), True, (255,255,255),'skyblue3')
    textRect = text.get_rect()
    textRect.center = 1100//2, 600//2

    clock = pygame.time.Clock()

    while not done:
        clock.tick(1)
        secs -= 1
        screen.fill('skyblue3')
        screen.blit(text, textRect)
        text = font.render('{}'.format(secs), True, (255,255,255),'skyblue3')
        if secs == -1:
            return True
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
        pygame.display.update()
    
def minigame1():
    audio()
    hit_sound = load_sound('hit1.wav')
    font = pygame.font.Font('holly.ttf', 32)
    sky_surface = pygame.image.load('graphics/Sky.png').convert_alpha()
    sky_surface = pygame.transform.scale(sky_surface, (1100, 500))
    ground_surface = pygame.image.load('graphics/ground.png').convert_alpha()
    ground_surface = pygame.transform.scale(ground_surface, (1100, 400))
    grass_surface = pygame.image.load('graphics/grass.png').convert_alpha()
    grass_surface = pygame.transform.scale(grass_surface, (1100, 135))
    plate_surface = pygame.image.load('graphics/plate.png').convert_alpha()
    plate_surface = pygame.transform.scale(plate_surface, (200,50))
    pause_surface = pygame.image.load('graphics/pause.png').convert_alpha()
    pause_surface = pygame.transform.scale(pause_surface, (680,400))
    gameover_surface = pygame.image.load('graphics/gameover.png').convert_alpha()
    gameover_surface = pygame.transform.scale(gameover_surface, (680,400))
    carrot_surface = pygame.image.load('graphics/carrot.png').convert_alpha()
    carrot_surface = pygame.transform.scale(carrot_surface, (100, 100))

    five_surface = pygame.image.load('graphics/five.png').convert_alpha()
    five_surface = pygame.transform.scale(five_surface, (220,50))
    four_surface = pygame.image.load('graphics/four.png').convert_alpha()
    four_surface = pygame.transform.scale(four_surface, (177,50))
    three_surface = pygame.image.load('graphics/three.png').convert_alpha()
    three_surface = pygame.transform.scale(three_surface, (135,50))
    two_surface = pygame.image.load('graphics/two.png').convert_alpha()
    two_surface = pygame.transform.scale(two_surface, (92,50))
    one_surface = pygame.image.load('graphics/one.png').convert_alpha()
    one_surface = pygame.transform.scale(one_surface, (50,50))

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
        25.51, 27.21, 27.64, 27.83
    ]

    carrot_velocities = [2200] * len(timestamps_local)
    indiv_velocities = [
        2200, 2800, 2800,  
        2800, 2100, 2100,  
        2500, 2800, 2800, 2800,
        2800, 2800, 2600, 2600,

        2200, 2800, 2800,  
        2800, 2200, 2200,  
        2800, 2800, 2800, 2800,
        2800, 2300, 2300, 2300,   
        ]
    carrot_velocities[:len(indiv_velocities)] = indiv_velocities
    
    audio_length = pygame.mixer.Sound('music3.wav').get_length()
    start_time = pygame.time.get_ticks()/ 1000
    visual_offset = -0.165
    score = 0
    miss = 5
    last_input = 0

    paused = False
    paused_time = 0

    while True:
        if not paused:
            current_time = pygame.time.get_ticks() / 1000
            elapsed_time = current_time - start_time
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_e and current_time - last_input > 0.2 and not paused:
                    last_input = current_time
                    hit = False
                    for i, timestamp in enumerate(timestamps_local):
                        if 0 <= elapsed_time - timestamp <= 0.2:
                            carrot_velocities[i] = 0
                            score += 1
                            hit_sound.play()
                            hit = True
                            break
                    if not hit:
                        miss -= 1
                            
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_v:
                    paused = not paused
                    if paused:
                        paused_time = elapsed_time
                        pygame.mixer.music.pause()
                    else:
                        pygame.mixer.music.unpause()
                        start_time = pygame.time.get_ticks() / 1000 - paused_time
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_m:
                    pygame.mixer.music.stop()
                    return
        if paused:
            screen.blit(pause_surface,(215,135))
            pygame.display.update()
            pygame.time.wait(10)
            continue

        if miss == 0:
            pygame.mixer.music.stop()
            screen.blit(gameover_surface, (215,135))
            pygame.display.update()
            pygame.time.wait(2000)
            return
        
        screen.blit(sky_surface,(0,0))
        screen.blit(ground_surface,(0,348))
        screen.blit(grass_surface,(0,265))
        screen.blit(plate_surface, (700,350))
        score_text = font.render('Score: %s / 28 ' % str(score), True, 'brown4')
        screen.blit(score_text, (780, 150))
        instruct_text = font.render('"V" to pause, "M" for menu', True, 'brown4')
        screen.blit(instruct_text, (100, 600))
        button_text = font.render('Press "E" for input', True, 'brown4')
        screen.blit(button_text, (700, 600))

        if miss == 5:
            screen.blit(five_surface, (50, 50))
        elif miss == 4:
            screen.blit(four_surface, (50, 50))
        elif miss == 3:
            screen.blit(three_surface, (50, 50))
        elif miss == 2:
            screen.blit(two_surface, (50, 50))
        elif miss == 1:
            screen.blit(one_surface, (50, 50))

        for i, timestamp in enumerate(timestamps_global):
            if elapsed_time >= timestamp:
                if carrot_velocities[i] != 0:
                    carrot_x_pos = int((elapsed_time - timestamp - visual_offset) * carrot_velocities[i]-1100)
                    if 0 <= carrot_x_pos <= 1100:
                        screen.blit(carrot_surface, (carrot_x_pos, 280))

        pygame.display.update()
        clock.tick(60)

        if elapsed_time >= audio_length:
            break

    pygame.mixer.music.stop()
    return score
