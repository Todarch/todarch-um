pipeline {
  agent any

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
        script {
          docker.build("todarch/um:latest", "-f ./dockerfiles/Dockerfile .")
        }
        // sh 'docker image build -f "./dockerfiles/Dockerfile" -t todarch/um:latest .'
      }
    }
  }
}
