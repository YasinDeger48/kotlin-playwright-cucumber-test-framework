# KotlinTestFramework

Cok basit bir Kotlin test framework ornegi.

- Cucumber + JUnit 5
- API testleri: Rest Assured
- UI testleri: Playwright

## Lokal calistirma

Gereksinim: Java 17 ve Maven.

```bash
mvn test
```

Sadece API testleri:

```bash
mvn test -Dcucumber.filter.tags="@api"
```

`qa` config ile:

```bash
mvn test -Denv=qa
```

## Docker ile calistirma

Image olustur:

```bash
docker build -t kotlin-test-framework .
```

Tum testleri calistir:

```bash
docker run --rm kotlin-test-framework
```

Sadece API testleri:

```bash
docker run --rm -e CUCUMBER_FILTER_TAGS="@api" kotlin-test-framework
```

`qa` config ile:

```bash
docker run --rm -e TEST_ENV=qa kotlin-test-framework
```

Not: Container icinde UI testleri icin varsayilan olarak `UI_HEADLESS=true` kullanilir.