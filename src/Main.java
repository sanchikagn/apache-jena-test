import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.util.FileManager;

public class Main {

    public static void main(String[] args) {
        FileManager.get().addLocatorClassLoader(Main.class.getClassLoader());
        Model model = FileManager.get().loadModel("data.rdf");
//        model.write(System.out, "RDF/JSON");
//        model.write(System.out, "N-TRIPLES");
        String queryString =
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "PREFIX foaf:<http://xmlns.com/foaf/0.1/>" +
                        "SELECT * WHERE {" +
                        "?person foaf:name ?x ." +
                        "FILTER(?x = \"mano\")" +
                        "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution queryExecutor = QueryExecutionFactory.create(query, model);
        try{
            ResultSet results = queryExecutor.execSelect();
            while(results.hasNext()){
                QuerySolution solution = results.nextSolution();
                Literal name = solution.getLiteral("x");
                System.out.println(name);
            }
        } finally {
            queryExecutor.close();
        }
    }
}
