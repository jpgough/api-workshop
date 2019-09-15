package empty

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method GET()
        url '/todos'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status OK()
        body([])
    }
}