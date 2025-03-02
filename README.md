# Mexus

*Mexus* is a personal 3D game engine prototype created using LWJGL 3 and Slick Utils. It focuses on rendering OBJ models, applying Phong lighting, and supporting an ECS (Entity-Component-System) architecture. Currently, it serves as a foundation for potential game projects, including the possibility of creating a city builder in the future.

## Features

### Phong Lighting  
Implements a basic Phong lighting model to add realistic light interactions with models in the scene.  

### OBJ Model Loading  
Supports loading OBJ files and applying textures for realistic 3D rendering.  

### Instance Rendering  
Allows the efficient rendering of multiple instances of objects in the game world with varying transformations.  

### Terrain Rendering
Allows for rendering terrain with custom options like Height Maps

### Camera Controls  
A basic camera system allowing the player to move and look around the 3D world. Movement is controlled via the keyboard.  

### Entity-Component-System (ECS)  
A foundational ECS architecture where entities are composed of components, and systems process them.  

## Controls

- **Camera movement:** Use the keyboard to move the camera around the scene.  
- **Toggle wireframe mode:** Press the `Z` key to switch between wireframe and filled rendering modes.  

## Requirements

- **LWJGL 3** – Used for rendering and window management.  

## Future Plans

*Mexus* is currently a work-in-progress.Features such as physics and animations might be added in the future to enhance the engine.  

## Setup

Download and use the project directly. Ensure LWJGL 3 and Slick Utils are included in your development environment.  

## License  

This project is for personal use only and is not meant for commercial distribution.  
