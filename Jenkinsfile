pipeline {
    agent {
        node {
            label 'docker-agent-java17'
        }
    }
    tools {
        gradle 'Gradle-7.6'
    }
    stages {
        stage('building backend') {
            steps {
                echo "Building the application backend"
                sh '''
                ./gradlew -v
                '''
//                 wrapper is needed if tools is not used and can only be used with
//                 gradle, maven, jdk
//                 withGradle() {
//                 sh '''
//                 ./gradlew -v
//                 '''

                }

            }
        }
    }
}