plugins {
    id("java")
    application
}

group = "com.interview.apiassignment.consumer"
version = "0.0.1-SNAPSHOT"

val slf4jVersion = "2.0.17"
val jacksonVersion = "2.19.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
    implementation("org.slf4j:slf4j-simple:${slf4jVersion}")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.interview.apiassignment.consumer.Main")
}