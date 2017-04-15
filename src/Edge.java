import javalib.impworld.WorldScene;
import javalib.worldimages.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

// represents an Edge in a graph
// represnts a Wall in a maze
class Edge<T> {
  Vertex from;
  Vertex to;
  int weight;
  Utils utils;

  // constructor
  Edge(Vertex from, Vertex to, int weight) {
    utils = new Utils();
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  @Override
  // overrides the toString method and returns a string representation of an edge
  public String toString() {
    return "(" + this.from + " " + this.to + " " + this.weight + ")";
  }

  @Override
  // overrides the hashCode method for generic purposes
  public int hashCode() {
    return (this.from.hashCode() * 100000) + this.to.hashCode();
  }

  @Override
  // overrides the equals method for generic purposes
  public boolean equals(Object other) {
    if (other instanceof Edge) {
      Edge that = (Edge) other;
      return ((this.from.equals(that.from) && this.to.equals(that.to))
        || (this.from.equals(that.to) && this.to.equals(that.from)));
    } else {
      return false;
    }
  }

  // returns the difference in weights between two edges
  int compareWeights(Edge other) {
    int difference = this.weight - other.weight;
    if (difference > 0) {
      return 1;
    } else if (difference < 0) {
      return -1;
    } else {
      return difference;
    }
  }

  // checks if this edge causes a cycle
  boolean causesCycle(HashMap<Vertex, Vertex> reps) {
    return this.utils.cycle(reps, this.from, this.to);
  }

  // adds the vertices in this edge to an ArrayList
  void addVertices(ArrayList<Vertex> vertices) {
    this.utils.addNoDupes(vertices, this.from);
    this.utils.addNoDupes(vertices, this.to);
  }

  // draws an empty space to remove a wall from the given WorldSize
  void drawEdge(WorldScene ws, int cellSize) {
    int direction = this.from.direction(this.to);
    Color gray = new Color(192, 192, 192);
    if (direction == Vertex.NORTH) {
      ws.placeImageXY(new RectangleImage(cellSize - 1, 2, "solid", gray),
          (this.from.x * cellSize) + (cellSize / 2) + 1,
          (this.from.y * cellSize));
    } else if (direction == Vertex.SOUTH) {
      ws.placeImageXY(new RectangleImage(cellSize - 1, 2, "solid", gray),
          (this.from.x * cellSize) + (cellSize / 2) + 1,
          (this.from.y * cellSize) + (cellSize));
    } else if (direction == Vertex.WEST) {
      ws.placeImageXY(new RectangleImage(2, cellSize - 1, "solid", gray),
          (this.from.x * cellSize),
          (this.from.y * cellSize) + (cellSize / 2) + 1);
    } else {
      ws.placeImageXY(new RectangleImage(2, cellSize - 1, "solid", gray),
          (this.from.x * cellSize) + cellSize,
          (this.from.y * cellSize) + (cellSize / 2) + 1);
    }
  }

  boolean containsVertex(Vertex v) {
    return this.from.equals(v) || this.to.equals(v);
  }

  Vertex getOther(Vertex v) {
    if (v.equals(this.from)) {
      return this.to;
    } else {
      return this.from;
    }
  }
}
