pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
    }

    agent any

    tools {
        maven 'maven_3.8.4'
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
        stage('Building Docker Image') {
            steps {
                echo 'Starting Building Docker image'
                sh 'docker build -t kubegajanan/mmt-project .'
                sh 'docker build -t mmt-project .'
                echo 'Completed Building Docker Image'
            }
        }
        stage('Docker Image Push To Docker Hub'){
            steps{
                script {
                    withCredentials([string(credentialsId:'dockerhubCred', veriable:'dockerhubCred')]){
                    sh 'docker login docker.io -u kubegajanan -p ${dockerhubCred}'
                    echo 'Push Docker Image TO Dockerhub In progress'
                    sh 'docker push kubegajanan/mmt-project:v1.1'
                    echo 'Push Docker Image to Dockerhub Completed'

                    }
                }

             }
        }
        stage('DockerImage Push to Amazon ECR'){
            steps{
                script {
                    withDockerRegistry([credentialsId:'ecr:ap-south-1:ecr-credentials, url:"https://559220132560.dkr.ecr.ap-south-1.amazonaws.com]){
                        sh """
                        echo "List the docker images present in local"
                        docker images
                        echo "Tagging the Docker Image: In Progress"
                        docker tag mmt-project:latest 559220132560.dkr.ecr.ap-south-1.amazonaws.com/mmt-project:latest
                        echo "Tagging the Docker Image: Completed"
                        echo "Push Docker Image to ECR : In Progress"
                        docker push 559220132560.dkr.ecr.ap-south-1.amazonaws.com/mmt-project:latest
                        echo "Push Docker Image to ECR : Completed"
                                         """
                    }
                }
            }
        }
    }
}