import org.apache.http.client.cache.Resource;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class Main {

    public static void main(String[] args) {
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel("pizza.owl");
//        printing the information in ontology
//        model.write(System.out, "RDF/JSON");
//        model.write(System.out, "N-TRIPLES");
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +
                "PREFIX pizza: <http://www.co-ode.org/ontologies/pizza/pizza.owl#>" +
                        "SELECT * WHERE {" +
                        "?topping rdfs:subClassOf pizza:PizzaTopping ." +
                        "?topping rdfs:subClassOf ?restriction ." +
                        "?restriction rdf:type owl:Restriction ." +
                        "?restriction ?p ?o ." +
                        "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecutor = QueryExecutionFactory.create(query, model);
        try{
            ResultSet results = queryExecutor.execSelect();
            while(results.hasNext()){
                QuerySolution solution = results.nextSolution();
                Literal name = solution.getLiteral("o");
//                Resource name = solution.getResource("o");
                System.out.println(name);
            }
        } finally {
            queryExecutor.close();
        }
    }
}
