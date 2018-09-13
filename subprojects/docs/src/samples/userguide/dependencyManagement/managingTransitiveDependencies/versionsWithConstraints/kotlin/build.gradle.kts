plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

// tag::dependency-constraints[]
dependencies {
    implementation("org.apache.httpcomponents:httpclient")
    constraints {
        add("implementation", "org.apache.httpcomponents:httpclient:4.5.3") {
            because("previous versions have a bug impacting this application")
        }
        add("implementation", "commons-codec:commons-codec:1.11") {
            because("version 1.9 pulled from httpclient has bugs affecting this application")
        }
    }
}
// end::dependency-constraints[]

task<Copy>("copyLibs") {
    from(configurations.compileClasspath)
    into("$buildDir/libs")
}