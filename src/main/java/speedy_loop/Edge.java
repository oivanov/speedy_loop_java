/**
  implementing the Edge of the Graph. everything is public for simplicity
*/

package speedy_loop;

public class Edge {
	public Town origin;
	public Town destination;
	public int weight;
	public Edge next;

	public Edge(Town origin, Town destination, int weight) {
		this.origin 		= origin;
		this.destination	= destination;
		this.weight 		= weight;
		this.next 		= null;
	}

	public Edge next(Edge edge) {
		this.next = edge;
		return this;
	}

//	@Override
//	public String toString() {
//		return "Edge{" +
//				"origin=" + origin +
//				", destination=" + destination +
//				", weight=" + weight +
//				", next=" + next +
//				'}';
//	}

// this format is easier for debugging
  public String toString() {
    return origin + "to" + destination;
  }
}
