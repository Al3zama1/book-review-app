pipeline {
    agent {
        node {
            label 'docker-agent-java17'
        }
    }
    stages {
        stage('building backend') {
            steps {
                echo "Building the application backend"
                withGradle() {
                sh '''
                ./gradlew -v
                '''
                }

            }
        }
    }
}