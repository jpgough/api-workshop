

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method POST()
        headers {
            contentType('application/json')
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