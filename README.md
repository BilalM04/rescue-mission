
# Island Rescue Mission

Authors: 
- [Mohammad Bilal](bilalm14@mcmaster.ca)
- [John Cho](choj35@mcmaster.ca)
- [Ahsan Muzammil](muzammia@mcmaster.ca)

## Product Description

This product is an _exploration command center_ for the [Island](https://ace-design.github.io/island/) serious game. 

- The `ca.mcmaster.se2aa4.island.team107.Explorer` class implements the command center, used to compete with the others.
- The `ca.mcmaster.se2aa4.island.team107.Runner` class allows one to run the command center on a specific map.

### Strategy Description

The exploration approach utilizes a comprehensive grid search technique to methodically scan the entire island, identifying inlets and locating the emergency site.

### Sample Execution
Here is a graphical representation of a sample execution using the `./maps/map10.json` map. <br/>

<div align="center">
  
https://github.com/BilalM04/rescue-mission/assets/77511892/ceb74a5f-1175-4e9d-b14e-3c2e6ecd5d72

</div>

## How to Compile and Run

### Compiling the project:

```
bilal@mohd % mvn clean package
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.960 s
[INFO] Finished at: 2024-01-20T18:26:43-05:00
[INFO] ------------------------------------------------------------------------
bilal@mohd % 
```

This creates one jar file in the `target` directory, named after the team identifier.

As the project is intended to run in the competition arena, this jar is not executable. 

### Run the project

The project is not intended to be started by the user, but instead to be part of the competition arena. However, one might one to execute their command center on local files for testing purposes.

To do so, we ask maven to execute the `Runner` class, using a map provided as parameter:

```
bilal@mohd % mvn exec:java -q -Dexec.args="./maps/map03.json"
```

It creates three files in the `outputs` directory:

- `_pois.json`: the location of the points of interests
- `Explorer_Island.json`: a transcript of the dialogue between the player and the game engine
- `Explorer.svg`: the map explored by the player, with a fog of war for the tiles that were not visited.
