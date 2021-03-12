pipeline {
    agent {
        docker {
            image 'cibar-nexus:9202/cibar-jdk:14'
            registryUrl 'https://cibar-nexus:9202'
            registryCredentialsId 'ciber-nexus-credentials-id'
            args '-v /var/jenkins_home/.m2:/root/.m2'
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
