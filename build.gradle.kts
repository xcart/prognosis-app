import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("com.github.node-gradle.node") version "2.2.4"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
}

group = "com.xcart"
version = "0.1.0"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	maven(url = "https://plugins.gradle.org/m2/")
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("com.github.node-gradle:gradle-node-plugin:2.2.4")
	implementation("com.github.kittinunf.fuel:fuel:2.2.3")
	implementation("com.github.kittinunf.fuel:fuel:2.2.3")
	implementation("com.github.kittinunf.fuel:fuel-jackson:2.2.3")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.security:spring-security-test")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.processResources {
	dependsOn("npm_install")
	dependsOn("npm_run_build")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}
