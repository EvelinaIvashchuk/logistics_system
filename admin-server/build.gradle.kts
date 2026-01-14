plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    java
    application
}

application {
    mainClass.set("com.logistics.admin.AdminApplication")
}

dependencies {
    implementation("de.codecentric:spring-boot-admin-server:3.3.3")
    implementation("de.codecentric:spring-boot-admin-server-ui:3.3.3")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
