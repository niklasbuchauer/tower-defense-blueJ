# 🏰 Tower Defense

A Java-based Tower Defense game built with BlueJ, inspired by Bloons TD 6. Defend your base across 100 increasingly difficult waves using four unique towers — each with five upgrade levels that fundamentally change how they play.

---

## 🎮 How to Play

| Action | Input |
|---|---|
| Place tower | Left-click on buildable tile |
| Select tower / open info panel | Left-click on existing tower |
| Upgrade tower | Click upgrade button in info panel |
| Close info panel | Right-click |
| Select tower type | Keys `1` `2` `3` `4` |
| Start game | `Enter` or click |

**Rules:**
- You start with **150$** and **20 lives**
- Enemies that reach the end cost you a life
- Killing enemies earns you gold
- You can place a **maximum of 5 towers per type**
- Towers cannot overlap

---

## 🗼 Towers

### 🔵 Basic Tower — 60$
Your all-round starter tower. Becomes a powerhouse at higher levels.

| Level | Upgrade | Cost |
|---|---|---|
| 2 | Faster fire rate (+25%) | 100$ |
| 3 | Piercing shots (hits 2–3 enemies) | 250$ |
| 4 | Explosive shots (AoE splash) | 600$ |
| 5 | Elite ammo (larger explosions + pierce + massive boss damage) | 1.500$ |

### 🟣 Sniper Tower — 100$
Long-range single-target specialist. Unlocks stealth detection and devastating one-shots.

| Level | Upgrade | Cost |
|---|---|---|
| 2 | Maximum range (covers nearly the full map) | 150$ |
| 3 | Detects stealthed enemies | 350$ |
| 4 | Headshot (30% chance instant kill) | 900$ |
| 5 | Railgun (pierces all enemies on the line) | 2.000$ |

### 🩵 Rapid Tower — 80$
Extreme fire rate, low damage per shot. Melts through groups with poison at higher levels.

| Level | Upgrade | Cost |
|---|---|---|
| 2 | Higher fire rate | 120$ |
| 3 | Poison ammo (damage over time) | 300$ |
| 4 | Double shot (2 projectiles simultaneously) | 750$ |
| 5 | Minigun mode (extreme fire rate, stacking poison, warmup time) | 1.800$ |

### ❄️ Freeze Tower — 90$
Slows and freezes enemies. Scales into area control and a permanent blizzard field.

| Level | Upgrade | Cost |
|---|---|---|
| 2 | Longer freeze duration | 130$ |
| 3 | Area freeze (freezes multiple enemies at once) | 320$ |
| 4 | Ice splinters (damages nearby enemies when they thaw) | 800$ |
| 5 | Blizzard (permanent slow field + periodic mass freeze) | 1.900$ |

---

## 👾 Enemies

| Enemy | Description | Special |
|---|---|---|
| 🟢 Normal | Green circle, two eyes | Basic enemy |
| 🩵 Fast | Light blue with movement arrows | High speed |
| 🟠 Tank | Dark orange with metal plates | High HP |
| 🟣 Splitter | Purple with cracks | Splits into 3 fragments on death |
| ⬛ Stealth | Dark grey, semi-transparent | Invisible — requires Sniper Lvl 3+ to detect |
| 🔴 Regen | Red with green plus | Regenerates health over time |
| ⚪ Armored | Grey with silver shield | Reduces all incoming damage |
| 🧊 Ice | Ice blue with snowflakes | Slows tower fire rates while alive |
| 🟡 Healer | Gold with white cross | Heals nearby enemies every few seconds |

---

## 💀 Bosses

A boss spawns every 10 waves. Each has unique mechanics.

| Wave | Boss | Ability |
|---|---|---|
| 10 | 👁️ Der Wächter | Massive HP, very slow |
| 20 | 🛡️ Der Gepanzerte | 50% projectile damage reduction |
| 30 | 👤 Der Schatten | Turns invisible periodically |
| 40 | ❄️ Frostlord | Immune to freeze, slows towers |
| 50 | 👑 Der König | High HP, summons Normal enemies |
| 60 | 😈 Der Dämon | Gets faster as HP drops |
| 70 | 🗿 Titan | Extreme armor, very high HP |
| 80 | 💀 Der Nekromant | Summons Splitter enemies |
| 90 | 🏯 Der Kaiser | Stealth + summons + armor + berserker |
| 100 | 🌑 Der Endkönig | All abilities, 3 phases, extreme HP |

### Endkönig Phases
- **Phase 1** (100–66% HP) — High armor, summons every 4 seconds
- **Phase 2** (66–33% HP) — Faster movement, summons Splitters
- **Phase 3** (33–0% HP) — Turns stealth, extreme speed, summons all enemy types

---

## 🏗️ Architecture

```
Game (main loop, Canvas)
├── Player            — money, lives
├── WaveManager       — 100-wave spawn system
├── GameMap           — tile grid, path, build zones
├── Hud               — on-screen stats display
├── TowerInfoPanel    — tower upgrade UI
│
├── Tower (abstract)
│   ├── BasicTower    — explosive / pierce upgrades
│   ├── SniperTower   — railgun / stealth detection
│   ├── RapidTower    — minigun / poison
│   └── FreezeTower   — blizzard / ice splinters
│
├── Bullet (base)
│   ├── FreezeBullet
│   ├── ExplosionBullet
│   ├── PoisonBullet
│   ├── PierceBullet
│   ├── RailgunBullet
│   └── SniperBullet
│
├── Enemy (base)
│   ├── NormalEnemy / FastEnemy / TankEnemy
│   ├── SplitterEnemy / StealthEnemy / RegenEnemy
│   ├── ArmoredEnemy / IceEnemy / HealerEnemy
│   └── BossEnemy (base)
│       ├── WatcherBoss / ArmorBoss / ShadowBoss
│       ├── FrostlordBoss / KingBoss / DemonBoss
│       ├── TitanBoss / NecromancerBoss
│       ├── EmperorBoss / EndKingBoss
│
└── Effects
    ├── PoisonEffect
    └── IceSplinterEffect
```

---

## ⚙️ Setup

**Requirements:** Java 11+, BlueJ (or any Java IDE)

1. Clone or download the project
2. Open the folder in BlueJ
3. Compile all classes (`Ctrl+Shift+C`)
4. Right-click `Main` → `void main(String[] args)`
5. Play

---

## 🗺️ Maps

| Map | Difficulty | Description |
|---|---|---|
| GameMap | Standard | Default path layout |
| GameMapAlt | Alternative | Alternate route variant |

Map type is selected via `MapSelector` using the `MapType` enum (`EASY`, etc.).

---

## 📌 Balancing Notes

- Max **5 towers per type** to prevent spamming
- Starting gold: **150$** — enough for one tower with reserves
- Upgrade costs scale exponentially per tower type
- All towers cap at **Level 5**
- Ice enemies slow your towers' fire rate while alive
- Stealth enemies are completely ignored by all towers except Sniper Lvl 3+
