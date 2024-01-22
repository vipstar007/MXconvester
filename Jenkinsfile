pipeline{
    agent any
    tools{
    maven 'my-maven'
    }
    stages{
        stage('Build with Maven'){
              step{
                    sh 'mvn --version'
                    sh 'java -version'
                    sh 'mvn clean package -Dmaven.test.failure.ignore=true'
                }
         }
        stage('Packaging/Pushing imagae'){
            step{
                withDockerRegistry(credentialsId: 'dockerhub', url: 'https://index.docker.io/v1/'){
                    sh 'docker build -t skynight007/mxConvester .'
                    sh 'docker push skynight007/mxConvester'

                }
            }
        }
       stage('Deploy Spring Boot to DEV'){
            step{
                echo 'Deploying and cleaning'
                sh 'docker image pull skynight007/mxConvester'
                sh 'docker container stop skynight007-mxConvester || echo "this container does not exist" '
                sh 'docker network create dev || echo "this network exists"'
                sh 'echo y | docker container prune '

                sh 'docker container run -d --rm --name skynight007-mxConvester -p 8081:8080 --network dev skynight007/mxConvester'
                }
       }
    }
    post {
        //clean after build
        always{
            cleanWs()
        }
    }

}