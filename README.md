Deprecated, use: https://github.com/KaptainWutax/SeedCracker

# Dungeon Cracker

Support all versions of minecraft (1.16 incluede), able to crack a world seed from a single dungeon (or 2 for 1.13-)

Credits to KaptainWutax and Matthew Bolan
# Run on google colab
Dungeon Cracker : [![Open In Colab](https://colab.research.google.com/assets/colab-badge.svg)](https://colab.research.google.com/github/hube12/DungeonCracker/blob/master/Dungeon_cracker.ipynb)
# Run 

- Go in release and download the appropriate jar

- Go in a console and type: `java -jar <jar>`



READ the README:


```
Support beta world with 1.2beta, 1.8 to 1.12, for those you need a second dungeon to cross reference the structure seed or another structure.
Support 1.14, 1.15 and 1.16 with normal reversal to before nextLong (random worldSeed) if not use biomes, or contact me.

You can use the below .exe or the python file to make it from an image, orientation should be North UP (-Z), East RIGHT (+X) on your image
( so click on the .exe, it will open a console prompt and a windows to select a file, choose the image (oriented NORTH upward) then do column by column from left to right by clicking right click for mossy and left for cobble, when you dont know use middle click)
It will give ya a sequence at the end ( the size of the dungeon should be 7*7 at minimum if less then you forgot to clear the walls)

Sequence from -x to +x and at each step -z +z

Go in a console and type: java -jar DungeonCracker14-0.1.0.jar / java -jar DungeonCracker1.16.jar
Enter the X Y and Z of the spawner (use the looking at or Targeted block line in the F3 menu)
then enter the sequence you previously got with the .exe

Before 1.14 use twice the jar for the version, get the dungeons seed and the coordinates in a text file then use the FindStructureSeedFromDungeons-0.1.0.jar to get the common structure seed
```
