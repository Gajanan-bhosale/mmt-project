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
        stage('Sonarqube Code Quality Analiese'){
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }
            steps {
                withSonarQubeEnv('sonar-server'){
                    sh '${scannerHome}/bin/sonar-scanner'
                    sh 'mvn sonar:sonar'
                }
                timeout(time: 10, unit: 'MINUTES'){
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('code package') {
            steps {
                echo 'Creating a war Artifact'
                sh 'mvn clean package'
                echo 'creation war artifact done'
            }
        }
        stage('Building & Tag Docker Image') {
            steps {
                echo 'Starting Building Docker Image'
                sh 'docker build -t kubegajanan/mmt-project .'
                sh 'docker build -t mmt-project .'
                echo 'Completed  Building Docker Image'
            }
        }
        stage('Docker Image Scanning') {
             steps {
                  echo 'Docker Image Scanning Started'
                  sh 'java -version'
                  echo 'Docker Image Scanning Started'
             }
        }
        stage(' Docker push to Docker Hub') {
             steps {
                  script {
                        withCredentials([string(credentialsId: 'dockerhubCred', variable: 'dockerhubCred')]){
                        sh 'docker login docker.io -u kubegajanan -p ${dockerhubCred}'
                        echo "Push Docker Image to DockerHub : In Progress"
                        sh 'docker push kubegajanan/mmt-project:latest'
                        echo "Push Docker Image to DockerHub : In Progress"
                        sh 'whoami'
                        }
                    }
             }
        }
        stage(' Docker Image Push to Amazon ECR') {
              steps {
                   script {
                        withDockerRegistry([credentialsId:'ecr:ap-south-1:ecr-credentials', url:"https://581192365144.dkr.ecr.ap-south-1.amazonaws.com"]){
                        sh """
                        echo "List the docker images present in local"
                        docker images
                        echo "Tagging the Docker Image: In Progress"
                        docker tag mmt-project:latest 581192365144.dkr.ecr.ap-south-1.amazonaws.com/mmt-project:latest
                        echo "Tagging the Docker Image: Completed"
                        echo "Push Docker Image to ECR : In Progress"
                        docker push 581192365144.dkr.ecr.ap-south-1.amazonaws.com/mmt-project:latest
                        echo "Push Docker Image to ECR : Completed"
                        """
                        }
                   }
              }
        }
        stage('Upload the docker Image to Nexus') {
              steps {
                    script {
                         withCredentials([usernamePassword(credentialsId: 'nexuscred', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]){
                         sh 'docker login http://65.2.166.87:8085/repository/mmt-project/ -u admin -p ${PASSWORD}'
                         echo "Push Docker Image to Nexus : In Progress"
                         sh 'docker tag mmt-project 65.2.166.87:8085/mmt-project:latest'
                         sh 'docker push 65.2.166.87:8085/mmt-project'
                         echo "Push Docker Image to Nexus : Completed"
                         }
                    }
              }
        }

    }
}