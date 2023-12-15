import org.jooq.meta.jaxb.Logging.WARN

plugins {
	java
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("nu.studer.jooq") version "8.2.1"
}

group = "com.kotkina"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	jooqGenerator("org.postgresql:postgresql:42.6.0")
}

jooq {
	version.set("3.18.4")

	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("main") {
			generateSchemaSourceOnCompilation.set(true)

			jooqConfiguration.apply {
				logging = WARN
				jdbc.apply {
					driver = "org.postgresql.Driver"
					url = "jdbc:postgresql://localhost:5432/contacts"
					user = "postgres"
					password = "admin"
				}
				generator.apply {
					name = "org.jooq.codegen.DefaultGenerator"
					database.apply {
						inputSchema = "contacts_schema"
					}
					generate.apply {
						isDeprecated = false
						isRecords = true
						isImmutablePojos = true
						isFluentSetters = true
					}
					target.apply {
						packageName = "com.kotkina.ContaclsWebDB"
						directory = "build/generated-src/jooq/main"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

tasks.named<nu.studer.gradle.jooq.JooqGenerate>("generateJooq") {
	(launcher::set)
	(javaToolchains.launcherFor {
		languageVersion.set(JavaLanguageVersion.of(17))
	})
}

tasks.withType<Test> {
	useJUnitPlatform()
}
