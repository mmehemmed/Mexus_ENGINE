# Mexus

Mexus is a personal 3D game prototype created using **LWJGL 2** and **Slick Utils**. It focuses on rendering OBJ models, applying Phong lighting, and supporting an ECS (Entity-Component-System) architecture. Currently, it serves as a foundation for potential game projects, including the possibility of creating a **city builder** in the future.

## Features

- **Phong Lighting**  
  Implements a basic **Phong lighting model** to add realistic light interactions with models in the scene.
  
- **OBJ Model Loading**  
  Supports loading OBJ files and applying textures for realistic 3D rendering.
  
- **Instance Rendering**  
  Allows the efficient rendering of multiple instances of objects in the game world with varying transformations.

- **Camera Controls**  
  A basic camera system allowing the player to move and look around the 3D world. Movement is controlled via the keyboard.

- **Entity-Component-System (ECS)**  
  A foundational ECS architecture where entities are composed of components, and systems process them.

## Controls

- **Camera movement**: Use the keyboard to move the camera around the scene.  
- **Toggle wireframe mode**: Press the `Z` key to switch between wireframe and filled rendering modes.

## Requirements

- **LWJGL 2**  
  The project uses **LWJGL 2** for rendering and window management.

- **Slick Utils**  
  Used for handling input events (e.g., keyboard input).

## Future Plans

Mexus is currently a work-in-progress and may evolve into a full-fledged **city builder** or other types of games. Features such as **physics** and **animations** might be added in the future to enhance the game engine.

## Setup

There are no detailed setup instructions yet. However, the project can be run directly if you have **LWJGL 2** and **Slick Utils** set up in your development environment.

## License

This project is for **personal use** only and is not meant for commercial distribution.
