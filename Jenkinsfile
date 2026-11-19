
pipeline {
    agent any

    environment {
        IMAGE_NAME = 'springboot-app'
        DEPLOY_HOST = '3.25.74.129'
        DEPLOY_USER = 'ubuntu'
        CONTAINER_NAME = 'springboot-app'
        APP_PORT = '8085'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ramreddy3872/spring-boot-application.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Docker Build and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login \
                            -u "$DOCKER_USER" --password-stdin

                        docker build \
                            -t "$DOCKER_USER/$IMAGE_NAME:$BUILD_NUMBER" .

                        docker push \
                            "$DOCKER_USER/$IMAGE_NAME:$BUILD_NUMBER"

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USER',
                        passwordVariable: 'DOCKER_PASS'
                    )
                ]) {
                    sshagent(credentials: ['deploy-ssh-key']) {
                        sh '''
                            ssh -o StrictHostKeyChecking=no \
                                $DEPLOY_USER@$DEPLOY_HOST "
                                
                                docker pull $DOCKER_USER/$IMAGE_NAME:$BUILD_NUMBER &&
                                
                                (docker stop $CONTAINER_NAME || true) &&
                                (docker rm $CONTAINER_NAME || true) &&
                                
                                docker run -d \
                                    --name $CONTAINER_NAME \
                                    --restart unless-stopped \
                                    -p $APP_PORT:$APP_PORT \
                                    $DOCKER_USER/$IMAGE_NAME:$BUILD_NUMBER
                            "
                        '''
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Application deployed successfully!'
        }

        failure {
            echo 'Deployment failed. Check Jenkins console logs.'
        }
    }
}
