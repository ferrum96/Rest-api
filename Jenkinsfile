pipeline {
    agent any

    stages {

        stage ('Run jar file stage') {

            steps {
                bat 'start.bat'
            }
        }

        stage ('Compile stage') {

        	steps {
                bat 'mvn clean compile'
            }
        }

        stage ('Testing stage') {

        	steps {
        		bat 'mvn test'
            }
        }

        stage ('Stop jar file stage') {

            steps {
                bat 'stop.bat'
            }
        }

        stage('Reports') {
            steps{
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }

        stage ('Clean') {
            steps {
                deleteDir()
                cleanWs()
            }
        }

    }
}