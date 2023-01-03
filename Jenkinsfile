pipeline {
    agent {
        node {
            label 'docker-agent-book-review-app'
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