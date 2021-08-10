# Dungeon Cracker

Supports all versions of minecraft (infdev, alpha, beta, and full releases up to 1.17.X)
Crack a world seed from a single dungeon for worlds generated after 1.13, and just 2 dungeons for worlds generated before 1.13.

Credits to KaptainWutax, Matthew Bolan, Neil, and the good people at MC@Home for putting up with me

# Run 

- Go to releases and download the jar
- Run by executing: `java -jar UniversalDungeonCracker.jar`

(Compiled using Java 8)

# How To Use

## Versions: 1.16, 1.17
- 1 Dungeon: the X Y Z of the spawner, the biome the spawner block is in, and the dungeon sequence (See below)

You will get ~10 world seeds which you can simply check in something like Amisdt or just load each of them up until you figure out which is the correct one.

## Versions: 1.13, 1.14, 1.15
- 1 Dungeon: the X Y Z of the spawner, and the dungeon sequence

You will get ~10 world seeds which you can simply check in something like Amisdt or just load each of them up until you figure out which is the correct one.

## Versions: infdev, all alpha, all beta, 1.0 through 1.12
- 2 Dungeons: the X Y Z and the dungeon sequences for both spawners.

You will get 2 dungeon seeds (technical info, you dont need this unless you know you need these) and 1 world seed.

**Note**: As of v1.0.0 the cracking code for 1.12 and earlier is very inefficient and thus is very slow. A 3700X using 16 threads takes over 12 minutes to crack using my internal test data. This should be fixed with my next release.

# Dungeon Sequences
A "Dungeon Sequence" is a pattern of 0's and 1's (maybe even 2's) based off what the dungeon floor looks like. You will "create" this manually using a simple method. Use the following image as reference:
![The sequence of this dungeon is "001011101111111111001111111011011101110111111110000110110111111"](https://i.imgur.com/eEl18Tq.png)

If the "top" of the image is north in-game, you will start at the top-left corner of the dungeon floor, a cobblestone will be '0', a mossy stone will be '1', and an unknown block will be '2' (An unknown block would be a block that was either removed or otherwise not visible).

After completing the first column, you move onto the 2nd column, and so on, until you finish and have a sequence that is 49(7x7), 63(7x9), or 81(9x9) chars long.

**REMEMBER**: There are floor blocks below the walls that generate. If you cannot see these, you will use a '2'.

**ALSO**: If you are unsure if the block is cobble or mossy, do NOT guess. If you guess wrong, the app will not produce a valid result.

# FAQ's
### Will this work with a modded world?
This app does not support modded worlds. Try if you want, but you will almost certainly not get a result.
### How can I be sure it's working properly?
There are test cases built into the app; when prompted for the version, type `test8` and it will spit out a result after a little while (1 minute on my machine).

The seed should be: -1700538326672817507

Full test options include: `test16`, `test15`, `test13`, `test8`, and `testL`. Please note that testL is *very* slow.
### Why do some versions need 2 dungeons?
Older versions are less "unique" with dungeons. Using only 1 dungeon would result in thousands of potential world seeds while 2 dungeons allow us to cross-reference this list and find a singular unique worldseed.
### I put in my dungeon and I'm sure it's right but the app says "no unique world seed was found"!
Sometimes other structures like mineshafts and even other dungeons can alter the way dungeons generate. Unfortunately, that dungeon will not work with this app and you will need to use a different one.
