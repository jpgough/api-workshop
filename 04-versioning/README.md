# Lab 4 Versioning

In Lab 3 we explored API specifications and generating code against those specifications.
Unlike when we use a binary dependency there is the opportunity for APIs to evolve in a decoupled way.
The issue with this is the possibility of software dependencies unexpectedly breaking.

Versioning can help us to prevent issues, in this lab we will explore how changes to our API either maintain or break backwards compatibility.

## Step 1

First let's create a simple Java program that will perform a diff between two versions of an OpenAPI specification and inform us of any changes between them.

You can do this easily in IntelliJ via the File -> New -> Project option.
Give your new program a name (e.g. `openapi-differ`), select Maven as the build system and Java 17.

Open the project and add the following new dependency to the Maven POM.

```xml
<dependency>
  <groupId>org.openapitools.openapidiff</groupId>
  <artifactId>openapi-diff-core</artifactId>
  <version>2.0.1</version>
</dependency>
```

This dependency pulls in a Java library from the project <https://github.com/OpenAPITools/openapi-diff> which can be used to compare two versions of an OpenAPI specification.

In the `Main` class of the project use the `openapi-diff` library to write a simple implementation that will compare 2 OpenAPI specs and create a report of the changes.

A basic solution may look something like this:

```java
    public static void main(String[] args) {
        ChangedOpenApi diff = OpenApiCompare.fromLocations("/path/to/spec_v1.0.json", "/path/to/spec_v1.1.json");

        String markdown = new MarkdownRender()
                .render(diff);

        System.out.println(markdown);
    }
```

## Step 2

Now let's test our OpenAPI spec diff'ing utility by creating two versions of the spec for our service with some minor changes.

If your ToDo service is not running currently, launch it and then make a copy of the API specification as it currently stands.

```shell
curl http://localhost:8080/v3/api-docs -o spec_v1.0.json
```

Make a small change to the API to add a field representing the Todo owner e.g.

```java
public record ToDo(String todoID, String description, String owner) {
}
```

Restart the application and capture the specification again

```shell
curl http://localhost:8080/v3/api-docs -o spec_v1.1.json
```

In the directory you are working in you should now have two files:

* spec_v1.0.json
* spec_v1.1.json

We will now inspect the compatibility of these two files using the `openapi-differ` program.

Make sure the paths you have specified in the `OpenApiCompare.fromLocations` method call points to the specs you have just generated then execute the program.

You should see some output in the console similar to the following:

```markdown
#### What's Changed
---

##### `GET` /todos


###### Return Type:

Changed response : **200 OK**
> OK

* Changed content type : `*/*`

    Changed items (object):

    * Added property `owner` (string)

##### `POST` /todos


###### Request:

Changed content type : `application/json`

* Added property `owner` (string)

##### `GET` /todos/{id}


###### Return Type:

Changed response : **200 OK**
> OK

* Changed content type : `*/*`

    * Added property `owner` (string)
```

## Step 3 - Richer diff output

We chose to output the diff using makrdown however there are various options for how the diff report can be output.

See if you can write the report to a HTML file that can viewed in a browser.

Check out the docs for your options here: <https://github.com/OpenAPITools/openapi-diff#render-difference>

## Step 4 - Breaking changes

Lets now try and break compatibility in the API.

Update the model for you `Todo` object to rename the id to be todoID e.g.

```java
public record ToDo(String todoID, String description, String owner) {
}
```

Restart the application and then run `curl http://localhost:8080/v3/api-docs -o spec_v2.0.json`.

Re-run your `openapi-differ` program against the new versoj of the spec and observe the output.

## Further reading

Read through the information about Major and Minor versioning on APIs in this great document by [PayPal](https://github.com/paypal/api-standards/blob/master/api-style-guide.md#api-versioning).
