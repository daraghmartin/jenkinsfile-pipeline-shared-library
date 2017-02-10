package cloud.abd.pipeline

class Helper {

  static String test() {
    return "test "
  }

  //this works in normal groovy but fails here due to major bug
  //https://issues.jenkins-ci.org/browse/JENKINS-26481
  //will make things easier when it works of course
  static String testIterator() {
    def r = ""
    ['test', 'it'].each {
      r = r + " " + it
    }
    return r
  }

  static postIt(url, payload, proxy_host, proxy_port = 8080) {
    def proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_host, proxy_port));
    url = new URL(url)
    def connection = url.openConnection(proxy)

    connection.setRequestMethod("POST")
    connection.doOutput = true

    def writer = new OutputStreamWriter(connection.outputStream)
    writer.write(payload)
    writer.flush()
    writer.close()

    connection.connect()

    println connection.content.text
    println connection.getResponseCode()
    println connection.content
    
  //  return connection.content
  }


}
