# User Management Service

It is the user management service for Todarch application.


## Consumer-Driven Contracts

- As a provider, check if um satisfies its contracts for its consumers.

```shell
mvn clean install
```

- just generate the tests if there are new contracts.

```shell
mvn spring-cloud-contract:generateTests
```

