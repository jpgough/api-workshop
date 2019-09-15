package entry

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method GET()
        url '/todos/1'
        headers {
            contentType(applicationJson())
        }
    }
    response {
        headers {
            contentType(applicationJson())
        }
        status OK()
        body(
                message: anyNonBlankString()
        )
    }
}