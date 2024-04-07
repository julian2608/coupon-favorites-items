FROM amazoncorretto:21 AS builder
USER root
RUN mkdir /code

WORKDIR /code

COPY . /code

RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar -Dspring.profiles.active=prod", "target/coupon-favorites-items-0.0.1-SNAPSHOT.jar"]