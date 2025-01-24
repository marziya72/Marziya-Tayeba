import pygame

def audio():
    pygame.init()
    file = 'music3.wav'
    pygame.mixer.music.load(file)
    pygame.mixer.music.play()

def audio2():
    pygame.init()
    file = 'music4.wav'
    pygame.mixer.music.load(file)
    pygame.mixer.music.play()

def load_sound(file):
    return pygame.mixer.Sound(file)
