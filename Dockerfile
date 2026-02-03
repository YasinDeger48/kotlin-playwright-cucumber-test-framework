FROM mcr.microsoft.com/playwright/java:v1.49.0-noble

WORKDIR /app

COPY pom.xml ./
RUN mvn -DskipTests dependency:go-offline

COPY src ./src

ENV TEST_ENV=default
ENV UI_HEADLESS=true
ENV CUCUMBER_FILTER_TAGS=

CMD ["sh", "-c", "mvn test -Denv=${TEST_ENV} ${CUCUMBER_FILTER_TAGS:+-Dcucumber.filter.tags=${CUCUMBER_FILTER_TAGS}}"]