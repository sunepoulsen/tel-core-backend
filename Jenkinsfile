pipeline {
    agent {
        docker {
            image 'cibar-nexus:21903/cibar-jdk:14'
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