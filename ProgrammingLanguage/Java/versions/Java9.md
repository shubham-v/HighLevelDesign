# Module System (Project Jigsaw)
Introduces modular programming to divide the JDK into smaller, 
manageable pieces and to allow developers to create modular applications.
- module-info.java file to define modules.
- Strong encapsulation of packages.
- Improved application performance and security.
```
    module com.example.myapp {
        requires java.base;
        exports com.example.myapp.utils;
    }
```

# JShell
Provides an interactive Read-Eval-Print Loop (REPL) for quickly testing Java code.
Ideal for prototyping and learning.
```
    jshell
    > int x = 10;
    > System.out.println(x * 2);
```
# HTTP/2 Client API (Incubator)
Introduces an HTTP/2 client to replace the old HttpURLConnection.
```
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://example.com"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println(response.body());
```

# Process API Enhancements
Improves the Process API to make it easier to manage and interact with operating system processes.
```
ProcessHandle currentProcess = ProcessHandle.current();
System.out.println("Process ID: " + currentProcess.pid());
```
# Enhanced Try-With-Resources
```
    AutoCloseable resource = new MyResource();
    try (resource) {
    // Use resource
    }
```

# Collection Factory Methods
Introduces factory methods to create **immutable** collections easily.
```
    List<String> list = List.of("A", "B", "C");
    Set<String> set = Set.of("X", "Y", "Z");
    Map<String, Integer> map = Map.of("key1", 1, "key2", 2);
```

# Stream API Enhancements
**takeWhile**: Takes elements as long as the condition is true.
**dropWhile**: Drops elements as long as the condition is true.
**ofNullable**: Creates a stream with a single element or an empty stream.
```
    List<Integer> numbers = List.of(1, 2, 3, 4, 5);
    numbers.stream()
           .takeWhile(n -> n < 4)
           .forEach(System.out::println);
```

#  Optional Enhancements
**ifPresentOrElse**: Executes one action if a value is present, or another action if not.
**or**: Supplies an alternative Optional.
**stream**: Converts Optional into a stream.
```
    Optional<String> optional = Optional.of("Java 9");
    optional.ifPresentOrElse(
        System.out::println,
        () -> System.out.println("Value is absent")
    );
```

# Stack-Walking API
Provides a more efficient way to traverse and analyze stack traces.
```
StackWalker walker = StackWalker.getInstance();
walker.forEach(frame -> System.out.println(frame.getClassName()));
```

# Unified JVM Logging
Introduces a unified logging framework for the JVM to make it easier to troubleshoot and debug.
Example (Run with JVM flags):
```
java -Xlog:gc*:file=gc.log
```

# Deprecation of Applet API

# Multi-Release JAR Files
Allows a single JAR file to support multiple Java versions.
- Place version-specific classes in a META-INF/versions directory.

# Miscellaneous Improvements
- Reduces memory footprint for strings by using byte arrays for Latin-1 strings.
- Allows private methods in interfaces to share code between default methods.
- Enhanced @Deprecated Annotation: Introduces forRemoval and since attributes to provide more context