FROM openjdk:11
RUN mkdir /donation_platform_app
WORKDIR /donation_platform_app
COPY target/DonationPlatform-0.0.1-SNAPSHOT.jar /donation_platform_app
ENTRYPOINT java -jar /donation_platform_app/DonationPlatform-0.0.1-SNAPSHOT.jar