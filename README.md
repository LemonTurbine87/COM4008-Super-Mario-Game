# Super Mario – COM4008 CW1 Project

A Java (Greenfoot) implementation of a Super Mario-style platformer, built for the COM4008 Programming Concepts CW1 assignment.

## Module Info

* **Module:** COM4008 Programming Concepts
* **Assignment:** CW1 Project
* **Codebase:** Super Mario (Java / Greenfoot)
* **Student ID:** 22423822
* **Submission Date:** 23/08/2026

## Requirements Implemented

This project fulfills all core requirements and distinction features as a solo implementation:

| # | Requirement | Status |
| :--- | :--- | :--- |
| **1** | Tilemap loading as an array of objects, with camera scrolling as Mario moves left/right | 🟩 **Completed** |
| **2** | Mario movement (left, right, jump physics) and collision detection with tiles/blocks/powerups | 🟩 **Completed** |
| **3** | Final boss (e.g. Bowser) with movement patterns, fireballs, animation and sound | 🟩 **Completed** |

---

## Project Structure & Architecture

* **`MarioWorld.java`:** Manages the game loop, 2D array tile parsing (`LEVEL_MAP`), HUD overlays (Score, Coins, Lives, Bowser HP), and viewport camera translations.
* **`ScrollActor.java`:** Abstract base class managing Cartesian world coordinates (`worldX`, `worldY`) and dynamic screen rendering.
* **`Tile.java`:** Concrete tile implementations (`GroundTile`, `BlockTile`, `QuestionBlock`, `Coin`).
* **`Mario.java`:** Player actor driven by semi-implicit Euler integration, jump kinetics, and dual-pass AABB collision resolution.
* **`Bowser.java`:** Boss entity utilising a timer-driven finite state machine for jumping patrols, multi-frame animations, and attack routines.
* **`Fireball.java`:** Projectile entity featuring independent linear trajectories and screen-boundary culling.

---

## Controls

* **Left / Right (or A / D):** Move horizontally
* **Space / Up (or W):** Jump
* **Objective:** Traverse the stage, hit `?` question blocks from below to collect coins, and defeat Bowser by stomping his head while dodging fireballs.

---

## Documentation & Evidence

Detailed documentation for assessment criteria is available in the repository Wiki:

* [Concepts Explained](https://github.com/LemonTurbine87/COM4008-Super-Mario-Game/wiki/Concepts-Explained) – Architectural overview, requirement mappings, non-trivial bug diagnosis, and oral demo script outline.
* [Development Log](https://github.com/LemonTurbine87/COM4008-Super-Mario-Game/wiki/Development-Log) – Dated sprint logs and problem-solving records (10/08/2026 – 23/08/2026).
* [Testing Matrix](https://github.com/LemonTurbine87/COM4008-Super-Mario-Game/wiki/Testing) – Full verification table covering manual test cases across Requirements 1, 2, and 3.
