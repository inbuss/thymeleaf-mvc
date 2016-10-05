thymeleaf-mvc
=============

An extension providing Thymeleaf as a view engine in MVC 1.0 (JSR371)
environments. It goes beyond a basic `ViewEngine` implementation, providing
access to named CDI beans (a feature labelled *"OPTIONAL but highly RECOMMENDED"*
in the MVC specification) and bridginig Conversion Service calls to registered
ParamConverter implementations.

Dependencies
------------

The current implementation is based on the *Early Draft (Second Edition)*
version of the MVC specification. A corresponding MVC implementation must be
present at runtime - if the runtime environment does not provide one, include
an appropriate library in the application. (The extension was tested using
Glassfish Ozark 1.0.0-m02.)

CDI functionality is provided by the `thymeleaf-cdi` extension; this is a
required dependency. JAX-RS integration relies on non-standard interfaces and
is thus implementation-specific; currently there is the `thymeleaf-jersey`
extension for use in conjunction with Jersey. This is an optional dependency.
These extensions have runtime dependencies as well (any CDI implementation
for the former, Jersey as JAX-RS for the latter), but they are required for
MVC as well, so they should already be present.

The extension obviously needs Thymeleaf as well. At least Thymeleaf 3 is
required - earlier versions lacks lazy variable evaluation necessary for CDI
support. 

Usage
-----

1. Include the library in your project. For example with Maven:

    ```xml
        <dependency>
            <groupId>hu.inbuss</groupId>
            <artifactId>thymeleaf-mvc</artifactId>
            <version>0.0.9</version>
        </dependency>
    ```

    or with Gradle:

    ```gradle
    dependencies {
        compile 'hu.inbuss:thymeleaf-mvc:0.0.9'
    }
    ```

    These methods pull in `thymeleaf-cdi` as a transitive dependency. If
    you're using a way that does not handle transitive dependencies, make
    sure to include it manually.
    
2. When using Jersey as the JAX-RS library backing the MVC implementation,
    consider also including the optional dependency `thymeleaf-jersey`. For
    example with Maven:

    ```xml
        <dependency>
            <groupId>hu.inbuss</groupId>
            <artifactId>thymeleaf-jersey</artifactId>
            <version>0.0.9</version>
        </dependency>
    ```

    or with Gradle:

    ```gradle
    dependencies {
        compile 'hu.inbuss:thymeleaf-jersey:0.0.9'
    }
    ```

3. Any necessary initialization for basic usage happens automatically. This
    includes the supporting components (`thymeleaf-cdi` and optionally
    `thymeleaf-jersey`) as well - you don't have to set them up manually. You
    are ready to define your controllers and write the view templates.

4. If required, you can customise the template engine by specializing the
    producer bean `hu.inbuss.thymeleaf.mvc.TemplateEngineProducer`. For
    example, you may want to include an additional dialect:

    ```java
    import hu.inbuss.thymeleaf.mvc.TemplateEngineProducer;
    import javax.enterprise.inject.Produces;
    import javax.enterprise.inject.Specializes;
    import nz.net.ultraq.thymeleaf.LayoutDialect;
    import org.thymeleaf.TemplateEngine;

    public class CustomTEP extends TemplateEngineProducer {
        @Override @Specializes @Produces
        public TemplateEngine getTemplateEngine() {
            final TemplateEngine res = super.getTemplateEngine();
            res.addDialect(new LayoutDialect());
            return res;
        }
    }
    ```
