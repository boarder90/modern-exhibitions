
# Exhibitions Explorer

This application can be used to visualize networks of artists. 
In particular, it displays when, where and with whom artists
exhibited. The application uses the 
Database of Modern Exhibitions (DoME), 
provided by the University of Vienna, 
which was translated as a relational SQL database into a Neo4j database due to performance reasons.
The application makes use of matrix-based and node-link-based visualizations, as these are the most common ways to display networks. 
Furthermore, new networks can be created by using a simple ego network explorer.
Please note that the application might have bugs and is still being maintained by the author.





## Installation

To run the frontend please open the according ``frontend`` folder in a terminal window and
run

```bash
  npm install
```
followed by 
```bash
  ng serve
```
. To run the backend, please run 

```bash
    ./mvnw spring-boot:run
```

in the complete directory, i.e., ``modern-exhibitions``. 
You can find the connection properties to Neo4j in the ``application.properties`` file. These have to match with the neo4j database you are using!

## Neo4j

This application uses the GPLv3-licensed Neo4j Community Edition. Please download Neo4j Community Server 4.4.6 using this [link](https://neo4j.com/download-thanks/?edition=community&release=4.4.6&flavour=unix) (direct download!). Please make sure to download and install the GDS, using [the jar file of the 2.0.4 release](https://github.com/neo4j/graph-data-science/releases/tag/2.0.4).
You can load the database using [admin tools](https://neo4j.com/docs/operations-manual/current/backup-restore/restore-dump/).



## Authors
Adam Nedas



## Acknowledgements

 - [Database of Modern Exhibitions (DoME)](https://exhibitions.univie.ac.at/)
 - [Mobile Patent Suits](https://observablehq.com/@d3/mobile-patent-suits)
 - [Adjacency Matrix](https://bl.ocks.org/harrisoncramer/32bbcd8c360e6d8aa0d5b7a50725fe73)

