
import java.util.Iterator;



/**
 *
 * @author Antou
 */
public class Graph<T> implements BasicGraphInterface<T>{
	/*
	 * @parameter graphList represent the whole graph and edges
	 * every element in graphList is a list 
	 * each list start by a unique vertex, follow by other vertices 
	 * indicates the relations or the edges
	 * for ex: the list v1,v3,v2,v7 represent edge (v1,v3), (v1,v2), (v1,v7) 
	 */
    List<List<T>> graphList;
    List<T> vertices; //contain all of vertices or vertex values
    int edgesCount = 0;
    
    public Graph() {
        graphList = new List<>();
        vertices = new List<>();
    }
    
    
    @Override
    public boolean addVertex(T vertexLabel) {
        vertices.add(vertexLabel);
        graphList.add(new List<>());
        return true;
    }

    @Override
    public boolean addEdge(T begin, T end, double edgeWeight) {
        edgesCount++;
        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        List<T> l;
        while(verticesIt.hasNext()){
            l= graphIt.next();
            //Find the begin vertex
            if(verticesIt.next().equals(begin)){
                l.add(end);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }

    @Override
    public boolean hasEdge(T begin, T end) {
        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        while(verticesIt.hasNext()){
            if(verticesIt.next().equals(begin)){
                List<T> list = graphIt.next();
                verticesIt = list.iterator();
                while(verticesIt.hasNext()){
                    if(verticesIt.next().equals(end)){
                        return true;
                    }
                }
                return false;
            }
            graphIt.next();
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return graphList.isEmpty();
    }

    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }

    @Override
    public int getNumberOfEdges() {
        return edgesCount;
    }

    @Override
    public void clear() {
        vertices = new List<>();
        graphList = new List<>();
    }

    
    Iterable<T> getEdges(String id) {
        Array<T> arr = new Array<>();

        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        while(verticesIt.hasNext()){
            if(verticesIt.next().equals(id)){
                List<T> list = graphIt.next();
                verticesIt = list.iterator();
                while(verticesIt.hasNext()){
                    arr.addElement(verticesIt.next());
                }
                break;
            }
            graphIt.next();
        }
        
        
        
        return arr;
    }
    //remove single edge
    void removeEdge(String begin, String end) {
        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        List<T>l;
        int index = 0;
        
        while(verticesIt.hasNext()){
            l = graphIt.next();
            if(verticesIt.next().equals(begin)){
                verticesIt = l.iterator();
                while(verticesIt.hasNext()){
                    index++;
                    if(verticesIt.next().equals(end)){
                        break;
                    }
                }
                verticesIt = l.iterator();
                for (int i = 0; i < index - 1; i++) {
                    verticesIt.next();
                }
                verticesIt.remove();
                break;
            }
        }
    }
    
    //remove all edges associated with a vertex
    void removeEdges(String id) {
        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        while(verticesIt.hasNext()){
            if(verticesIt.next().equals(id)){
                graphIt.next().clear();
                return;
            }
            graphIt.next();
        }

    }

    void removeVertex(String id) {
        Iterator<T> verticesIt = vertices.iterator();
        Iterator<List<T>> graphIt = graphList.iterator();
        int index =0 ;
        while(verticesIt.hasNext()){
            index++;
            if(verticesIt.next().equals(id)){
                graphIt.remove();
                break;
            }
            graphIt.next();
        }
        verticesIt = vertices.iterator();
        for (int i = 0; i < index-1; i++) {
            verticesIt.next();
        }
        verticesIt.remove();
    }    
}
