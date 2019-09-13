package contracts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/todos'
    }
    response {
        status 200
        body([])
    }
}