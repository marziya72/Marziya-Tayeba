import pygame
import sys
import re
import button
from pygame.locals import *
from audios import audio, load_sound
from minigame_1 import countdown

pygame.init()
screen = pygame.display.set_mode((1100,700))
pygame.display.set_caption('Rhythm Cook')
clock = pygame.time.Clock()
test_font = pygame.font.Font(None, 50)
screen.fill('skyblue3')

class Tutorial():
    def __init__(self):
        self.font = pygame.font.Font('holly.ttf', 72)
        self.font2 = pygame.font.Font('holly.ttf', 32)
        self.plate_surface = pygame.image.load('graphics/plate.png').convert_alpha()
        self.plate_surface = pygame.transform.scale(self.plate_surface, (200, 50))
        self.bg_surface = pygame.image.load('graphics/tutbg.png').convert_alpha()
        self.bg_surface = pygame.transform.scale(self.bg_surface, (730, 200))
        self.arrow_surface = pygame.image.load('graphics/arrow.png').convert_alpha()
        self.arrow_surface = pygame.transform.scale(self.arrow_surface, (300, 30))
        self.button_up_surface = pygame.image.load('graphics/button_up.png').convert_alpha()
        self.button_up_surface = pygame.transform.scale(self.button_up_surface, (120, 120))
        self.button_down_surface = pygame.image.load('graphics/button_down.png').convert_alpha()
        self.button_down_surface = pygame.transform.scale(self.button_down_surface, (120, 100))
        self.skip = pygame.image.load('graphics/skip.png').convert_alpha()
        self.skip_button = button.Button(1000, 620, self.skip, 0.35)
        self.behind_surface = pygame.image.load('graphics/behind.png').convert_alpha()
        self.behind_surface = pygame.transform.scale(self.behind_surface, (58,150))
        self.front_surface = pygame.image.load('graphics/front.png').convert_alpha()
        self.front_surface = pygame.transform.scale(self.front_surface, (58,150))
        self.button_up2_surface = pygame.image.load('graphics/button_up2.png').convert_alpha()
        self.button_up2_surface = pygame.transform.scale(self.button_up2_surface, (120, 120))
        self.button_down2_surface = pygame.image.load('graphics/button_down2.png').convert_alpha()
        self.button_down2_surface = pygame.transform.scale(self.button_down2_surface, (120, 100))
        self.event = None

    def instructions(self, carrot_surface):
        carrot_x_pos = 200
        speed = 3
        down = False
        
        while True:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    return
            screen.fill('skyblue3')
            instructions_text = self.font.render('Instructions', True, 'white')
            screen.blit(instructions_text, (400, 120))
            minigame_text = self.font.render('Catching Carrots', True, 'white')
            screen.blit(minigame_text, (340, 50))
            screen.blit(self.bg_surface, (190,210))
            tut_text = self.font2.render('A cut sound is made, the carrot is released', True, 'white')
            tut_text2 = self.font2.render('Press "E" at correct time', True, 'white')
            screen.blit(tut_text, (200, 450))
            screen.blit(tut_text2, (660, 580))
            screen.blit(self.plate_surface, (700,350))
            screen.blit(carrot_surface, (200,280))
            screen.blit(self.arrow_surface, (360,370))
            
            if self.skip_button.draw():
                countdown()

            if carrot_x_pos == 200:
                chop_sound = load_sound('chop.wav')
                chop_sound.play()
            if 200 <= carrot_x_pos <= 740:
                screen.blit(carrot_surface, (carrot_x_pos,280))
                carrot_x_pos += 10
            if carrot_x_pos == 740:
                hit_sound = load_sound('hit1.wav')
                hit_sound.play()
                down = True
            if down == True:
                screen.blit(self.button_down_surface, (740, 440))
                pygame.display.update()
                pygame.time.wait(100)
                return True
            if down == False:
                screen.blit(self.button_up_surface, (740, 420))

            pygame.display.update()
            clock.tick(60)

    def instructions2(self, onion_surface, potato_surface):
        sprite_x_pos = 210
        speed = 3
        down = False
        count = 0
        
        while count < 2:
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    return
            screen.fill('skyblue3')
            instructions_text = self.font.render('Instructions', True, 'white')
            screen.blit(instructions_text, (400, 120))
            minigame_text = self.font.render('Vegetable Show', True, 'white')
            screen.blit(minigame_text, (340, 50))
            screen.blit(self.bg_surface, (190,210))
            tut_text = self.font2.render('Canon sound made, onion or potato is released', True, 'white')
            tut_text2 = self.font2.render('Press "E" at correct time', True, 'white')
            tut_text3 = self.font2.render('Press "R" at correct time', True, 'white')
            screen.blit(tut_text, (200, 450))
            screen.blit(self.behind_surface, (858,230))
            screen.blit(self.front_surface, (800,230))
            screen.blit(self.arrow_surface, (400,370))

            skip = False
            if self.skip_button.draw():
                print('skip')
                skip = True
                return skip
  
            if sprite_x_pos == 210:
                canon_sound = load_sound('canon.mp3')
                canon_sound.play()
                down = False
            if 210 <= sprite_x_pos <= 829 and count == 0:
                screen.blit(onion_surface, (sprite_x_pos,280))
                sprite_x_pos += 10
            elif count == 1:
                screen.blit(potato_surface, (sprite_x_pos,280))
                sprite_x_pos += 10
            if sprite_x_pos >= 829:
                hit_sound = load_sound('hit1.wav')
                hit_sound.play()
                screen.blit(self.behind_surface, (858,230))
                sprite_x_pos = 210
                down = True
                if count == 0:
                    pygame.time.wait(100)
            if down == True:
                if count == 0:
                    screen.blit(self.button_down_surface, (780, 440))
                    pygame.display.update()
                    pygame.time.wait(500)
                    count+=1
                    down = False
                else:
                    screen.blit(self.button_down2_surface, (780, 440))
                    pygame.display.update()
                    pygame.time.wait(500)
                    count+=1
                    down = False
                if count >=2:
                    return True
            if down == False:
                if count == 0:
                    screen.blit(tut_text2, (660, 580))
                    screen.blit(self.button_up_surface, (780, 420))
                if count == 1:
                    screen.blit(tut_text3, (660, 580))
                    screen.blit(self.button_up2_surface, (780, 420))

            pygame.display.update()
            clock.tick(60)

        return True



