package brave.jersey;

import javax.ws.rs.core.Feature;

import brave.Tracing;

import brave.jaxrs2.TracingFeature;

import zipkin.Span;
import zipkin.reporter.AsyncReporter;
import zipkin.reporter.Reporter;
import zipkin.reporter.okhttp3.OkHttpSender;

final class generateTrace {

  private static final String ZIPKIN_SERVER_URL = "http://ZIPKIN-SEVER:QUERY-PORT/api/v1/spans";

  private static Tracing tracing = null;
  private static Reporter<Span> reporter = null;
  private static OkHttpSender sender = null;

  private static Feature feature = null;

  generateTrace(String serviceName) {

    sender = OkHttpSender.create(ZIPKIN_SERVER_URL);
    reporter = AsyncReporter.builder(sender).build();

    tracing = Tracing.newBuilder()
             .localServiceName(serviceName)
             .reporter(reporter)
             .build();

    feature = TracingFeature.create(tracing);

  }

  public static Feature getTrace() {
    return feature;
  }

}
