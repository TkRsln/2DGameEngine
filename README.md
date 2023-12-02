# 2DGameEngine
#### [ Please Wait for gifs to load, it may take time :c ]
## Game Chapters
### Chapter1
![Imgur Image](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/SpaceGame_chapter%201(1).gif?raw=true) 
- Goal of the Players are trying to reach space ship.
- Enemies are: White aliens and Boss_1 
- Boss_1 can jump over players and shoots twice, %60 chance he can drop heart to heal player while jumping. Cameras shake when Boss_1 falls to the ground
- White Aliens follows player and if players are in range of aliens, they shoot. %30 chance that they can drop heart when they die
- After Boss_1 die, players can reach space ship and then next scene starts

### Chapter 2
![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/game_fig_2.gif?raw=true)
- This chapter takes place in space
- Gravity of the objects are setted to zero, litle friction force has applied to stop moving objects
- Players are trying to reach Worm Hole to go Aliens' Planet

### Chapter 3
![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/game_fig_3.gif?raw=true)
- Players are trying to reach gate to stop Aliens
- Enemies in this chapter are: Flying Dragon, Monster Plant and Boss_2
- Flying Dragons can throw bouncing ball to players.
- Boss_2 throws huge rock
- After boss_2 dies, the gate opens

## Game Engine Benefits
### Camera
- Camera class extended from JPanel, "Camera" class has transform component where position and size data kept just like regular GameObject class.
- Camera checks all "GameObject"s in scene, GameObject can be drawn if GameObject's edge is inside of the render box
- Theoretically, cameras can be added to any number to games and each camera can follow the desired "GameObject", this feature lets the "Split Screen"

### GameObject
- Each "GameObject" has its own thread. Due to thread system, each Component has its own LifeCycle
- Each GameObject has its own Transform component where position and size variables of the object kept
- Developers can add their own custom components to GameObjects
- This class also contains "Destroy()" function and can take time parameter. when the function invoke it destroys the given object with given time

### Components
- Components are responsible from how objects will be rendered or how it behave or how it will interact with Physic Engine
- Developers can use default components such as "Animator", "RigidBody", "Collider" and etc.
- Components are informed GameObject status by interface functions
- Developers can use "Update()", "Start()" functions inside of the components just like Unity
#### Rigidbody Component
![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/rigidbody.gif?raw=true)
- This component lets gameobject to interect with PhysicEngine,
- Gravity, friction and bouncing factor can be applied with this component
- Rigidbody Component calculates the collision between moving and static objects.
- Developers can apply force to GameObject with this Component
#### Animator Component
![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/alien.gif?raw=true) ![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/boss_2.gif?raw=true) ![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/boss_1.gif?raw=true)
- This component reads pngies and changes apperance of the GameObjects
#### Collider Component 
- This component informs physic engine that this GameObject is physicaly interactable
#### Particle Component
![Alt text](https://github.com/TkRsln/2DGameEngine/blob/main/game_ss/spaceship.gif?raw=true)
- Creates multiple litle spheres as a particle
- With duration, particle size decrease and its color changes
- So with this component, developers can simulate basic flame

