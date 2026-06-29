# 🏰 Tower Defense

Ein in Java programmiertes Tower Defense Spiel, entwickelt mit BlueJ und inspiriert von Bloons TD 6. Verteidige deine Basis gegen 100 zunehmend schwierigere Wellen mit vier einzigartigen Türmen — jeder mit fünf Upgrade-Stufen, die seine Spielweise grundlegend verändern.

---

## 🎮 Spielanleitung

| Aktion | Eingabe |
|---|---|
| Turm platzieren | Linksklick auf ein bebaubares Feld |
| Turm auswählen / Info-Panel öffnen | Linksklick auf einen bestehenden Turm |
| Turm upgraden | Upgrade-Button im Info-Panel anklicken |
| Auswahl aufheben / Info-Panel schließen | Rechtsklick irgendwo ins Leere |
| Auswahl per Tastatur aufheben | `Esc`-Taste |
| Turmtyp zum Platzieren wählen | Tasten `1`, `2`, `3`, `4` |
| Spiel starten (im Hauptmenü) | `Enter` oder Klick |

**Regeln & Features:**
- Du startest mit **150$** und **20 Leben**.
- Gegner, die das Ende des Pfades erreichen, kosten dich Leben.
- Das Besiegen von Gegnern bringt dir Gold.
- Du kannst **maximal 5 Türme pro Typ** platzieren.
- Türme dürfen sich nicht überlappen.
- **Maus-Vorschau:** Wenn du einen Turm zum Platzieren ausgewählt hast, siehst du an der Maus seinen Reichweitenkreis und seine Kosten. Die Anzeige leuchtet **Grün**, wenn du genug Geld hast, und **Rot**, wenn es zu teuer ist.
- **Schadenszahlen:** Treffer erzeugen dynamische, fette Schadenszahlen, die über den Gegnern aufsteigen und verblassen.

---

## 🗼 Die Türme

### 🔵 Basic-Turm (Standard) — 60$
Dein Allrounder für den Spieleinstieg. Wird auf höheren Stufen zu einem echten Kraftpaket.

| Stufe | Upgrade | Kosten |
|---|---|---|
| 2 | Schnellere Feuerrate (+25%) | 100$ |
| 3 | Durchschlagende Schüsse (trifft 2–3 Gegner) | 250$ |
| 4 | Explosive Schüsse (Flächenschaden / AoE) | 600$ |
| 5 | Elite-Munition (größere Explosionen + Durchschlag + massiver Boss-Schaden) | 1.500$ |

### 🟣 Sniper-Turm (Scharfschütze) — 100$
Spezialist für hohe Reichweite und Einzelziele. Schaltet Tarnungserkennung und verheerende One-Shots frei.

| Stufe | Upgrade | Kosten |
|---|---|---|
| 2 | Maximale Reichweite (deckt fast die gesamte Karte ab) | 150$ |
| 3 | Erkennt getarnte (Stealth) Gegner | 350$ |
| 4 | Headshot (30% Chance auf Sofort-Kill bei normalen Gegnern) | 900$ |
| 5 | Railgun (Projektil durchschlägt alle Gegner in einer geraden Linie) | 2.000$ |

### 🩵 Rapid-Turm (Schnellfeuer) — 80$
Extreme Feuerrate bei geringem Schaden pro Schuss. Schmilzt Gegnergruppen auf höheren Stufen mit Giftschaden dahin.

| Stufe | Upgrade | Kosten |
|---|---|---|
| 2 | Höhere Feuerrate | 120$ |
| 3 | Gift-Munition (Schaden über Zeit) | 300$ |
| 4 | Doppelschuss (2 Projektile gleichzeitig) | 750$ |
| 5 | Minigun-Modus (extreme Feuerrate, stapelbares Gift, kurze Aufwärmzeit) | 1.800$ |

### ❄️ Freeze-Turm (Eis) — 90$
Verlangsamt und friert Gegner ein. Entwickelt sich zur perfekten Zonen-Kontrolle mit einem permanenten Blizzard.

| Stufe | Upgrade | Kosten |
|---|---|---|
| 2 | Längere Einfrier-Dauer | 130$ |
| 3 | Flächen-Einfrieren (friert mehrere Gegner gleichzeitig ein) | 320$ |
| 4 | Eissplitter (verursacht Schaden an nahen Gegnern beim Auftauen) | 800$ |
| 5 | Blizzard (permanentes Verlangsamungsfeld + periodisches Massen-Einfrieren) | 1.900$ |

---

## 👾 Gegnertypen

| Gegner | Beschreibung | Spezialfähigkeit |
|---|---|---|
| 🟢 Normal | Grüner Kreis, zwei Augen | Standard-Gegner ohne Besonderheiten |
| 🩵 Fast | Hellblau mit Bewegungspfeilen | Sehr hohe Bewegungsgeschwindigkeit |
| 🟠 Tank | Dunkelorange mit Metallplatten | Enorm hohe Lebenspunkte |
| 🟣 Splitter | Lila mit Rissen | Teilt sich beim Tod in 3 kleinere Fragmente auf |
| ⬛ Stealth | Dunkelgrau, halbtransparent | Unsichtbar — benötigt einen Sniper Stufe 3+, um angegriffen zu werden |
| 🔴 Regen | Rot mit grünem Plus | Regeneriert verloren gegangenes Leben über Zeit |
| ⚪ Armored | Grau mit silbernem Schild | Reduziert jeglichen erlittenen Schaden um einen festen Wert |
| 🧊 Ice | Eisblau mit Schneeflocken | Verlangsamt die Feuerrate aller Türme, solange er lebt |
| 🟡 Healer | Gold mit weißem Kreuz | Heilt nahestehende Gegner alle paar Sekunden |

