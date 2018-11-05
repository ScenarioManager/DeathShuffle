pipeline {
    agent { node { label 'sbt' } }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10', artifactNumToKeepStr: '10'))
    }
    
    stages {
        stage('package') {
            steps {
                sh 'sbt assembly'
                archiveArtifacts 'target/scala-*/*.jar'
            }
        }
    }
}