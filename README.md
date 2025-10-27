# Square - Dodge & Survive

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)](https://www.java.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Release](https://img.shields.io/badge/Release-v1.0.0-orange)

> A fast-paced arcade dodge game built in Java where survival is everything. Dodge falling enemies, collect power-ups, and compete for the highest score!

Built during 11th grade as a passion project to learn game development and Java programming.

---

## 🎮 About The Game

Square is an arcade-style survival game where you control a customizable square character avoiding endless waves of falling enemies. The longer you survive, the harder it gets! Collect coins to unlock new skins, grab power-ups for temporary advantages, and climb the leaderboards.

### ✨ Key Features

- **🎯 Intense Gameplay** - Fast-paced dodging action with increasing difficulty
- **👤 Account System** - Create accounts to save progress and track high scores
- **🎨 30+ Unique Skins** - Unlock characters ranging from superheroes to Minecraft creepers
- **💰 In-Game Economy** - Earn coins by surviving and spend them in the shop
- **⚡ Power-Ups**
  - **Boost** - Blast through enemies while the screen goes psychedelic
  - **Invisibility** - Phase through enemies temporarily
  - **Auto Gun** - Fire bullets automatically to clear your path
- **🎵 Dynamic Soundtrack** - Multiple music tracks that change each game
- **🎮 Dual Control Schemes** - Play with keyboard (WASD/Arrows) or mouse
- **💾 Persistent Saves** - Your progress, coins, and unlocks are saved locally

---

## 📸 Screenshots

![Main_menu](https://github.com/user-attachments/assets/71298616-3bc5-4148-9ece-746551f7d144)
Main Menu

![Gameplay](https://github.com/user-attachments/assets/2f2af680-0550-4d2e-b32d-47aa1027c6ba)
Gameplay

![Customization](https://github.com/user-attachments/assets/88c48a8c-d321-4ecc-9856-b094f588ca27)
Customization

![Invisibility_powerup](https://github.com/user-attachments/assets/f4ed73f7-4034-4458-bd4c-0c9d24885159)
Invisibility Powerup

![Machinegunner_powerup](https://github.com/user-attachments/assets/af755f83-7029-4217-9985-d8d1564af531)
Machinegunner Powerup

![Boost_powerup](https://github.com/user-attachments/assets/912c8753-faeb-42a8-bc52-899a5133e961)
Boost Powerup



---

## 🚀 Getting Started

### Prerequisites

- Java Runtime Environment (JRE) 8 or higher
- Windows, macOS, or Linux

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/TaherJoudeh/Square.git
   cd Square
   ```

2. **Compile the game**
   ```bash
   javac -d bin src/game/*.java src/gameInputs/*.java
   ```

3. **Run the game**
   ```bash
   java -cp bin game.Gameplay
   ```

### Quick Start (Pre-compiled)

*If you want to provide a JAR file:*

1. Download `Square.jar` from [Releases](https://github.com/TaherJoudeh/Square/releases)
2. Double-click the JAR file or run:
   ```bash
   java -jar Square.jar
   ```

---

## 🎯 How To Play

### Controls

**Keyboard Mode:**
- `W/↑` - Move Up
- `A/←` - Move Left  
- `S/↓` - Move Down
- `D/→` - Move Right
- `P` - Pause
- `C` - Show coins (during game)
- `ESC` - Exit

**Mouse Mode:**
- Move your mouse to control your character
- `ESC` - Exit

### Gameplay Tips

1. **Start Small** - Focus on dodging rather than collecting coins at first
2. **Use Power-Ups Wisely** - They spawn randomly, so grab them when safe
3. **Watch the Patterns** - Some enemies move horizontally, others fall straight down
4. **Earn Coins** - You get coins based on your survival time
5. **Unlock Skins** - Save up for rare skins that cost more!

---

## 🛠️ Technical Details

### Built With

- **Language:** Java
- **GUI Framework:** Swing
- **Audio:** Java Sound API
- **Graphics:** Java 2D Graphics

### Project Structure

```
Square/
├── src/
│   ├── game/
│   │   ├── Gameplay.java      # Main game loop and rendering
│   │   ├── Accounts.java      # User account management
│   │   ├── GameObject.java    # Base class for game entities
│   │   ├── Handler.java       # Entity manager
│   │   ├── Power.java         # Power-up system
│   │   ├── AudioPlayer.java   # Sound management
│   │   ├── Customize.java     # Skin system
│   │   └── ...
│   └── gameInputs/
│       ├── KeyInput.java      # Keyboard controls
│       ├── MouseInput.java    # Mouse controls
│       └── Buttons.java       # UI button system
├── Data/
│   ├── Accounts/              # Saved user accounts
│   ├── Audio/                 # Sound effects & music
│   ├── Customize/             # Character skins
│   └── Powers/                # Power-up graphics
└── README.md
```

### Architecture Highlights

- **Game Loop:** Custom tick-render cycle running at 60 FPS
- **Collision Detection:** Rectangle-based bounding box system
- **State Machine:** Multiple game states (Menu, Game, Customize, etc.)
- **Persistence:** Text-based file storage for accounts and progress

---

## 🎨 Customization

The game includes 30 unique skins with varying prices:

| Price | Examples |
|-------|----------|
| Free | Blue Square, Red Square |
| 300-500 | Camo, Rainbow, Happy Blue |
| 1000-2000 | Microsoft, Ninja, Thief Mask |
| 2500-4000 | Emo Boy, Mustache, Diamond |
| 5000-6000 | Batman, Creeper, Dark |
| 7000+ | Developer (Premium), ASQ (Ultimate) |

---

## 🐛 Known Issues

- File paths use Windows-style backslashes (affects macOS/Linux compatibility)
- No multiplayer functionality
- Screen resolution fixed at 1366x770

---

## 📝 Development Journey

This game was created during my 11th grade year as a way to learn:
- Object-oriented programming in Java
- Game development fundamentals
- File I/O and data persistence
- Audio and graphics programming
- State management and game loops
- The skins/cosmetics for the player are made by me and my classmates

It represents countless hours of learning, debugging, and iteration. While the code may not be perfect, I'm proud of what I built and what I learned in the process!

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Taher Joudeh**
- GitHub: [@TaherJoudeh](https://github.com/TaherJoudeh)
- Made with ☕ and determination

---

## ⭐ Support

If you enjoyed this game or found the code helpful for learning, please consider giving it a star! It means a lot for a high school project.

**Found a bug?** Open an issue!  
**Want to contribute?** Pull requests are welcome!

---

<p align="center">Made in 11th Grade | 2019</p>
