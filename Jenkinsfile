pipeline {
    agent {
        docker {
            image 'openjdk:15'
        }
    }

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Content') {
            steps {
                sh 'ls -l'
            }
        }
        stage('Versions') {
            steps {
                sh 'java -version'
            }
        }
    }
}
