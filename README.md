# Dungeon Cracker v1.1.0

Supports all versions of minecraft (infdev, alpha, beta, and full releases up to 1.17.X)
Crack a world seed from a single dungeon for worlds generated after 1.13, and just 2 dungeons for worlds generated before 1.13.

Credits to KaptainWutax, Matthew Bolan, Neil, and the good people at MC@Home for putting up with me

# Run

- Go to releases and download the jar
- Run by executing: `java -jar UniversalDungeonCracker_1.1.0.jar`

(Compiled using Java 8)

# How To Use
This app has multiple modes, Dungeon Data Mode and Dungeon Seed Mode

The mode will be selected after choosing a version and dungeon quantity.
## Dungeon Data Mode
You will need the following:
- The MC version the dungeon was generated in
- X, Y, and Z coordinates
- The Dungeon Sequence (floor pattern)
- General awareness of the biome the spawner block is in (this really only matters for 1.16+)
- Minimum 2 dungeons for a seed on versions 1.12 and below, 1 dungeon data set can be provided but you will only get a dungeon seed and not a world seed

## Dungeon Seed Mode
You will need the following:
- The MC version the dungeon was generated in
- X and Z coordinates
- The Dungeon Seed (aka Structure Seed)
- General awareness of the biome the spawner block is in (this really only matters for 1.16+)
- Minimum 2 dungeons for a seed on versions 1.12 and below, single-dungeon-seed-mode is compatible with 1.13+


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
I have many testing dungeon data sets at the bottom of src/main/java/neil/Main.java, try one out.
### Why do some versions need 2 dungeons?
Older versions are less "unique" with dungeons. Using only 1 dungeon would result in thousands of potential world seeds while 2 dungeons allow us to cross-reference this list and find a singular unique worldseed.
### I put in my dungeon and I'm sure it's right but the app says "unfortunately no seeds were found"!
Sometimes other structures like mineshafts, lava lakes, and even other dungeons can alter the way dungeons generate. Unfortunately, that dungeon will not work with this app and you will need to use a different one.


# Development

To setup your dev environment run
```md
// clean your setup
./gradlew clean
// run the app
./gradlew run
// create a release (create a {rootProject.name}-{version}.jar and {rootProject.name}-{version}.exe in build/libs)
./gradlew release
````
