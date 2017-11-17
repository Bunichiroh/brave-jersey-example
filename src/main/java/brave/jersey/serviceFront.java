package brave.jersey;

import java.net.URI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class serviceFront {

  static final String fronthost = "localhost";
  static final String backhost = "localhost";

  final JerseyClient client;

  serviceFront(JerseyClient client) {
    this.client = client;
  }

  @GET
  public String callBackend() {
    return client.target(URI.create("http://"+backhost+":9001/api")).request()
        .buildGet().invoke().readEntity(String.class);
  }

  public static void main(String[] args) {
    generateTrace gtrace = new generateTrace("brave-jsersey-front");
    serviceFront front = new serviceFront(new JerseyClientBuilder()
        .register(gtrace.getTrace()).build());
    ResourceConfig rc = new ResourceConfig()
        .register(gtrace.getTrace())
        .register(front);

    GrizzlyHttpServerFactory.createHttpServer(URI.create("http://"+fronthost+":9000"), rc);
  }
}
