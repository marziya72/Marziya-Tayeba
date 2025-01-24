import pygame
import sys

def stage_menu():
    font = pygame.font.Font('holly.ttf', 52)
    #minigame1_img = pygame.image.load('graphics/minigame1.png').convert_alpha()
    #mingame2_img = pygame.image.load('graphics/mingame2.png').convert_alpha()
    #minigame3_img = pygame.image.load('graphics/minigame3.png').convert_alpha()
    #FinalDish_img = pygame.image.load('graphics/FinalDish.png').convert_alpha()

    #minigame1_button = Button(450, 150, minigame1_img, 0.35)
    #minigame2_button = Button(450, 270, minigame2_img, 0.35)

    run = True
    while run:
        screen.fill((191,62,255))
        stage1_text = font.render('Stage 1', True, 'brown4')
        screen.blit(stage1_text, (180, 150))
        stage2_text = font.render('Stage 2', True, 'brown4')
        screen.blit(stage2_text, (380, 150))
        stage3_text = font.render('Stage 3', True, 'brown4')
        screen.blit(stage3_text, (580, 150))
        #screen.blit(paper_surface, (310, 50))
        #screen.blit(menu_surface, (340, 60))
    
        '''if signup_button.draw():
            current_username = signup()
        if login_button.draw():
            current_username = login()
        if settings_button.draw():
            settings()
        if start_button.draw():
            if len(current_username) > 0:
                print('start')
                #start()'''

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False

        pygame.display.update()

