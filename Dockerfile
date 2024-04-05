FROM amazoncorretto:21 AS builder
USER root
RUN mkdir /code
WORKDIR /code

RUN yum install -y git

RUN mkdir /root/.ssh/

ADD id_ed25519 /root/.ssh/id_ed25519

RUN touch /root/.ssh/known_hosts

RUN chmod 600 /root/.ssh/id_ed25519 \
    && ssh-keyscan github.com >> /root/.ssh/known_hosts

RUN git clone git@github.com:julian2608/coupon-favorites-items.git

WORKDIR /code/coupon-favorites-items

RUN ./mvnw package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/coupon-favorites-items-0.0.1-SNAPSHOT.jar"]