import pygame
import button
import tutorial
from audios import audio2, load_sound
from sys import exit

pygame.init()
screen = pygame.display.set_mode((1100,700))
pygame.display.set_caption('Rhythm Cook')
clock = pygame.time.Clock()
font = pygame.font.Font(None, 50)
screen.fill('skyblue3')

timestamps_global = [
    0,  1.5,  1.75,  1.9,
    3, 4.7, 4.95,  5.2,
    6,  7.8,  8.05,  8.2,
    9,  9.7, 10.5,  11.3,

    12.5, 14, 14.3, 14.75,
    15.9, 17.4, 17.65, 17.8,
    19, 20.5, 20.75, 20.9,
    21.7, 22.6, 23.5, 24.3
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
                pygame.quit( )
                exit()
        pygame.display.update()


def minigame2():
    audio2()
    screen.fill('skyblue3')
    hit_sound = load_sound('hit1.wav')
    font = pygame.font.Font('holly.ttf', 32)
    plate_surface = pygame.image.load('graphics/plate.png').convert_alpha()
    plate_surface = pygame.transform.scale(plate_surface, (200,50))
    bg_surface = pygame.image.load('graphics/bg2.png').convert_alpha()
    bg_surface = pygame.transform.scale(bg_surface, (1100,700))
    canon_surface = pygame.image.load('graphics/canon.png').convert_alpha()
    canon_surface = pygame.transform.scale(canon_surface, (350,450))
    onion_surface = pygame.image.load('graphics/onion.png').convert_alpha()
    onion_surface = pygame.transform.scale(onion_surface, (55,60))
    potato_surface = pygame.image.load('graphics/potato.png').convert_alpha()
    potato_surface = pygame.transform.scale(potato_surface, (58,70))
    knife_surface = pygame.image.load('graphics/knife.png').convert_alpha()
    knife_surface = pygame.transform.scale(knife_surface, (100,100))
    behind_surface = pygame.image.load('graphics/behind.png').convert_alpha()
    behind_surface = pygame.transform.scale(behind_surface, (58,150))
    front_surface = pygame.image.load('graphics/front.png').convert_alpha()
    front_surface = pygame.transform.scale(front_surface, (58,150))

    pause_surface = pygame.image.load('graphics/pause.png').convert_alpha()
    pause_surface = pygame.transform.scale(pause_surface, (680,400))
    gameover_surface = pygame.image.load('graphics/gameover.png').convert_alpha()
    gameover_surface = pygame.transform.scale(gameover_surface, (680,400))
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

    sprite_x_pos = 835
    sprite_y_pos = 410
    sprite_speed = 2200

    timestamps_local = [
        0.7, 2.3, 2.75, 2.9,
        3.9, 5.5, 6.0, 6.15,
        7.2, 8.8, 9.25, 9.4,
        10.03, 10.8, 11.6, 12.4,

        13.6, 15.2, 15.6, 15.8,
        16.7, 18.4, 18.85, 19,
        20, 21.6, 22.05, 22.2,
        22.8, 23.6, 24.5, 25.2
        ]

    potato_list = [2,5,6,8,10,12,13,16,19,20,21,25,27,29]
    onion_list = [0,1,3,4,7,9,11,14,15,17,18,22,23,24,26,28,30,31]

    sprite_velocities = [7000] * 32
    indiv_velocities = [
        7000, 5000, 4500, 4500,  
        5700, 4600, 4600, 4600, 
        4000, 5000, 4000, 4000,
        4600, 4600, 4500, 4500,

        4400, 4100, 4000, 4500,  
        5300, 4200, 4100, 4100, 
        5000, 4200, 4200, 4200,
        4500, 4500, 5000, 5000,   
        ]
    sprite_velocities[:len(indiv_velocities)] = indiv_velocities
    
    audio_length = pygame.mixer.Sound('music4.wav').get_length()
    start_time = pygame.time.get_ticks()/ 1000
    visual_offset = -0.165
    score = 0

    miss = 5
    last_input = 0

    paused = False
    paused_time = 0
    ignore = False

    while True:
        if not paused:
            current_time = pygame.time.get_ticks() / 1000
            elapsed_time = current_time - start_time
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                pygame.quit()
                exit()
                  
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_e and current_time - last_input > 0.0 and not paused:
                    last_input = current_time
                    hit = False
                    for i, timestamp in enumerate(timestamps_local):
                        if 0 <= elapsed_time - timestamp <= 0.3 and i not in potato_list:
                            sprite_velocities[i] = 0
                            score += 1
                            hit_sound.play()
                            hit = True
                            break
                    if not hit:
                        miss -= 1

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_r and current_time - last_input > 0.2 and not paused:
                    last_input = current_time
                    hit = False
                    for i, timestamp in enumerate(timestamps_local):
                        if 0 <= elapsed_time - timestamp <= 0.3 and i not in onion_list:
                            sprite_velocities[i] = 0
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
                        start_time = pygame.time.get_ticks()/ 1000 - paused_time
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

        screen.blit(bg_surface, (0,0))
        screen.blit(behind_surface, (258,100))
        score_text = font.render('Score: %s / 32 ' % str(score), True, 'deepskyblue4')
        screen.blit(score_text, (780, 150))
        instruct_text = font.render('"V" to pause, "M" for menu', True, 'brown4')
        screen.blit(instruct_text, (100, 620))
        button_text = font.render('Press "E" for onion, "R" for potato', True, 'brown4')
        screen.blit(button_text, (700, 620))

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
                if sprite_velocities[i] != 0:
                    sprite_x_pos = 940 - int((elapsed_time - timestamp - visual_offset) * sprite_velocities[i]*0.12)
                    sprite_y_pos = 448 - int((elapsed_time - timestamp - visual_offset) * sprite_velocities[i]*0.05)
                    if -100 <= sprite_x_pos <= 1100 and i not in potato_list:
                        screen.blit(onion_surface, (sprite_x_pos,sprite_y_pos))
                    else:
                        screen.blit(potato_surface, (sprite_x_pos,sprite_y_pos))

        screen.blit(canon_surface, (750,245))
        screen.blit(front_surface, (200,100))

        pygame.display.update()
        clock.tick(60)

        if elapsed_time >= audio_length:
            break

    pygame.mixer.music.stop()
    return score



