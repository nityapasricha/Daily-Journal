pipeline {
    agent any

    environment {
        BITBUCKET_REPO = 'https://sanchitagarg1@bitbucket.org/sgarg123/journal-management-application.git'
        BRANCH = 'master'
    }

    stages {
        stage('Start MySQL DB') {
            steps {
                script {
                    echo "Starting MySQL service (Windows)..."
                    bat 'net start MySQL80 || exit /b 0'
                }
            }
        }

        stage('Checkout Code') {
            steps {
                git branch: "${BRANCH}", url: "${BITBUCKET_REPO}"
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building the application with Maven..."
                    bat 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests..."
                    bat 'mvn test'
                }
            }
        }

        // Optional Docker section for later
        /*
        stage('Docker Build & Run') {
            steps {
                script {
                    bat 'docker build -t journal-app .'
                    bat 'docker run -d -p 9090:9090 journal-app'
                }
            }
        }
        */
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
