# KotlinTestFramework

A very simple Kotlin test framework example.

- Cucumber + JUnit 5
- API tests: Rest Assured
- UI tests: Playwright

You can also use Playwright APIs for API testing, but in this project I preferred Rest Assured for API scenarios.

## Run Locally

Requirements: Java 17 and Maven.

```bash
mvn test
```

Run only API tests:

```bash
mvn test -Dcucumber.filter.tags="@api"
```

Run with `qa` config:

```bash
mvn test -Denv=qa
```

## Run with Docker

Build image:

```bash
docker build -t kotlin-test-framework .
```

Run all tests:

```bash
docker run --rm kotlin-test-framework
```

Run only API tests:

```bash
docker run --rm -e CUCUMBER_FILTER_TAGS="@api" kotlin-test-framework
```

Run with `qa` config:

```bash
docker run --rm -e TEST_ENV=qa kotlin-test-framework
```

Note: Inside the container, `UI_HEADLESS=true` is used by default for UI tests.