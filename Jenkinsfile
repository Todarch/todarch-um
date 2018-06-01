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
      }
    }
  }
}
