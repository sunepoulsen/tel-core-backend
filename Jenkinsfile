pipeline {
    agent {
        docker {
            image 'localhost:21903/cibar-jdk:14'
        }
    }

    stages {
        stage('Tools') {
            steps {
                sh 'java -version'
                sh 'docker --version'
                sh 'ls -l'
            }
        }
    }
}
