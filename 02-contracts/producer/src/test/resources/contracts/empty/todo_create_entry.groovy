package empty

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method POST()
        headers {
            contentType(applicationJson())
        }
        url '/todos'
        body(
                "message": "A new todo"
        )
    }
    response {
        status CREATED()
        headers {
            header(location(), anyUrl())
        }
    }
}