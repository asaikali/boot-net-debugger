package com.example;

import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RequestController {

  private final Environment environment;

  public RequestController( Environment environment) {
    this.environment = environment;
  }

  @GetMapping(value = "/request",produces = MediaType.TEXT_PLAIN_VALUE)

   String dump(HttpServletRequest request) {
    String result = "";

    Enumeration<String> names = request.getHeaderNames();
    while(names.hasMoreElements()) {
      String header = names.nextElement();
      result += header + ": " + request.getHeader(header) + "\n";
    }

    result += "\n\n";

    AtomicInteger counter = (AtomicInteger) request.getSession().getAttribute("counter");
    if(counter == null) {
      counter = new AtomicInteger(0);
      request.getSession().setAttribute("counter",counter);
    }


    result += "\n\ncurrent time is: " + LocalDateTime.now();
    result += "\n\nThis is request number "  + counter.incrementAndGet();
    result += "\n\nThe HttpSession ID is: " + request.getSession().getId();

    result += "\n\nThe application is running on port: " + environment.getProperty("local.server.port");
    result += "\n\nThe application is running on address: " + environment.getProperty("local.server.address");
    result += "\n\nThe servlet request.isSecure(): " + request.isSecure();
    result += "\n\nThe servlet request.getProtocol(): " + request.getProtocol();
    result += "\n\nThe servlet request.getRemoteAddr(): " + request.getRemoteAddr();
    result += "\n\nThe servlet request.getRemotePort(): " + request.getRemotePort();
    result += "\n\nThe servlet request.getLocalAddr(): " + request.getLocalAddr();
    result += "\n\nThe servlet request.getLocalPort(): " + request.getLocalPort();
    result += "\n\napp running in a Kubernetes Container: " + CloudPlatform.KUBERNETES.isActive(environment);

    return result;
  }
}