---

## 💀 Bosse

Alle 10 Wellen erscheint ein Boss-Gegner. Jeder Boss besitzt eigene, gefährliche Mechaniken.

| Welle | Boss | Fähigkeit |
|---|---|---|
| 10 | 👁️ Der Wächter | Riesiger Lebensbalken, bewegt sich sehr langsam |
| 20 | 🛡️ Der Gepanzerte | Reduziert allen Projektilschaden pauschal um 50% |
| 30 | 👤 Der Schatten | Wird in regelmäßigen Abständen komplett unsichtbar |
| 40 | ❄️ Frostlord | Immun gegen Einfrieren; verlangsamt aktiv umliegende Türme |
| 50 | 👑 Der König | Hohe HP; beschwört kontinuierlich normale Gegner |
| 60 | 😈 Der Dämon | Wird schneller, je weniger Lebenspunkte er besitzt (Berserker) |
| 70 | 🗿 Titan | Extreme Rüstung und astronomisch hohe Lebenspunkte |
| 80 | 💀 Der Nekromant | Beschwört in regelmäßigen Abständen Splitter-Gegner |
| 90 | 🏯 Der Kaiser | Kombiniert Tarnung, Beschwörungen, Rüstung und Berserker-Wut |
| 100 | 🌑 Der Endkönig | Besitzt alle Fähigkeiten, kämpft in 3 Phasen und hat extreme HP |

### Die Phasen des Endkönigs
- **Phase 1** (100–66% HP) — Hohe Rüstung, beschwört alle 4 Sekunden Standard-Gegner.
- **Phase 2** (66–33% HP) — Erhöhtes Bewegungstempo, beschwört ununterbrochen Splitter-Gegner.
- **Phase 3** (33–0% HP) — Wird permanent getarnt (Stealth), bewegt sich extrem schnell und beschwört alle Gegnertypen durcheinander.

---

## 🏗️ Architektur & Klassenstruktur

Game (Hauptschleife, Canvas)
|-- Player             - Kontrolliert Geld und Leben
|-- WaveManager        - Steuert das Spawnsystem für alle 100 Wellen
|-- GameMap            - Kachel-Gitternetz (Tiles), Pfad- und Bauzonen
|-- Hud                - Zeichnet die Statusanzeigen auf dem Bildschirm
|-- FloatingText       - Verwaltet die aufsteigenden Schadenszahlen
|-- TowerInfoPanel     - Benutzeroberfläche für Turm-Upgrades
|
+-- Tower (abstrakt)
|   |-- BasicTower     - Explosionen / Durchschlags-Upgrades
|   |-- SniperTower    - Railgun / Tarnungserkennung
|   |-- RapidTower     - Minigun / Giftgeschosse
|   +-- FreezeTower    - Blizzard / Eissplitter-Effekte
|
+-- Bullet (Basisklasse für Projektile)
|   |-- FreezeBullet
|   |-- ExplosionBullet
|   |-- PoisonBullet
|   |-- PierceBullet
|   |-- RailgunBullet
|   +-- SniperBullet
|
+-- Enemy (Basisklasse für Gegner)
|   |-- NormalEnemy / FastEnemy / TankEnemy
|   |-- SplitterEnemy / StealthEnemy / RegenEnemy
|   |-- ArmoredEnemy / IceEnemy / HealerEnemy
|   +-- BossEnemy (Basisklasse für Bosse)
|       |-- WatcherBoss / ArmorBoss / ShadowBoss
|       |-- FrostlordBoss / KingBoss / DemonBoss
|       |-- TitanBoss / NecromancerBoss
|       +-- EmperorBoss / EndKingBoss
|
+-- Effects (Zustandseffekte)
|-- PoisonEffect
+-- IceSplinterEffect

---

## ⚙️ Installation & Start

**Voraussetzungen:** Java 11 oder neuer, BlueJ (oder eine andere Java-IDE deiner Wahl)

1. Klicke auf GitHub auf **Code -> Download ZIP** und entpacke das Projekt (oder clone es per Git).
2. Öffne den Projektordner direkt in BlueJ.
3. Kompiliere alle Klassen (z. B. unten links auf den Button **Übersetzen** oder `Strg + Umschalt + C` drücken).
4. Mache einen Rechtsklick auf die Klasse `Main` und wähle `void main(String[] args)`.
5. Viel Spaß beim Spielen!

---

## 🗺️ Karten (Maps)

| Karte | Schwierigkeit | Beschreibung |
|---|---|---|
| GameMap | Standard | Das klassische, normale Pfad-Layout |
| GameMapAlt | Alternativ | Eine Variante mit veränderten Kurven und Routen |

Der Kartentyp wird über den `MapSelector` unter Verwendung des `MapType`-Enums (`EASY`, etc.) festgelegt.

---

## 📌 Balancing-Hinweise

- **Spam-Schutz:** Maximal **5 Türme pro Typ**, um eine taktische Vielfalt zu erzwingen, statt die Map nur mit billigen Türmen vollzustellen.
- **Startkapital:** Mit **150$** kannst du dir exakt einen Turm deiner Wahl setzen und behältst noch eine kleine Geldreserve.
- **Upgrade-Skalierung:** Die Kosten für Upgrades steigen exponentiell an, bieten dafür aber mächtige Gameplay-Veränderungen auf Stufe 5.
- **Eis-Gegner:** Sollten oberste Priorität beim Abschuss haben, da sie die Angriffsgeschwindigkeit deiner Verteidigung massiv drosseln.
- **Tarnung:** Vergiss nicht, rechtzeitig einen Sniper auf Stufe 3 zu bringen, da getarnte Gegner sonst unbeschadet durch deine Linien spazieren!
