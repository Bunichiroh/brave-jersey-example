package brave.jersey;

import java.net.URI;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class serviceBack {

  static final String backhost = "localhost";
  static final String ht = "{\"Time\":\"";
  static final String tt = "\"}";

  @GET
  public String printDate() {
    Date dt = new Date();
    return ht+dt.toString()+tt;
  }

  public static void main(String[] args) {
    generateTrace gtrace = new generateTrace("brave-jersey-back");

    ResourceConfig rc = new ResourceConfig()
        .register(gtrace.getTrace())
        .register(serviceBack.class);

    GrizzlyHttpServerFactory.createHttpServer(URI.create("http://"+backhost+":9001"), rc);
  }
}
