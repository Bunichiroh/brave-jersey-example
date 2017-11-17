package brave.jersey;

import java.util.Collections;
import java.util.List;

import brave.Span;
import brave.Tracer;
import brave.Tracing;

import com.google.common.collect.Lists;

public class testTrace {

  private static List<zipkin.Span> spans;

  public static void main(String[] args) {

    spans = Collections.synchronizedList(Lists.newArrayList());
//    Tracing.newBuilder().reporter(spans::add).build();
//    Tracer currentTracer = Tracing.currentTracer();

    Tracer currentTracer = Tracing.newBuilder().reporter(spans::add).build().currentTracer();
    Span cspn = currentTracer.nextSpan();

    if ( currentTracer == null ) System.out.println("currentTracer is null.");
    if ( cspn == null ) {
	System.out.println("currentTracer.currentSpan() is null.");
    } else {
    Span tempChild = currentTracer.newChild(cspn.context()).name("ManualChild").start();
    tempChild.tag("State", "Manually Created");
    tempChild.finish();

//    cspn = currentTracer.currentSpan();
//    cspn = currentTracer.nextSpan();
    cspn = tempChild;
    tempChild = currentTracer.newChild(cspn.context()).name("ManualChild").start();
    tempChild.tag("State", "Manually Created");
    tempChild.finish();

//    cspn = currentTracer.nextSpan();
    cspn = tempChild;
    tempChild = currentTracer.newChild(cspn.context()).name("ManualChild").start();
    tempChild.tag("State", "Manually Created");
    tempChild.finish();

    System.out.println("Size of Spanlist: "+spans.size());

    zipkin.Span manualChild = spans.get(0);
    zipkin.Span innerParent = spans.get(1);
    zipkin.Span outerParent = spans.get(2);

    System.out.println("Manual Child TraceID: "+manualChild.traceId);
    System.out.println("Inner Parent TraceID: "+innerParent.traceId);
    System.out.println("Outer Parent TraceID: "+outerParent.traceId);

    System.out.println("Manual Child SpanID: "+manualChild.id);
    System.out.println("Inner Parent SpanID: "+innerParent.id);
    System.out.println("Outer Parent SpanID: "+outerParent.id);

    System.out.println("Manual Child ParentID: "+manualChild.parentId);
    System.out.println("Inner Parent ParentID: "+innerParent.parentId);
    System.out.println("Outer Parent ParentID: "+outerParent.parentId);

    }

  }

}
