# Lab 4 Versioning

In Lab 3 we explored API specifications and generating code against those specifications. 
Unlike when we use a binary dependency there is the opportunity for APIs to evolve in a decoupled way.
The issue with this is the possibility of software dependencies unexpectedly breaking. 

Versioning can help us to prevent issues, in this lab we will explore how changes to our API either retain or break backwards compatibility.

### Step 1

Create a copy of the API specification as it stands at the moment `curl http://localhost:8080/v2/api-docs -o v1.0.json`

Make a small refactor to the API and add a field representing the todo owner. 
This can simply be added into the Java object as an additional optional field.

Restart the application and capture the specification again `curl http://localhost:8080/v2/api-docs -o v1.1.json`

In the directory you are working in you should now have two files

```
$ls
v1.0.json	v1.1.json
```

We will now inspect the compatibility of these two files using [swagger-diff](https://github.com/civisanalytics/swagger-diff). 
The installation instructions can be found on the site, but it should be as simple as running `gem install swagger-diff`.

Running swagger diff returns nothing...

```
$swagger-diff v1.0.json v1.1.json 
$
```
This is actually good news as it means the change to add a field into our API has not broken compatibility.
That means that anyone who generated code with V1.0 of the API can still use their client code against V1.1.

Prove this to yourself by using you client from the previous lab.

### Step 2

We can also ask the swagger-diff tool to give us a breakdown of the changes. 
As this change has preserved backwards compatibility it would be a minor API change. 

```
$swagger-diff -c v1.0.json v1.1.json 
- new request params
  - post /todo/item/{}
    - new request param: owner (in: body, type: string)
- new response attributes
  - get /todo
    - new attribute for 200 response: []/owner (in: body, type: string)
  - get /todo/item/{}
    - new attribute for 200 response: owner (in: body, type: string)
$
```

### Step 3

Lets now try and break compatibility in the API. 
For this change rename the id to be taskID, restart the application and then run `curl http://localhost:8080/v2/api-docs -o v2.0.json`.

Rerun `swagger-diff` and observe the output.

### Step 4

Read through the information about Major and Minor versioning on APIs in this great document by [PayPal](https://github.com/paypal/api-standards/blob/master/api-style-guide.md#api-versioning).


