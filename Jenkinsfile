pipeline {
  agent {
    docker {
      // maven:3-alpine cause problems with surefire. Forked jvm is being killed.
      image 'maven:3.5.3-alpine'        // is going to run in jenkins-docker
      args '-v /root/.m2:/root/.m2' // on jenkins-docker -v "$HOME"/.m2:/root/.m2
    }
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }
    stage('Docker Build') {
      steps {
        docker.build("todarch/um:latest", "./dockerfiles/Dockerfile")
        // sh 'docker image build -f "./dockerfiles/Dockerfile" -t todarch/um:latest .'
      }
    }
  }
}
