import pygame
import sys

screen_height = 700
screen_width = 1100

screen = pygame.display.set_mode((screen_width, screen_height))

class Slider:
     def __init__(self, pos: tuple, size: tuple, initial_val: float, min_val: int, max_val: int) -> None:
        self.pos = pos
        self.size = size
        self.min_val = min_val
        self.max_val = max_val
        self.value = initial_val

        self.button_x = int(self.pos[0] + (self.size[0] * (self.value - min_val) / (max_val - min_val)))

        self.container_rect = pygame.Rect(self.pos, self.size)
        self.button_rect = pygame.Rect(self.button_x - 5, self.pos[1], 10, self.size[1])

     def render(self, screen):
        pygame.draw.rect(screen, 'darkgray', self.container_rect)
        pygame.draw.rect(screen, 'brown4', self.button_rect)

     def update_value(self, new_value):
        self.value = max(self.min_val, min(new_value, self.max_val))
        self.button_x = int(self.pos[0] + (self.size[0] * (self.value - self.min_val) / (self.max_val - self.min_val)))
        self.button_rect.x = self.button_x
