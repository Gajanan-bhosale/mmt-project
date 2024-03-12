pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'maven_3.9.4'
    }

    stages {
        stage('code Compilation') {
            steps {
                echo 'code compilation is statring'
                sh 'mvn clean compile'
            }
        }
        stage('code QA Execution') {
            steps {
                echo 'unit test case check in progess'
                sh 'mvn clean test'
                echo 'unit test case check completed'
            }
        }
        stage('code package') {
            steps {
                echo 'Creating a war Artifact'
                sh 'mvn clean package'
                echo 'creation war artifact done'
            }
        }

    }
}